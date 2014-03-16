package org.capcaval.ccoutils.common;

import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

public class CcFrame extends JFrame{
	int x =0;
	int y =0;
	int width =100;
	int height =200;
	
	public CcFrame(){
		this.init("", 0, 0, 300, 600);
	}
	
	public CcFrame(String title, int x, int y, int width, int height){
		this.init(title, x, y, width, height);
	}

	public CcFrame( int x, int y, int width, int height){
		this.init( "", x, y, width, height);
	}

	
	public void init(final String title, final int x, final int y, final int width, final int height){
		
    	this.addComponentListener(this.newComponentListener(title, x, y, width, height));	
	}
	
	private ComponentListener newComponentListener(final String title, int x, int y, int width, int height) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		ComponentListener listener = new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {
				initFrame(title, CcFrame.this.x, CcFrame.this.y, CcFrame.this.width, CcFrame.this.height);
			}
			
			@Override public void componentResized(ComponentEvent e) {}
			@Override public void componentMoved(ComponentEvent e) {}
			@Override public void componentHidden(ComponentEvent e) {}
		};
		return listener;
	}
	
	public void initFrame(String title, int x, int y, int width, int height){
		Insets i = this.getInsets();
		this.setBounds( x + i.left, y + i.right, width + i.left + i.right, height + i.top + i.bottom);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	public void addInside(Component c){
		this.getContentPane().add(c);
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height){
		super.setBounds(x, y, width, height);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}
}
