package org.capcaval.lafabrique.crypto;


import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.capcaval.lafabrique.lang.ArrayTools;
import org.capcaval.lafabrique.lang.SystemTools;
import org.capcaval.lafabrique.net.common.NetTool;

public class CryptoTools {

	public static String encryptToHexaString(String str) {
		byte[] array = encryptToByteArray(str);
		
		return ArrayTools.byteArrayToCompactHexaString(array);
	}
	
	public static byte[] encryptToByteArray(String str) {
		byte[] encryptedArray = null;
		try {
			byte[] key = SystemTools.getUserName().getBytes();
			byte[] iv = NetTool.getMacAddress().getBytes();
			iv = Arrays.copyOf(iv, 8); // use only first 8 bytes
			
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 8); // use only first 8 bytes
			
			SecretKeySpec secretKeySpec = new SecretKeySpec(key, "DES");
			AlgorithmParameterSpec ivParam = new IvParameterSpec(iv);
			
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParam);

			encryptedArray = cipher.doFinal(str.getBytes());
			//returnValue = ArrayTools.byteArrayToCompactHexaString(encryptedArray);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return encryptedArray;
	}

	public static String decryptFromHexaString(String encrytedHexaString) {
		byte[] array = ArrayTools.compactHexaStringToByteArray(encrytedHexaString);
		
		return decrypt(array);
	}
	
	public static String decrypt(byte[] encrytedArray) {
		String returnValue = null;
	      try {
				byte[] key = SystemTools.getUserName().getBytes();
				byte[] iv = NetTool.getMacAddress().getBytes();
				iv = Arrays.copyOf(iv, 8); // use only first 8 bytes

				MessageDigest sha = MessageDigest.getInstance("SHA-1");
				key = sha.digest(key);
				key = Arrays.copyOf(key, 8); // use only first 8 bytes
				
				SecretKeySpec secretKeySpec = new SecretKeySpec(key, "DES");
				AlgorithmParameterSpec ivParam = new IvParameterSpec(iv);
				
	            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParam);

	            //byte[] encrytedArray = ArrayTools.compactHexaStringToByteArray(cripted);
	            byte[] original = cipher.doFinal(encrytedArray);

	            returnValue =  new String(original);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

		return returnValue;
	}

}
