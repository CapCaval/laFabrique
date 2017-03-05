package org.capcaval.lafabrique.askii;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import javax.swing.JFrame;

public class BitmapTools {
	public static byte[] getByteArray(BufferedImage image) {
		byte[] imageInByte = null;
		try {
			WritableRaster raster = image.getRaster();
			DataBufferByte dbb = (DataBufferByte)raster.getDataBuffer();
			imageInByte = dbb.getData();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return imageInByte;
	}
	
	public static BufferedImage scaleImage(BufferedImage image, int width, int height) {
		// create a new buffer
		BufferedImage buf = new BufferedImage(width, height, image.getType());
		
		Graphics2D g = (Graphics2D)buf.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(image, 0, 0, width, height, null);
		
		return buf;
	}

	public static BufferedImage convertToGray(BufferedImage image){
		// create a new buffer
		BufferedImage buf = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		
		Graphics2D g = (Graphics2D)buf.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		
		return buf;
	}
	
	public static void displayImage(final BufferedImage image){
		JFrame frame = new JFrame(){
			@Override
			public void paint(Graphics g){
				g.drawImage(image, 5, 20, null);
			}
		};
		frame.setBounds(10,10, image.getWidth(), image.getHeight());
		frame.setVisible(true);
	}
}
