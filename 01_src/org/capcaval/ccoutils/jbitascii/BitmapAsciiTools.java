package org.capcaval.ccoutils.jbitascii;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.capcaval.ccoutils.lang.StringTools;

public class BitmapAsciiTools {

	public static String convertStringToAscii(String str){
		// default width is 80 characters		
		return convertStringToAscii(str, "@;. ", 80);
	}
	
	public static String convertStringToAscii(String str, String gradient, int widthInChar){
		int fontSize = 300;

		BufferedImage imageTemp = new BufferedImage(2400, 350, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D gTemp = (Graphics2D)imageTemp.getGraphics();
		
		Font font = new Font("Verdena", Font.BOLD, fontSize);  // Verdena
        FontMetrics fontMetrics = gTemp.getFontMetrics(font);
        int width = fontMetrics.stringWidth(str);
		
		BufferedImage image = new BufferedImage(width, 290, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g = (Graphics2D)image.getGraphics();
		
		g.setColor(Color.white);
		g.setBackground(Color.white);
		g.clearRect(0, 0, image.getWidth(), image.getHeight());
		
		g.setFont(font);
		g.setColor(Color.black);
		g.drawString(str, 0, fontSize-75);
		
		//BitmapTools.displayImage(image);

		// compute the image ratio
		double ratio = width/290;

		int heightInChar = (int)(widthInChar / ratio);
		
		return convertBitmapToAscii(image, widthInChar, heightInChar, gradient.toCharArray());
	}

	
	public static String convertBitmapToAscii(BufferedImage image){
		return convertBitmapToAscii(image, new String("@&;,. ").toCharArray());
	}
	
	public static String convertBitmapToAscii(BufferedImage image, char[] conversionTable){
		// compute the image ratio
		double ratio = image.getWidth() / image.getHeight();
		int width = 80;
		
		return convertBitmapToAscii(image, width, (int)(width / ratio / 1), String.valueOf(conversionTable));
	}
	
	public static String convertBitmapToAscii(BufferedImage image, int width, char[] conversionTable){
		// compute the image ratio
		double ratio = image.getWidth() / image.getHeight();
		
		return convertBitmapToAscii(image, width, (int)(width * ratio / 2), String.valueOf(conversionTable));
	}

	
	public static String convertBitmapToAscii(BufferedImage image, int width, int height, char[] conversionTable){
		return convertBitmapToAscii(image, width, height, String.valueOf(conversionTable));
	}
	
	public static String convertBitmapToAscii(BufferedImage image, int width, int height, String conversionTable){
		String returnValue = "";
		
		// convert to the new scale
		image = BitmapTools.scaleImage(image, width, height);
		
		// convert to gray scale
		image = BitmapTools.convertToGray(image);
		
		// get the byte array
		byte[] byteArray = BitmapTools.getByteArray(image);
		
		// calculate the ratio
		int ratio = 255 / (conversionTable.length()-1);
		
		int arraySize = height*(width+1);
		
		// allocate 
		char[] charArray = new char[arraySize];
		int counter = 0;
		int outCounter = 0;
		int charValue = 0;
		
		for(int index =0; index<arraySize; index++){
			if(counter >= width){
				charValue = '\n';
				counter=0;
			}else{
				charValue = (byteArray[outCounter++] & 0xFF) / ratio;
				charValue = conversionTable.charAt(charValue);
				counter++;	
			}
			// add the value
			charArray[index] = (char)charValue;
		}
		
		returnValue = cutTheLastEmptyLine(new String(charArray));
		
		return returnValue;
	}

	private static String cutTheLastEmptyLine(String string) {
		String[] lineList = string.split("\n");
		int index = 0;
		
		for( index = lineList.length-1 ; index>0 ; index--){
			String line = lineList[index];
			if(isStringEmpty(line) == false){
				break;
			}
		}
		
		StringBuilder strBuilder = new StringBuilder();
		
		for(int i = 0; i<=index; i++){
			strBuilder.append(lineList[i]+'\n');
		}
		
		return strBuilder.toString();
	}

	private static boolean isStringEmpty(String string) {
		// an empty string a made of space tab or CR
		return StringTools.isExclusiveMadeOf(string, '\n','\t',' ');
	}
}
