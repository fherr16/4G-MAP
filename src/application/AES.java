package application;

import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
  static String IV = "FabianBradKevinM";
  static String plaintext = null;
  static String encryptionKey = null;
  static StringBuffer appendingString = new StringBuffer();
  static StringBuffer plainTextString = new StringBuffer();
  
  public static void main() {
    try {
      
      System.out.println("==Java==");
      System.out.println("plain:   " + plaintext);
      System.out.println("encryptionKey: " + encryptionKey);

      byte[] cipher = encrypt(plaintext, encryptionKey);

      System.out.print("cipher:  ");
      for (int i=0; i<cipher.length; i++)
        System.out.print(new Integer(cipher[i])+" ");
      System.out.println("");

      String decrypted = decrypt(cipher, encryptionKey);

      System.out.println("decrypt: " + decrypted);

    } catch (Exception e) {
      e.printStackTrace();
    } 
  }

  public static void alter(String text, String key)
  {
	  appendingString.append(key);
	  plaintext = text;
	  int counter = key.length();
	  while(counter < 16)
	  {
		  appendingString.append("\0");
		  counter++;
	  }
	  encryptionKey = appendingString.toString();
  }
  
  public static byte[] encrypt(String plainText, String encryptionKey) throws Exception {
	  
	  if(plainTextString.length() > 0)
		  plainTextString.delete(0, plainTextString.length());
	  
	  plainTextString.append(plainText);
	  int counter = plainText.length();
	  
	  while(counter < 16)
	  {
		  plainTextString.append("\0");
		  counter++;
	  }
	  plainText = plainTextString.toString();  
	    
	  if(appendingString.length() > 0)
		  appendingString.delete(0, appendingString.length());
	  
	  appendingString.append(encryptionKey);
	  int count = encryptionKey.length();
	  
	  while(count < 16)
	  {
		  appendingString.append("\0");
		  count++;
	  }
	  encryptionKey = appendingString.toString();  
	  
    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
    SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
    cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
    return cipher.doFinal(plainText.getBytes("UTF-8"));
  }

  public static String decrypt(byte[] cipherText, String encryptionKey) throws Exception{
	  
	  if(appendingString.length() > 0)
		  appendingString.delete(0, appendingString.length());
	  
	  appendingString.append(encryptionKey);
	  int counter = encryptionKey.length();
	  
	  while(counter < 16)
	  {
		  appendingString.append("\0");
		  counter++;
	  }
	  encryptionKey = appendingString.toString();  
	  
	  
    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
    SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
    cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
    return new String(cipher.doFinal(cipherText),"UTF-8");
  }
}