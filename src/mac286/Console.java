package mac286;
import java.io.*;
import java.util.*;
import java.util.Scanner;
public class Console {			
	public static void main(String[] args) {		
		String[] data = new String[Data.c];
		try(BufferedReader br = new BufferedReader(new FileReader(Data.path))){
			//Read the headers since we want to skip them
			br.readLine();
			int i = 0;//Utility variable
			//Read file and assign values to our arrays..		
			while((Data.row = br.readLine())!=null) {
				//Using comma as separators				
				data = Data.row.split(",");
				Data.columnA[i] = data[0];
				Data.columnB[i] = data[1];	
				Data.columnC[i] = Double.parseDouble(data[2]);
				Data.columnD[i] = Double.parseDouble(data[3]);
				Data.columnE[i] = Double.parseDouble(data[4]);
				Data.columnF[i] = Double.parseDouble(data[5]);
				Data.columnG[i] = Double.parseDouble(data[6]);
				Data.columnH[i] = Double.parseDouble(data[7]);	
				i++;
			}//./while()
		}catch(IOException e){ e.printStackTrace();} //./Try()
		readInput();			
	}//./main
	//Read user input
	public static void readInput() {		
		System.out.println("What do you need to know from the data? By the way, I also understand some Spanish ");
		//Read user input
		Scanner kb = new Scanner(System.in);		
		String str 	= kb.nextLine();
		//Search for keywords in user input
		boolean isPercent = false;
		//Positive limit by default
		boolean isNegative = false;
		do {//Fix or clean str variable before anything
			str = str.toLowerCase();
			//Response friendly if the user is also being friendly =)			
			if(Data.isUserFriendly(str))
				Data.getFriendlyResponse();
			
			if(str.contains("lose")||str.contains("loosing")||str.contains("perdida")||str.contains("perdiendo")||str.contains("perdido")) 
				isNegative=true;	
			//Search for keywords to find "relation to which column?"
			int i;
			for(i = 0; i<str.length()-1;i++) {			
					if(str.contains("%"))
						isPercent = true;				
					if((str.contains(Data.keywords[i]))) break;
									
			}			
			if(extractNumber(str)!=0)
			search(extractNumber(str),str,i,isPercent,isNegative);
			else search(str);
			System.out.println();
			System.out.println("What else do you need to know from the data? Remember, I also understand some Spanish. ");	
			str = kb.nextLine();			
			//Terminate the program if the user's input is empty or Data.negativeUserResponse[str.contains(all elements)];
			if(!(Data.doesUserNeedsAnythingElse(str)))
			break;
			
			
			
		}while(!str.isEmpty());		
		System.out.println("The program has been terminated.");		
	}//./readInput();
	//Search for number on user input
	public static double extractNumber(final String str) {                
		double n = 0;
	    if(str == null || str.isEmpty()) return n;
	    StringBuilder sb = new StringBuilder();
	    boolean found = false;
	    for(char c : str.toCharArray()){
	        if(Character.isDigit(c)){
	            sb.append(c);
	            found = true;
	        } else if(found){
	            // If we already found a digit before and this char is not a digit, stop looping
	            break;                
	        }
	    }
	    if(!(sb.toString().isEmpty()))
	    n = Double.parseDouble(sb.toString());
	    return n;
	}
	//Search based on parameters
	public static void search(String str) {
		System.out.print("I see what you're tryin to do..");
	}
	public static void search(double n, String str, int keyword, boolean p, boolean s ) {
		//Some cases may have execute the same function. The reason is because some cases are coming from Spanish language.
		switch(keyword) {
		//Open price
		case 0: filterInRelationToOpenPrice(n,p,s); break;
		case 8: filterInRelationToOpenPrice(n,p,s); break;
		//Today's high
		case 2:	filterInRelationToTodaysHigh(n,p,s); break;
		case 10: filterInRelationToTodaysHigh(n,p,s); break;
		//Today's low
		case 3: filterInRelationToTodaysLow(n,p,s); break;
		case 11: filterInRelationToTodaysLow(n,p,s); break;
		//52-Week high
		case 4: filterInRelationTo52wkHigh(n,p,s); break;
		case 5: filterInRelationTo52wkHigh(n,p,s); break;
		case 12: filterInRelationTo52wkHigh(n,p,s); break;
		case 13: filterInRelationTo52wkHigh(n,p,s); break;
		//52-Week low
		
			default: System.out.println("Sorry, I couldn't get that.");
		}//./switch()		
	}
	//Functions to compare current price to any other column
	public static void filterInRelationToOpenPrice(double n,boolean p,boolean s) {
		displayHeader(2);
		//n = number in comparison, p = isPercent, s = isNegative
		if(p) {
			//search for percent negative
			if(s) {					
				for(int i = 0; i<Data.c;i++) {					
					if( Data.columnD[i]<(Data.columnC[i]-Data.columnC[i]*(n/100))) {						
						displayReport(i,2);
						
					}
				}
			}else {
				for(int i = 0; i<Data.c;i++) {						
					if( Data.columnD[i]>(Data.columnC[i]+Data.columnC[i]*(n/100))) {
						System.out.println(Data.columnA[i]);
					}
				}
			}				
		}else {
			if(s)n = n * -1;
			//search for constant							
			for(int i= 0; i<Data.c;i++) {	
					if(Data.columnD[i]-Data.columnC[i]<n) {
						System.out.println(Data.columnA[i]);
					}						
			}
		}
	}
	public static void filterInRelationToTodaysHigh(double n, boolean p, boolean s) {
		if(p){//search for percent negative
			if(s){for(int i = 0; i<Data.c;i++){					
					if( Data.columnD[i]<(Data.columnE[i]-Data.columnE[i]*(n/100))) {
						System.out.println(Data.columnA[i]);}}}//./if(s)
					else {System.out.println("That's imposible to answer. Check your input and try again, plaese \n");readInput();}}
		//Search for constant
		else {if(s){//search for constant							
				for(int i= 0; i<Data.c;i++) {	
					if(Data.columnD[i]-Data.columnE[i]<n) {
						System.out.println(Data.columnA[i]);}}}
		else {System.out.println("That's imposible to answer. Check your input and try again, plaese \n");readInput();}}//./else			
	
	}
	public static void filterInRelationToTodaysLow(double n, boolean p, boolean s) {
		//n = number in comparison, p = isPercent, s = isNegative
		if(p) {//search for percent 		
			for(int i = 0; i<Data.c-1;i++) 						
					if(Data.columnD[i]>=(Data.columnF[i]+Data.columnF[i]*(n/100))) 
						System.out.println(Data.columnA[i]);							
		}else //search for constant			
			for(int j= 0; j<Data.c;j++) 	
					if(Data.columnD[j]-Data.columnF[j]>=n) 
						System.out.println(Data.columnA[j]);			
	}
	public static void filterInRelationTo52wkHigh(double n, boolean p, boolean s) {
		//TO BE CODED
		if(p) {//search for percent negative
			if(s) {
				System.out.println("Search for percent negative in relation to 52wk high");
				for(int i = 0; i<Data.c-1;i++)				
					if( Data.columnD[i]>=(Data.columnG[i]-Data.columnG[i]*(n/100))) 
						System.out.println(Data.columnA[i]);			
			} else System.out.println("Wait, that makes no sense. Check your input and try again please.");			
		}else//search for constant							
			 for(int i= 0; i<Data.c;i++) 	
					if(Data.columnD[i]-Data.columnG[i]< -n) 
						System.out.println(Data.columnA[i]);	
	}
	public static void filterInrelationTo52wkLow(double n, boolean p, boolean s) {
		
	}
	public static void displayReport(int i,int c) {
		//Details for given company at index [i]
		StringBuilder sb = new StringBuilder();		
		sb.append(StringUtil.pad(Data.columnA[i], 20));
		sb.append(StringUtil.pad(Data.columnB[i].toLowerCase(), 9));
		sb.append(StringUtil.pad("$"+Data.columnD[i], 15));
		switch(c) {
		//Open price
		case 2: sb.append(StringUtil.pad("$"+Data.columnC[i], 12)); break;
		case 4: sb.append(StringUtil.pad("$"+Data.columnE[i], 15)); break;
		case 5:	sb.append(StringUtil.pad("$"+Data.columnF[i], 15)); break;
		case 6: sb.append(StringUtil.pad("$"+Data.columnG[i], 15)); break;
		case 7: sb.append(StringUtil.pad("$"+Data.columnH[i], 15)); break;
		default: System.out.println("Error printing display report");
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
		default : System.out.println("Error printing header");
		}		
		sb.append("\n");
		System.out.println(sb);
	}


}
