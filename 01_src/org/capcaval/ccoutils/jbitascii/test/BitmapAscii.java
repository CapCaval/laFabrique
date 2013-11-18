package org.capcaval.ccoutils.jbitascii.test;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import org.capcaval.ccoutils.jbitascii.BitmapAsciiTools;

public class BitmapAscii {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
				
		BufferedImage image = null;
		try {
			//URL resource = BitmapAscii.class.getResource("darky.jpg");
			URL resource = BitmapAscii.class.getResource("capcaval.png");
			image = ImageIO.read(resource);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String str = BitmapAsciiTools.convertBitmapToAscii(image, 160, 20, 
				new char[]{'@','#','§','&','*','^','/','\\','"','-','_',';',':','.', ' '});
				//new char[]{'%',';','.',' '});
		
		System.out.println("result : \n" + str);
	}

}
