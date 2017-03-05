package org.capcaval.lafabrique.common;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.capcaval.lafabrique.factory.FactoryTools;

public class TextDisplayerFrame {
	
 	public static TextDisplayerFrameFactory factory = FactoryTools.newImmutableFactory( TextDisplayerFrameFactory.class);

	public static class TextDisplayerFrameFactory {
		public TextDisplayerFrame newTextDisplayerFrame(int x, int y, int width, int height, String text, boolean withFocus){
			return new TextDisplayerFrame(x, y, width, height, text, withFocus);
		}
 	}
 	
	private JFrame frame;
	
	protected TextDisplayerFrame(int x, int y, int width, int height, String text, boolean withFocus) {
		// image can not be null
		assert(text != null);
		// let display the image
		this.init(x, y, width, height, text, withFocus);
	}
	
	protected void init(int x, int y, int width, int height, String text, boolean withFocus){
		this.frame = new CcFrame("", x, y, width, height, withFocus);
		this.frame.getContentPane().add(this.newTextPanel(text));
		this.frame.setFocusableWindowState(withFocus);
	}

	private Component newTextPanel(final String text) {
		JTextArea panel = new JTextArea(text);
		
		Font font = new Font( "Monospaced", Font.PLAIN, 7 );
		panel.setFont(font);
		
//		@SuppressWarnings("serial")
//		JPanel panel = new JPanel(){
//			@Override
//			public void paint(Graphics g){
//				g.drawString(text, 0,0);
//			}
//		};
		return panel;
	}
	
	public void display(){
		this.frame.setVisible(true);
	}

	public void display(int x, int y, int width, int height){
		this.frame.setBounds(x, y, width, height);
		this.frame.setVisible(true);
	}

	
	public void hide(){
		this.frame.setVisible(false);
	}

}
