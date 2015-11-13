package application;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	String master = "passwordisthethingiamusing";
	
	public byte[] hash() throws NoSuchAlgorithmException {
	    MessageDigest sha256 = MessageDigest.getInstance("SHA-256");        
	    byte[] passBytes = master.getBytes();
	    byte[] passHash = sha256.digest(passBytes);
	    System.out.println(passHash);
	    return passHash;
	}

}
