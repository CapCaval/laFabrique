package org.capcaval.ermine.jfx._sample;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.SVGPathBuilder;

import org.capcaval.ermine.jfx.JfxApplication;
import org.capcaval.ermine.jfx.JfxCustomFrame;
import org.capcaval.ermine.jfx.JfxFrame;
import org.capcaval.ermine.jfx.JfxFrameCallBack;
import org.capcaval.ermine.jfx.SimpleWindowApplication;

public class JfxCustomFrameMain{

	public static void main(String[] args) {
		
		JfxFrame frame = JfxFrame.factory.newInstance(
				"JfxFrameMain",
				10, 10, 500, 300,
				new JfxFrameCallBack() {
				@Override
				public void notifyFrameCreated(JfxFrame frame) {
				frame.setbackgroundColor(Color.DARKBLUE);
				// create and add a label
				Label label = new Label("Hello!");
				label.setTextFill(Color.WHITE);
				frame.setView(label);
				}
				});
		
		
		
		
		
		
		
		
		
		
		
		JfxApplication.start();
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				SVGPath svg = new SVGPath();
				svg.setContent("M -3437.4375 -826.4375 C -3581.0824 -826.4375 -3698.71 -773.87042 -3708.6875 -707.28125 L -3788.0938 -707.28125 C -3829.1241 -707.28125 -3862.1562 -674.24903 -3862.1562 -633.21875 L -3862.1562 -243.5625 C -3862.1562 -202.53222 -3829.124 -169.5 -3788.0938 -169.5 L -3086.7812 -169.5 C -3045.7509 -169.5 -3012.7188 -202.53222 -3012.7188 -243.5625 L -3012.7188 -633.21875 C -3012.7188 -674.24903 -3045.751 -707.28125 -3086.7812 -707.28125 L -3166.2188 -707.28125 C -3176.1963 -773.87042 -3293.7926 -826.4375 -3437.4375 -826.4375 z ");
				
//				Polygon p = new Polygon(
//						10,0,
//						40,0,
//						38,20,
//						0,30);
//				
//				p.setFill(Color.AQUA);
				
				DropShadow ds = new DropShadow();
				ds.setOffsetX(2);
				ds.setOffsetY(5);
				svg.setEffect(ds);
				
				
				JfxCustomFrame frame = new JfxCustomFrame(10, 10, svg);
				
				SimpleWindowApplication.addDraggingSceneOnNode(svg, frame.getStage());
				
				
//				JfxBorderPane pane = new JfxBorderPane();
//				pane.setBackgroundColor(new Color(1,1,0,0));
//				frame.setView(pane);
				
				frame.display();
			}
		});
	}

}
