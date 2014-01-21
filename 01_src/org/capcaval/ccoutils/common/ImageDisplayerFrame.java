package org.capcaval.ccoutils.common;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.capcaval.ccoutils.factory.FactoryTools;

public class ImageDisplayerFrame {
	
 	public static ImageDisplayerFrameFactory factory = FactoryTools.newImmutableFactory(ImageDisplayerFrameFactory.class);

	public static class ImageDisplayerFrameFactory {
		public ImageDisplayerFrame newImageDisplayerFrame(BufferedImage image){
			return new ImageDisplayerFrame(image);
		}
 	}
 	
	private CcFrame frame;
	
	protected ImageDisplayerFrame(BufferedImage image) {
		// image can not be null
		assert(image != null);
		// let display the image
		this.init(0, 0, image.getWidth(), image.getHeight(), image);
	}
	
	protected void init(int x, int y, int width, int height, BufferedImage image){
		this.frame = new CcFrame(x, y, width, height);
		this.frame.addInside(this.newImagePanel(image));
	}

	private Component newImagePanel(final BufferedImage image) {
		@SuppressWarnings("serial")
		JPanel panel = new JPanel(){
			@Override
			public void paint(Graphics g){
				g.drawImage(image, 0,0, null);
			}
		};
		return panel;
	}
	
	public void display(){
		this.frame.setVisible(true);
	}
	
	public void hide(){
		this.frame.setVisible(false);
	}

}
