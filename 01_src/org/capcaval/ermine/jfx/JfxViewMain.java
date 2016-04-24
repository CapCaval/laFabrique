package org.capcaval.ermine.jfx;



public class JfxViewMain{

	public static void main(String[] args) throws InterruptedException {
		final JfxFrame f = JfxFrame.factory.newInstance("Toto", 10,30, 200,200,new JfxFrameCallBack() {
			@Override
			public void notifyFrameCreated(JfxFrame frame) {
//				frame.
//				frame.getGroup().getChildren().add(new Button("Frame1"));
//				frame.getGroup().requestLayout();
			}
		});
		
		final JfxFrame f2 = JfxFrame.factory.newInstance("Toto2", 10,100, 200, 200,new JfxFrameCallBack() {
			
			@Override
			public void notifyFrameCreated(JfxFrame frame) {
				System.out.println("add button f2");
				//frame.getGroup().getChildren().add(new Button("Frame2"));
				//frame.getGroup().requestLayout();
				frame.display();
			}
		});
		f.display();
		f2.display();
		
		
//		Platform.runLater(new Runnable() {
//			
//			@Override
//			public void run() {
//				f2.group.getChildren().add(new Button("djbcdksjhnkcjdkn"));
//				f2.getGroup().requestLayout();
//				f2.display();
//			}
//		});
		
	}
}
