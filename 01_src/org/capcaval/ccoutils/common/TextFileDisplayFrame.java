package org.capcaval.ccoutils.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import org.capcaval.ccoutils.factory.FactoryTools;
import org.capcaval.ccoutils.file.FileTools;


public class TextFileDisplayFrame {

	public static TextFileDisplayFrameFactory factory = FactoryTools.newImmutableFactory(TextFileDisplayFrameFactory.class);

	public static class TextFileDisplayFrameFactory {
		public TextFileDisplayFrame newTextFileDisplayFrame(){
			return new TextFileDisplayFrame();
		}
 	}
	private CcFrame frame;

 	public TextFileDisplayFrame() {
// 		String fileName = Thread.currentThread().getStackTrace()[3].getClassName();
// 		String classFileName = + fileName.replace('.', '/') + ".java";
// 		String source = "";
// 		try {
//			source = FileTools.readStringfromFile(classFileName);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
// 		
// 		this.frame = new CcFrame(fileName, 0, 0, 200, 500);
// 		
// 	    JEditorPane editor = new JEditorPane("text/html",
// 	           "<H1>A!</H1><P><FONT COLOR=blue>blue</FONT></P>" + source );
// 	       editor.setEditable(false);
// 	       JScrollPane pane = new JScrollPane(editor);
// 		frame.addInside(pane);
	}
 	
 	public void display(){
 		frame.setVisible(true);
 	}
}
