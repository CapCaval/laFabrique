package org.capcaval.lafabrique.crypto._test;


import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.capcaval.lafabrique.lang.ArrayTools;
import org.capcaval.lafabrique.lang.SystemTools;
import org.capcaval.lafabrique.net.common.NetTool;

public class EncrytionSample
{    
	public static void main(String[] argv) {
		
		try{
			byte[] key = SystemTools.getUserName().getBytes("UTF-8");
			byte[] iv = NetTool.getMacAddress().getBytes();
			
			iv = Arrays.copyOf(iv, 8); // use only first 8 bytes
			
			System.out.println("iv : " +ArrayTools.byteArrayToCompactHexaString(iv) + " " + iv.length);
			
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			
			key = sha.digest(key);
			key = Arrays.copyOf(key, 8); // use only first 8 bytes
			SecretKeySpec secretKeySpec = new SecretKeySpec(key, "DES");
			
			AlgorithmParameterSpec ivParam = new IvParameterSpec(iv);
			
		    KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
		    SecretKey myDesKey = keygenerator.generateKey();
		    System.out.println(Arrays.toString(myDesKey.getEncoded()) + " l : " + myDesKey.getEncoded().length);
		    
		    Cipher desCipher;

		    // Create the cipher 
		    desCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");//DES/ECB/PKCS5Padding");
		    
		    // Initialize the cipher for encryption
		    desCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParam);

		    //sensitive information
		    byte[] text = "Hello world!".getBytes();

		    System.out.println("Text [Byte Format] : " + ArrayTools.byteArrayToCompactHexaString(text));
		    System.out.println("Text : " + new String(text));
		   
		    // Encrypt the text
		    byte[] textEncrypted = desCipher.doFinal(text);

		    System.out.println("Text Encryted : " + ArrayTools.byteArrayToCompactHexaString(textEncrypted));
		    
		    // Initialize the same cipher for decryption
		    desCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParam);

		    // Decrypt the text
		    byte[] textDecrypted = desCipher.doFinal(textEncrypted);
		    
		    System.out.println("Text Decryted : " + new String(textDecrypted));
		    
		}catch(Exception e){
			e.printStackTrace();
		} 
	   
	}
}