package ccoutils.askii;

import java.awt.image.BufferedImage;

import org.capcaval.ccoutils.askii.AskiiTools;
import org.capcaval.ccoutils.commandline.Command;
import org.capcaval.ccoutils.common.ImageDisplayerFrame;
import org.capcaval.ccoutils.common.TextDisplayerFrame;
import org.capcaval.ccoutils.common.TextDisplayerFrame.TextDisplayerFrameFactory;
import org.capcaval.ccoutils.file.FileTools;
import org.capcaval.ccoutils.lang.StringMultiLine;
import org.capcaval.ccoutils.lang.SystemTools;

import ccoutils.SampleCommons;

public class AskiiSample {

	@Command(desc = "show how to create an ascii logo. It shows two different logos display.")
	public String asciiLogo(){
		System.out.println( SampleCommons.SAMPLE_SOURCE_CODE_MESSAGE + SystemTools.getCurrentFullMethodName());
		//FileTools.readStringfromFile(pathStr);
		
		
		// ask for a string logo
		String logoStr = SystemTools.readConsoleInput("Enter a logo name : ");
		
		// create a multi-line to add two ascii logos
		StringMultiLine str = new StringMultiLine();
		
		// Set the default logo string
		str.addLine(AskiiTools.convertStringToAscii(logoStr));
		
		//set a second logo with others parameters 
		str.addLine(AskiiTools.convertStringToAscii(logoStr, " '*$", 70));
		
		// this is it 
		return str.toString();
	}
	
	@Command(desc = "show how to create an ascii image.")
	public String asciiImage(){
		System.out.println( SampleCommons.SAMPLE_SOURCE_CODE_MESSAGE + SystemTools.getCurrentFullMethodName());
		
		// load the file inside the sample package
		BufferedImage image = FileTools.getLocalImage("mouette.jpg");
		
		// display it
		ImageDisplayerFrame.factory.newImageDisplayerFrame(image).display();
		
		// convert it to string
		String askiiFish = AskiiTools.convertBitmapToAscii(image, 240, 90, "$*'  ");
		
		// display the ascii image
		TextDisplayerFrame.factory.newTextDisplayerFrame(500, 0, 1000, 1000, askiiFish).display();
		
		// it is going to be displayed on the console
		return askiiFish;
	}
}
