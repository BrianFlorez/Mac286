package mac286;
import java.util.Random;
public class Data {
	//Some variables
	public static String row ="";
	public static String path ="C:\\Users\\Brian\\Desktop\\mac286\\src\\mac286\\MarketData - book1.csv";
	public final static int c = 100;
	 //Let's create our columns with a initial size of 100 elements
	public static String[] columnA = new String[c];
	public static String[] columnB = new String[c];
	public static double[] columnC = new double[c];
	public static double[] columnD = new double[c];
	public static double[] columnE = new double[c];
	public static double[] columnF = new double[c];
	public static double[] columnG = new double[c];
	public static double[] columnH = new double[c];
	//Array keywords helpful "In relation to what? cases" DO NOT ADD ELEMENTS WITHOUT MODIFYING CASES IN SEARCH()
	public static String[] keywords = {"open price","current price","today's high","today's low","52-week high", "52-wk high","52-week low","52-wk low","precio inicial","precio actual","alto de hoy","bajo de hoy","alto anual","maximo del ano","bajo anual","minimo del ano"};
	//Does the user needs anything else? The elements must be lower case.
	public static String[] positiveUserResponse = {};
	public static String[] negativeUserResponse = {"no, thank you!","no, thank you.","no, thank you","no, thanks", "no, thanks.","no","no.","no, gracias!","no, gracias." , "no, gracias"};
	//Is the user being friendly? then let's have some friendly response!
	public static String[] friendlyResponse = {"Of course!","Here you go =)","Say no more.","You got it.","Here you go, boss.","At your orders."};
	public static String[] isUserFriendly = {"please","por favor", "amigo","friend","good morning","buenos dias","good afternoon","buenas tardes", "hi","hi," ,"hello", "hola"};
	//Get a random friendly response =)
	public static void getFriendlyResponse() {
		Random rd = new Random();				
		int n = rd.nextInt(friendlyResponse.length);
		System.out.println(friendlyResponse[n]);
		System.out.println();
	}
	//Returns true if user is being friendly
	public static boolean isUserFriendly(String str) {
		str = str.toLowerCase();
		for(int i = 0;i < isUserFriendly.length;i++ )
			if(str.contains(isUserFriendly[i])) return true;			
		return false;
	};
	//Does the user needs anything else? function
	public static boolean doesUserNeedsAnythingElse(String str) {
		for(int i = 0; i< negativeUserResponse.length;i++) 
			if(str.equals(negativeUserResponse[i]))return false;		
		return true;
	}
}
