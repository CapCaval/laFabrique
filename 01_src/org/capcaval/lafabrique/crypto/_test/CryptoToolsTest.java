package org.capcaval.lafabrique.crypto._test;

import java.util.Arrays;

import junit.framework.Assert;

import org.capcaval.lafabrique.crypto.CryptoTools;
import org.capcaval.lafabrique.lang.ArrayTools;
import org.junit.Test;

public class CryptoToolsTest {
	
	@Test
	public void encriptDecripotTest(){
		String str = "Hello world!";
		byte[] array = CryptoTools.encryptToByteArray(str);
		
		System.out.println(Arrays.toString(array));
		// [-66, -77, 105, 104, -111, -98, -118, 114, -15, -2, 7, 88, -90, -105, 125, 21]
		System.out.println(ArrayTools.byteArrayToCompactHexaString(array));
		// BEB36968919E8A72F1FE0758A6977D15
		
		String result = CryptoTools.decrypt(array);
		System.out.println(result);

		String strCryptedHexa = CryptoTools.encryptToHexaString(str);
		System.out.println(strCryptedHexa);
		
		String result2 = CryptoTools.decryptFromHexaString(strCryptedHexa);
		System.out.println("==>" + result2);
		
		
		Assert.assertEquals(str, result);
		
	}
	
	
	

}
