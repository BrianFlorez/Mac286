import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
public class SuperWatchList{
    private static final String APPLICATION_NAME = "SuperWatchList";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static int size;
    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = SuperWatchList.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);}
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
    public static void main(String... args) throws IOException, GeneralSecurityException  {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1WD8LTq7xwR4JL6U8Pw1zTTfaksvHvcd0xOQcp0Rx2vs";
        final String range = "book1!A2:H";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            // constant number for maximum number of companies in watchlist
           size = values.size();
           Brain brain = new Brain(values.size());
            //Lets retrieve and save the data
            int i =0;
            for (List row : values) {
                try{
                    Brain.columnA[i] = row.get(0).toString();
                    Brain.columnB[i] = row.get(1).toString();
                    Brain.columnC[i] = Double.parseDouble(row.get(2).toString());
                    Brain.columnD[i] = Double.parseDouble(row.get(3).toString());
                    Brain.columnE[i] = Double.parseDouble(row.get(4).toString());
                    Brain.columnF[i] = Double.parseDouble(row.get(5).toString());
                    Brain.columnG[i] = Double.parseDouble(row.get(6).toString());
                    Brain.columnH[i] = Double.parseDouble(row.get(7).toString());
                    i++;
                } catch (Exception e){
                    System.out.println("There is something wrong with the data, please check your google sheet.");
                    System.out.println(row.get(0).toString() + " has invalid data");
                };
            }
        }


        //Execute program loop
        readInput();
    }//./main
    //Read user input
    private static void readInput() {
        System.out.println("What do you need to know from the data? By the way, I also understand some Spanish ");
        //Read user input
        Scanner kb = new Scanner(System.in);
        String str 	= kb.nextLine();
        do {
            //Search for keywords in user input
            boolean isPercent = false;
            //Positive limit by default
            boolean isNegative = false;
            //Fix or clean str variable before anything
            str = str.toLowerCase();
            //Response friendly if the user is also being friendly =)
            if(Brain.isUserFriendly(str))
                Brain.getFriendlyResponse();
            if(str.contains("lose")||str.contains("losing")||str.contains("perdida")||str.contains("perdiendo")||str.contains("perdido"))
                isNegative=true;
            //Search for keywords to find "relation to which column?"
            int i;
            for(i = 0; i< Brain.keywords.length; i++) {
                if(str.contains("%"))
                    isPercent = true;
                if((str.contains(Brain.keywords[i]))) break;

            }
            if(extractNumber(str)!=0)
                search(extractNumber(str),str,i,isPercent,isNegative);
            else search(str);
            System.out.println();
            Brain.getFriendlyIntroduction(); //Does the user needs anything else?
            str = kb.nextLine();
            //Terminate the program if the user's input is empty or Brain.negativeUserResponse[str.contains(all elements)];
            if(!(Brain.doesUserNeedsAnythingElse(str)))break;
        }while(!str.isEmpty() || (str !=""));
        System.out.println("The program has been terminated.");
    }//./readInput();
    //Search for number on user input

    /**
     *
     * @param str str represents a user string
     * @return returns a double number from the user string
     */
    public static double extractNumber(final String str) {
        if(str == null || str.isEmpty()) return 0; //Returns 0 when given str is empty.
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        boolean firstDecimalSeparator = false;
        for(char c : str.toCharArray()){
            if(Character.isDigit(c)) {
                sb.append(c);
                found = true;
            } else if (found && c == '.' && !firstDecimalSeparator ) {
                sb.append(c);
                firstDecimalSeparator = true;
            } else if(found)
                break; // If we already found a digit before and this char is not a digit, stop looping
        }
        if(!(sb.toString().isEmpty()))
            return Double.parseDouble(sb.toString());
        return 0;
    }
    //Search based on parameters
    public static void search(String str) {
        //Plural
        if(str.contains("companies")||str.contains("stocks")) {
            displayHeader(0);
            for(int i = 0; i< Brain.size-1; i++)
                displayReport(i,0);
        }
        //Singular
        else {
            displayHeader(0);
            //is looking for more than one company specifically?
            for(int i = 0; i < size-1; i++) {
                if(str.contains(Brain.columnA[i].toLowerCase()))
                    displayReport(i,0);
            }

        }
    }
    public static void search(double n, String str, int keyword, boolean p, boolean s ) {
        //Some cases may have execute the same function. The reason is because some cases are coming from Spanish language.
        //keyword represents the index in Brain.java keywords[]
        switch(keyword) {
            //Open price
            case 0:
            case 8:
                filterInRelationToOpenPrice(n,p,s); break;
            //Today's high
            case 2:
            case 10:
                filterInRelationToTodaysHigh(n,p,s); break;
            //Today's low
            case 3:
            case 11:
                filterInRelationToTodaysLow(n,p); break;
            //52-Week high
            case 4:
            case 5:
            case 13:
            case 12:
                filterInRelationTo52wkHigh(n,p); break;
            //52-Week low
            case 6:
            case 7:
            case 14:
            case 15:
                filterInrelationTo52wkLow(n,p); break;
            default: System.out.println("Sorry, I couldn't get that.");
        }//./switch()
    }


    /**
     * Function to compare current price to open price
     * @param number number is representing the value from user input
     * @param isPercent is the user searching using percent?
     * @param isNegative is the search a negative difference?
     */
    public static void filterInRelationToOpenPrice(double number,boolean isPercent,boolean isNegative) {
        displayHeader(2);
        //number = number in comparison, p = isPercent, isNegative = isNegative
        if(isPercent) {
            //search for percent negative
            if(isNegative) {
                for(int i = 0; i< Brain.size; i++) {
                    if( Brain.columnD[i]<(Brain.columnC[i]- Brain.columnC[i]*(number/100)))displayReport(i,2);
                }
            }else {
                for(int i = 0; i< Brain.size; i++) {
                    if( Brain.columnD[i] > (Brain.columnC[i] + Brain.columnC[i]*(number/100)))
                        displayReport(i,2);
                }
            }
        }else {
            if (isNegative) {
                for (int i = 0; i < Brain.size; i++) {
                    if (Brain.columnC[i] - Brain.columnD[i] > number)
                        displayReport(i, 2);
                }
            } else {
                //search for constant
                for (int i = 0; i < Brain.size; i++) {
                    if (Brain.columnD[i] - Brain.columnC[i] > number)
                        displayReport(i, 2);
                }
            }
        }
    }
    public static void filterInRelationToTodaysHigh(double n, boolean p, boolean s) {
        displayHeader(4);
        if(p){//search for percent negative
            if(s){for(int i = 0; i< Brain.size; i++){
                if( Brain.columnD[i]<(Brain.columnE[i]- Brain.columnE[i]*(n/100))) {
                    displayReport(i,4);
                }}}//./if(s)
            else {System.out.println("That's imposible to answer. Check your input and try again, plaese \n");readInput();}}
        //Search for constant
        else {if(s){//search for constant
            for(int i = 0; i< Brain.size; i++) {
                if((Brain.columnE[i]-Brain.columnD[i])>= n) {
                    displayReport(i,4);}}
        }
        else {System.out.println("That's imposible to answer. Check your input and try again, plaese \n");readInput();}}//./else
    }
    public static void filterInRelationToTodaysLow(double n, boolean p) {
        //n = number in comparison, p = isPercent, s = isNegative
        displayHeader(5);
        if(p) {//search for percent
            for(int i = 0; i< Brain.size-1; i++)
                if(Brain.columnD[i]>=(Brain.columnF[i]+ Brain.columnF[i]*(n/100)))
                    displayReport(i,5);
        }else //search for constant
            for(int j = 0; j< Brain.size; j++)
                if((Brain.columnD[j]- Brain.columnF[j])>=n)
                    displayReport(j,5);
    }
    public static void filterInRelationTo52wkHigh(double n, boolean isPercent) {
        displayHeader(6);
        if(isPercent) {
            for(int i = 0; i< Brain.size-1; i++)
                if( Brain.columnD[i]<=(Brain.columnG[i]- Brain.columnG[i]*(n/100)))
                    displayReport(i,6);
        }else//search for constant
            for(int i = 0; i< Brain.size; i++)
                if(Brain.columnD[i]- Brain.columnG[i]< -n)
                   displayReport(i,6);
    }
    public static void filterInrelationTo52wkLow(double number, boolean isPercent) {
        displayHeader(7);
        if(isPercent) {
            for(int i = 0; i< Brain.size-1; i++)
                if( Brain.columnD[i]>=(Brain.columnH[i] + Brain.columnH[i]*(number/100)))
                    displayReport(i,7);
        }else//search for constant
            for(int i = 0; i< Brain.size; i++)
                if(Brain.columnD[i]- Brain.columnH[i]> number)
                   displayReport(i,7);
    }
    public static void displayReport(int i,int column) {
        //Details for given company at index [i]
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.pad(Brain.columnA[i], 20));
        sb.append(StringUtil.pad(Brain.columnB[i].toLowerCase(), 9));
        sb.append(StringUtil.pad("$"+ Brain.columnD[i], 15));
        switch(column) {
            case 2: sb.append(StringUtil.pad("$"+ Brain.columnC[i], 12)); break;
            case 4: sb.append(StringUtil.pad("$"+ Brain.columnE[i], 15)); break;
            case 5:	sb.append(StringUtil.pad("$"+ Brain.columnF[i], 15)); break;
            case 6: sb.append(StringUtil.pad("$"+ Brain.columnG[i], 15)); break;
            case 7: sb.append(StringUtil.pad("$"+ Brain.columnH[i], 15)); break;
            default: 	sb.append(StringUtil.pad("$"+ Brain.columnC[i], 12));
                sb.append(StringUtil.pad("$"+ Brain.columnE[i], 15));
                sb.append(StringUtil.pad("$"+ Brain.columnF[i], 15));
                sb.append(StringUtil.pad("$"+ Brain.columnG[i], 15));
                sb.append(StringUtil.pad("$"+ Brain.columnH[i], 15));
        }
        sb.append("\n");
        System.out.print(sb);
    }
    public static void displayHeader(int c) {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.pad("Company ", 20));
        sb.append(StringUtil.pad("Ticker ", 9));
        sb.append(StringUtil.pad("Current price ", 15));
        switch(c){
            case 2:sb.append(StringUtil.pad("Open price ", 12)); break;
            case 4: sb.append(StringUtil.pad("Today's high ",15)); break;
            case 5:	sb.append(StringUtil.pad("Today's low", 15)); break;
            case 6: sb.append(StringUtil.pad("52-week high", 15)); break;
            case 7: sb.append(StringUtil.pad("52-week low", 15)); break;
            default :
                sb.append(StringUtil.pad("Open price ", 12));
                sb.append(StringUtil.pad("Today's high ",15));
                sb.append(StringUtil.pad("Today's low", 15));
                sb.append(StringUtil.pad("52-week high", 15));
                sb.append(StringUtil.pad("52-week low", 15));

        }
        sb.append("\n");
        System.out.print(sb);
    }
}