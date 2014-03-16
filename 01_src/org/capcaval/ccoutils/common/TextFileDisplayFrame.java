package org.capcaval.ccoutils.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import org.capcaval.ccoutils.factory.FactoryTools;
import org.capcaval.ccoutils.file.FileSeekerResult;
import org.capcaval.ccoutils.file.FileTools;


public class TextFileDisplayFrame {

	public static TextFileDisplayFrameFactory factory = FactoryTools.newImmutableFactory(TextFileDisplayFrameFactory.class);

	public static class TextFileDisplayFrameFactory {
		public TextFileDisplayFrame newTextFileDisplayFrame(Path fileToDisplay){
			return new TextFileDisplayFrame(fileToDisplay);
		}
		
		public TextFileDisplayFrame newTextFileDisplayFrame(Class<?> classToDisplay){
			String fileName = classToDisplay.getCanonicalName();
	 		String classFileName = fileName.replace('.', '/') + ".java";
	 		
	 		FileSeekerResult r = null;
	 		try {
				r = FileTools.seekFiles(classToDisplay.getSimpleName() + ".java", Paths.get("."));
			} catch (IOException e) {
				e.printStackTrace();
			}
	 		Path p = r.getPathList()[0];
			return new TextFileDisplayFrame(p);
		}
 	}
	
	private CcFrame frame;

 	public TextFileDisplayFrame(Path fileToDisplay) {
 		String source = "";
 		
 		try {
			source = FileTools.readStringfromFile(fileToDisplay);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
 		this.frame = new CcFrame(fileToDisplay.toFile().getName(), 0, 0, 200, 500);
 		
 	    JEditorPane editor;
		try {
			editor = new JEditorPane("text/plain", source);
	 	    editor.setEditable(false);
	 	   Document doc = editor.getDocument();
	 	   if (doc instanceof PlainDocument) {
	 		   doc.putProperty(PlainDocument.tabSizeAttribute, 2);
	 	   }
	 	    
	 	    JScrollPane pane = new JScrollPane(editor);
	 		frame.addInside(pane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 	
 	public void display(){
 		frame.setVisible(true);
 	}
 	
	public void display(int x, int y, int width, int height){
		this.frame.setBounds(x, y, width, height);
		this.frame.setVisible(true);
	}

}
