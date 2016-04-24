package org.capcaval.ermine.jfx;

import org.capcaval.ermine.coves.AbstractCovesApplication;


public interface JfxFrameFactory {

	public JfxFrame newInstance(String frameLabel, int x, int y, int width, int height);
	public JfxFrame newInstance(String frameLabel, int x, int y, int width, int height, JfxFrameCallBack cb);
	
	public void setApp(AbstractCovesApplication app);

}
