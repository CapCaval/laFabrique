package ccoutils.askii;

import java.awt.image.BufferedImage;

import org.capcaval.ccoutils.askii.AskiiTools;
import org.capcaval.ccoutils.commandline.Command;
import org.capcaval.ccoutils.common.ImageDisplayerFrame;
import org.capcaval.ccoutils.common.TextDisplayerFrame;
import org.capcaval.ccoutils.common.TextDisplayerFrame.TextDisplayerFrameFactory;
import org.capcaval.ccoutils.common.TextFileDisplayFrame;
import org.capcaval.ccoutils.file.FileTools;
import org.capcaval.ccoutils.lang.StringMultiLine;
import org.capcaval.ccoutils.lang.SystemTools;

import ccoutils.SampleCommons;

public class AskiiImageSample {
	
	@Command(desc = "show how to create an ascii image.")
	public void asciiImage(){
		System.out.println( SampleCommons.SAMPLE_SOURCE_CODE_MESSAGE + SystemTools.getCurrentFullMethodName());
		TextFileDisplayFrame.factory.newTextFileDisplayFrame(this.getClass()).display(800, 0, 600, 800);
		
		// load the file inside the sample package
		BufferedImage image = FileTools.getLocalImage("mouette.jpg");
		
		// display it
		ImageDisplayerFrame.factory.newImageDisplayerFrame(image).display();
		
		// convert it to string
		String askiiFish = AskiiTools.convertBitmapToAscii(image, 240, 90, "$*'  ");
		
		// display the ascii image
		TextDisplayerFrame.factory.newTextDisplayerFrame(500, 0, 1000, 1000, askiiFish).display();
		
	}
}
