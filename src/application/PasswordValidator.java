package application;

import java.util.regex.*;

public class PasswordValidator {
	private Pattern pattern;
	private Pattern pattern2;
	private Matcher matcher;
	
	private static final String passwordPattern =
			"((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s)(?=.*[!@#$%^&*]).{8,16})";
	
	private static final String userNamePattern =
			"((?!.*\\s).{1,64})";
	
	public PasswordValidator(){
		pattern = Pattern.compile(passwordPattern);
		pattern2 = Pattern.compile(userNamePattern);
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
	
	public boolean validateUserName (final String username){
		matcher = pattern2.matcher(username);
		return matcher.matches();
	}


}
