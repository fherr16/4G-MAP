package application;

import java.util.Random;

public class Password {

	 	private static final String lowerCase = "abcdefghijklmnopqrstuvwxyz";
	    private static final String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    private static final String symbol = "!@#$%^&*";
	    private static final String integers = "0123456789";
	    private static Random r = new Random();
	    private static String pass = new String();

	    public static void main (String[] args) {
	    		    	int spot;
	    	int u = 0; //With this method of randomization, there is no guarantee that every section will be filled in.
	    	int l = 0; //Until we meet, I am just putting in these checks. 
	    	int s = 0;
	    	int i = 0;

	        while (pass.length () != 10){
	            int rPick = r.nextInt(4);
	            if (rPick == 0){
	                spot = r.nextInt(25);
	                pass += lowerCase.charAt(spot);
	                l++;
	            } else if (rPick == 1) {
	                spot = r.nextInt (25);
	                pass += upperCase.charAt(spot);
	                u++;
	            } else if (rPick == 2) {
	                spot = r.nextInt (7);
	                pass += symbol.charAt(spot);
	                s++;
	            } else if (rPick == 3){
	                spot = r.nextInt (9);
	                pass += integers.charAt (spot);
	                i++;
	            }
	        }
	        if (i > 0 && s > 0 && l > 0 && u > 0)
	        	System.out.println(pass);
	        else 
	        	System.out.println("Did not pass" + i + l + s + u);
	    	}
}
