package application;

import java.util.regex.*;

public class PasswordValidator {
	private Pattern pattern;
	private Matcher matcher;
	
	private static final String passwordPattern =
			"((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,16})";
	
	public PasswordValidator(){
		pattern = Pattern.compile(passwordPattern);
	}
	
	/**
	   * Validate password with regular expression
	   * @param password password for validation
	   * @return true valid password, false invalid password
	   */
	public boolean validate (final String password){
		matcher = pattern.matcher(password);
		return matcher.matches();
	}


}
