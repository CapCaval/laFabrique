package org.capcaval.lafabrique.lang;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamTools {
	public static String toString(InputStream is) {
		StringMultiLine returnStr = new StringMultiLine();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		String line;
		try {
			while ((line = br.readLine()) != null) {
				returnStr.addLine(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnStr.toString();
	}
}
