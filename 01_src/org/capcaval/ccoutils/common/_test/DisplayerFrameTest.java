package org.capcaval.ccoutils.common._test;

import java.awt.image.BufferedImage;

import org.capcaval.ccoutils.common.ImageDisplayerFrame;
import org.capcaval.ccoutils.common.TextDisplayerFrame;
import org.capcaval.ccoutils.file.FileTools;
import org.capcaval.ccoutils.lang.StringMultiLine;
import org.junit.Test;

public class DisplayerFrameTest {

	@Test
	public void imageDisplayTest() {
		BufferedImage image = FileTools.getLocalImage("smile.png");
		ImageDisplayerFrame.factory.newImageDisplayerFrame(image).display();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void textDisplayTest() {
		StringMultiLine str = new StringMultiLine(
				"line1",
				"line2",
				"line3",
				"line4");
		TextDisplayerFrame.factory.newTextDisplayerFrame(0, 0, 100, 200, str.toString()).display();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
