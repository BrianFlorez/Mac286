package mac286;
import java.io.*;
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
		switch(keyword) {
		//open price
		case 0: filterInRelationToOpenPrice(n,p,s); break;
		case 8: filterInRelationToOpenPrice(n,p,s); break;
		//today's high
		case 2:	filterInRelationToTodaysHigh(n,p,s); break;
		case 10: filterInRelationToTodaysHigh(n,p,s); break;
			default: System.out.println("Sorry, I couldn't get that.");
		}//./switch()
		
	}
	//Functions to compare current price to any other column
	public static void filterInRelationToOpenPrice(double n,boolean p,boolean s) {
		//n = number in comparison, p = isPercent, s = isNegative
		if(p) {
			//search for percent negative
			if(s) {					
				for(int i = 0; i<Data.c;i++) {					
					if( Data.columnD[i]<(Data.columnC[i]-Data.columnC[i]*(n/100))) {
						System.out.println(Data.columnA[i]);
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
	public static void filterInRelationToTodaysLow() {
		
	}
	public static void filterInRelationTo52wkHigh() {
		
	}
	public static void filterInrelationTo52wkLow() {
		
	}
}
