package org.capcaval.ermine.jfx.layout._test;

import javafx.scene.paint.Color;

import org.capcaval.ermine.jfx.JfxFrame;
import org.capcaval.ermine.jfx.JfxFrameCallBack;
import org.capcaval.ermine.jfx.layout.JfxRefPointLayout;
import org.capcaval.ermine.jfx.panes.JfxBorderPane;
import org.capcaval.ermine.jfx.panes.JfxTestPane;
import org.capcaval.ermine.layout.RefPointEnum;


public class JfxRefPointLayoutTestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		JfxFrame f = JfxFrame.factory.newInstance(
				"Test", 10, 10, 500, 700, new JfxFrameCallBack() {
					@Override
					public void notifyFrameCreated(JfxFrame frame) {
						JfxBorderPane main = new JfxBorderPane();
						
						JfxTestPane leftPane = new JfxTestPane("Left");
						leftPane.setBackgroundColor(Color.ORANGERED);
						JfxTestPane rightPane = new JfxTestPane("Right");
						rightPane.setBackgroundColor(Color.ORANGE);
						
						JfxRefPointLayout layout = new JfxRefPointLayout(main);
						layout.addResizableNode(leftPane, 
								RefPointEnum.topLeftPoint, 5, 5, 
								RefPointEnum.topLeftPoint, 200, 400);

						layout.addResizableNode(rightPane, 
								RefPointEnum.topLeftPoint, 210, 5, 
								RefPointEnum.bottomRightPoint, -5, -5);
						
						frame.setView(main);
						frame.display();
					}
				});
		
	}
}
