package org.capcaval.ermine.jfx;

import java.awt.GraphicsDevice;
import java.awt.GraphicsDevice.WindowTranslucency;
import java.awt.GraphicsEnvironment;
import java.util.concurrent.CountDownLatch;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class JfxTools {

	public static boolean isTransparencySupported() {
		boolean transparencySupported = false;

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();

		if (gd.isWindowTranslucencySupported(WindowTranslucency.TRANSLUCENT)) {
			transparencySupported = true;
		}

		return transparencySupported;
	}

	public static void invokeAndWait(final Runnable runnable) {

		if (Platform.isFxApplicationThread() == true) {
			// just run
			runnable.run();
		} else {
			final CountDownLatch syncro = new CountDownLatch(1);
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					runnable.run();
					// the syncro can be raised
					syncro.countDown();
				}
			});
			try {
				syncro.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String toStyleString(Color color){
		String r = Integer.toHexString((int)(color.getRed()*255)); 
		String g = Integer.toHexString((int)(color.getGreen()*255)); 
		String b = Integer.toHexString((int)(color.getBlue()*255));
		String a = Integer.toHexString((int)(color.getOpacity()*255));
		
		if(r.length()==1){
			r = "0" + r;
		}
		if(g.length()==1){
			g = "0" + g;
		}
		if(b.length()==1){
			b = "0" + b;
		}
		if(a.length()==1){
			a = "0" + a;
		}

		
		return r+g+b+a;
	}
	
	public static void setBackgroundColor(Node node, Color color){
		node.setStyle("-fx-background-color: #" + toStyleString(color));
	}
	
	public static double getFontWidth(Font font, String car){
        Text t = new Text(car);
        new Scene(new Group(t));
        t.setFont(font);
        t.applyCss();
        double w = t.getLayoutBounds().getWidth();

        return w;
	}
}
