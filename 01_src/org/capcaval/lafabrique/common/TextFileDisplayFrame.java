package org.capcaval.lafabrique.common;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import org.capcaval.lafabrique.factory.FactoryTools;
import org.capcaval.lafabrique.file.FileSeekerResult;
import org.capcaval.lafabrique.file.FileTools;


public class TextFileDisplayFrame {

	public static TextFileDisplayFrameFactory factory = FactoryTools.newImmutableFactory(TextFileDisplayFrameFactory.class);

	public static class TextFileDisplayFrameFactory {
		public TextFileDisplayFrame newTextFileDisplayFrame(Path fileToDisplay){
			return new TextFileDisplayFrame(fileToDisplay);
		}
		
		public TextFileDisplayFrame newTextFileDisplayFrame(Class<?> classToDisplay){
	 		FileSeekerResult r = FileTools.seekFiles(classToDisplay.getSimpleName() + ".java", Paths.get("."));
	 		Path p = r.getPathList()[0];
			return new TextFileDisplayFrame(p);
		}
 	}
	
	private CcFrame frame;

 	public TextFileDisplayFrame(Path fileToDisplay) {
 		String source = "";
 		
		source = FileTools.readStringfromFile(fileToDisplay);
 		
 		this.frame = new CcFrame(fileToDisplay.toFile().getName(), 0, 0, 200, 500, false);
 		
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
