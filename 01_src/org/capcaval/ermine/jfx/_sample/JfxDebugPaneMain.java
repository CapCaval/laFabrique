package org.capcaval.ermine.jfx._sample;

import org.capcaval.ermine.jfx.JfxFrame;
import org.capcaval.ermine.jfx.JfxFrameCallBack;
import org.capcaval.ermine.jfx.panes.JfxDebugPane;

public class JfxDebugPaneMain {

	public static void main(String[] args) {

		JfxFrame frame = JfxFrame.factory.newInstance(
				"Debug", 
				0, 0, 
				500, 600, new JfxFrameCallBack() {
					@Override
					public void notifyFrameCreated(JfxFrame frame) {
						frame.setView(new JfxDebugPane());
					}
				});
		frame.display();
	}

}
