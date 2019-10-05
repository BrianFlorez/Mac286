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
//				//System.out.println(data[0]);
				i++;
			}//./while()
		}catch(IOException e){ e.printStackTrace();} //./Try()
		System.out.println("What do you need to know from the data? By the way, I also understand some Spanish ");
		readInput();		
		//Phrases to be interpreted
		//I would like to see the companies loosing 3% in relation to the open price.
		//I would like to see the companies gaining 3% in relation to the last 52 weeks.
	}//./main
	//Read user input
	public static void readInput() {
		//Read user input
		Scanner kb = new Scanner(System.in);
		
		String str = kb.nextLine();		
		//Array "In relation to what?"
		String[] keywords = {"open price","current price","today's high","today's low",
		"52-week high", "52-wk high","52-week low","52-wk low"};
		String[] keywords_ES = {"precio inicial","precio actual","alto de hoy","bajo de hoy",
		"alto anual","maximo del ano","bajo anual","minimo del ano"};
		//Fix or clean str
		String a = str.toLowerCase();
		//Search for keywords in user input
		boolean isPercent = false;
		//Positive limit by default
		boolean isNegative = false;
		if(a.contains("lose")||a.contains("loosing")||a.contains("perdida")||a.contains("perdiendo")||a.contains("perdido")) 
			isNegative=true;	
		//Search for keywords to find "relation to which column?"
		int i;
		for(i = 0; i<a.length()-1;i++) {			
				if(a.contains("%"))
					isPercent = true;				
				if((a.contains(keywords[i]))||(a.contains(keywords_ES[i]))) {
					break;
				}				
		}
		//System.out.println(extractNumber(a) + " " + isPercent + " "+i +" "+ isNegative);
		//Return
		if(extractNumber(a)!=0)
		search(extractNumber(a),a,i,isPercent,isNegative);
		else search(str);
		System.out.println();
		System.out.println("Would you like to do something else?");		
		Scanner in = new Scanner(System.in);
		String userResponse = in.nextLine();
		if(userResponse.contains("no, thank you!")||userResponse.contains("no, thank you!")||userResponse.contains("no")||userResponse.contains("no, thanks")){
		System.out.println("It's been a pleasure.");}
		else if(userResponse.contains("no, gracias!")||userResponse.contains("no, muchas gracias!")||userResponse.contains("no, muchas gracias"))
			System.out.println("Ha sido un placer.");
		else {
			if(userResponse.contains("please"))
				System.out.println("Of course!, here you go:");			 	
			readInput();
			}
		in.close();
		
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
		
	}
	public static void search(double n, String str, int column, boolean p, boolean s ) {		
		switch(column) {
		//open price
		case 0:		
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
			break;
		//today's high
		case 2:
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
		break;
		//today's low
		case 3:
			//do something
			break;
		//52-week high
		case 4:
			//do something
			break;
		//52-wk high
		case 5:
			//do something
			break;
		//52-week low
		case 6:
			//do something
			break;
		//52-wk low
		case 7:
			//do something
			break;
			default: System.out.println("Sorry, I couldn't get that.");
		}//./switch()
		
	}
}
