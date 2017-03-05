package org.capcaval.lafabrique.common._test;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

import org.capcaval.lafabrique.common.ImageDisplayerFrame;
import org.capcaval.lafabrique.common.TextDisplayerFrame;
import org.capcaval.lafabrique.common.TextFileDisplayFrame;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lang.StringMultiLine;
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
		TextDisplayerFrame.factory.newTextDisplayerFrame(0, 0, 100, 200, str.toString(), false).display();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void textFileDisplayTest() {
		Path p = FileTools.getLocalResourcePath("crowAndFox.txt");
		
		//TextFileDisplayFrame.factory.newTextFileDisplayFrame(p).display();

		
		TextFileDisplayFrame f =TextFileDisplayFrame.factory.newTextFileDisplayFrame(this.getClass());
		f.display(100,10,600,400);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
