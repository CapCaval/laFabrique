package org.capcaval.ermine.jfx;

import java.util.concurrent.atomic.AtomicReference;

import org.capcaval.ermine.coves.AbstractCovesApplication;

public class JfxFrameFactoryImpl implements JfxFrameFactory {

	AtomicReference<AbstractCovesApplication> applicationRef = new AtomicReference<>();

	@Override
	public JfxFrame newInstance(String frameLabel, int x, int y, int width,
			int height) {
		return this.newInstance(frameLabel, x, y, width, height, null);
	}

	@Override
	public JfxFrame newInstance(final String frameLabel, final int x, final int y, final int width,
			final int height, final JfxFrameCallBack cb) {
		// start JavaFx if needed
		JfxApplication.start();
		
		final AtomicReference<JfxFrame> frameRef = new AtomicReference<>();
		
		JfxTools.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				JfxFrame frame = new JfxFrame(frameLabel, x, y, width, height, applicationRef.get());
				// keep the ref
				frameRef.set( frame);
				// call the callback
				if(cb!=null){
					cb.notifyFrameCreated(frame);}
			}
		});
		return frameRef.get();
	}

	@Override
	public void setApp(AbstractCovesApplication app) {
		this.applicationRef.set(app);
	}

}
