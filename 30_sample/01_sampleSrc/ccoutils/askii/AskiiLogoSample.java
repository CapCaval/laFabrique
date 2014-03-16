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

public class AskiiLogoSample {

	@Command(desc = "show how to create an ascii logo. It shows two different logo displays.")
	public String asciiLogo(){
		System.out.println( SampleCommons.SAMPLE_SOURCE_CODE_MESSAGE + SystemTools.getCurrentFullMethodName());
		TextFileDisplayFrame.factory.newTextFileDisplayFrame(this.getClass()).display(800, 0, 600, 800);		
		
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
}
