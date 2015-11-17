package application;
import java.util.Random;

public class PasswordGenerator {

	private String lowerCase = "abcdefghijklmnopqrstuvwxyz";
	private String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String symbol = "!@#$%^&*";
	private String integers = "0123456789";
	private Random r = new Random();
	private String pass = new String();
	private String finalPassword = new String();

	public String generate() {
		
		pass = "";
		int spot = 0;
		int u = 0; 
		int l = 0; 
		int s = 0;
		int i = 0;

    	spot = r.nextInt(25);
        pass += lowerCase.charAt(spot);
        l++;
        
    	spot = r.nextInt (25);
        pass += upperCase.charAt(spot);
        u++;
        
    	spot = r.nextInt (7);
        pass += symbol.charAt(spot);
        s++;
        
    	spot = r.nextInt (9);
        pass += integers.charAt (spot);
		i++;
        
		while (pass.length () < 16){
			int rPick = r.nextInt(4);
			if (rPick == 0){
				spot = r.nextInt(25);
				pass += lowerCase.charAt(spot);
				l++;
			} 
			else if (rPick == 1) {
				spot = r.nextInt (25);
				pass += upperCase.charAt(spot);
				u++;
			} 
			else if (rPick == 2) {
				spot = r.nextInt (7);
				pass += symbol.charAt(spot);
				s++;
				}
			else if (rPick == 3){
				spot = r.nextInt (9);
				pass += integers.charAt (spot);
				i++;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(pass);
		finalPassword = "";
	
	while(sb.length() > 0)
	{
		int length = sb.length();
		int letter = r.nextInt(length);
		finalPassword += sb.charAt(letter);
		sb.deleteCharAt(letter);
	}
	return finalPassword;
	}
}