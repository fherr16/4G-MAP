package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.DatatypeConverter;

public class LoginFileReader {
	
	private String loginName;
	private String loginPassword;
	private ArrayList<Website> sites = new ArrayList();
	
	//Delimiter use
    
    private static final String COMMA_DELIMITER = ",";

    //Student attributes index
    private static final int WEBNAME_IDX = 0;
    private static final int PASSWORD_IDX = 1;
    
    private static AES encrypt = new AES();
	
	
	/**
	 * Function in charge of testing whether or not the user has access to it.
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean LoginFile(String username,String password){
		try{
			loginName = username;
			loginPassword = password;
			BufferedReader fileReader = new BufferedReader(new FileReader(loginName+".csv"));
			String line = "";
			line = fileReader.readLine();
			if(password.equals(line)){ //Password matches
				fileReader.close();
				return true;
			}
			fileReader.close();
			return false;
		}
		catch(IOException e){
			return false;
		}
	}
	
	
	public void hintLogin(String Username){
		
	}
	
	/**
	 * Decrypts Websites and puts them in ArrayList<Website> sites
	 * @param passwordName
	 * @return
	 */
	public boolean viewList(String passwordName){
		try{
			BufferedReader fileReader = new BufferedReader(new FileReader(loginName+".csv"));
			String line = "";
			line = fileReader.readLine(); //Skips Pass
			line = fileReader.readLine(); //Skips Hint Question

	         while ((line = fileReader.readLine()) != null) {
	         	//Get all tokens available in line
	             String[] tokens = line.split(COMMA_DELIMITER);
	             if (tokens.length > 0) {
	             	String websiteName = ((tokens[WEBNAME_IDX]));
	             	String websitePass = ((tokens[PASSWORD_IDX]));
	             	
	             	byte[] encryptedName = DatatypeConverter.parseHexBinary(websiteName);
	                String website = encrypt.decrypt(encryptedName, passwordName);
	             	
	                byte[] encryptedBytes = DatatypeConverter.parseHexBinary(websitePass);
	                String original = encrypt.decrypt(encryptedBytes, passwordName);
	             	
	             	Website temp = new Website(website,original);
	             	sites.add(temp);
					}
	         }
	         return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
	/**
	 * Return sites
	 * @return
	 */
	public ArrayList<Website> getViewList(){
		return sites;
	}
	
	
	public void addList(){
		
	}
}
