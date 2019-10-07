package mac286;
public class StringUtil {
	public static String pad(String s, int length) {
		if(s.length()<length) {
			StringBuilder sb = new StringBuilder(s);
			while(sb.length()<length) {
				sb.append(" ");
			}
			return sb.toString();
		}else {
			//truncate the string to the specified length
			return s.substring(0, length);
		}		
	} // ./pad
} // ./StringUtil class
