import java.util.Random;
public class Brain {
	//Some variables
	static int size;
	static String[] columnA;
	public static String[] columnB;
	public static double[] columnC;
	public static double[] columnD;
	public static double[] columnE;
	public static double[] columnF;
	public static double[] columnG;
	public static double[] columnH;
	Brain(int c){
		size = c;
		columnA = new String[size];
		columnB = new String[size];
		columnC = new double[size];
		columnD = new double[size];
		columnE = new double[size];
		columnF = new double[size];
		columnG = new double[size];
		columnH = new double[size];
	}
	//Array keywords helpful "In relation to what? cases" DO NOT ADD ELEMENTS IN BETWEEN, ALWAYS INSERT AT THE END
	public static String[] keywords = {"open price","current price","today's high","today's low","52-week high", "52-wk high","52-week low","52-wk low","precio inicial","precio actual","alto de hoy","bajo de hoy","alto anual","maximo del ano","bajo anual","minimo del ano"};
	//Does the user needs anything else? The elements must be lower case.
	public static String[] positiveUserResponse = {};
	public static String[] negativeUserResponse = {"no, thank you!","no, thank you.","no, thank you","no, thanks", "no, thanks.","no","no.","no, gracias!","no, gracias." , "no, gracias","bye", "nothing, thank you!", "nothing thank you", "nada, gracias", "nada, gracias!" , "chao", "bye", "bye!"};
	//Is the user being friendly? then let's have some friendly response!
	public static String[] friendlyResponse = {"Of course!","Here you go =)","Say no more.","You got it.","Here you go, boss.","At your orders."};
	public static String[] isUserFriendly = {"please","por favor", "amigo","friend","good morning","buenos dias","good afternoon","buenas tardes", "hi","hi," ,"hello", "hola"};
	//What phrase do we use when running the loop?
	public static String[] friendlyIntroduction = {"Is there anything else I can do for you?", "Let me know if you need anything else." ,"If you need anything else, let me know.", "Is that it?", "What else do you need?", "I will be here waiting for your instructions." };
	//Let's get some keywords for singular or plural when search();
	public static String[] keywordsSingular = {"company", "stock"};
	public static String[] keywordsPlural = {"companies" , "stocks"};
	//Get a random friendlyIntroduction
	public static void getFriendlyIntroduction() {
		Random rd = new Random();
		int n = rd.nextInt(friendlyIntroduction.length);
		System.out.println(friendlyIntroduction[n]);
		System.out.println();
	};
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
