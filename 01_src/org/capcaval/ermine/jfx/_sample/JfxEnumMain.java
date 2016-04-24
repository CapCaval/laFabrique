package org.capcaval.ermine.jfx._sample;

import org.capcaval.ermine.jfx.JfxFrame;
import org.capcaval.ermine.jfx.JfxFrameCallBack;
import org.capcaval.ermine.jfx.node.JfxEnumComboBox;
import org.capcaval.ermine.jfx.panes.JfxRefPointPane;
import org.capcaval.ermine.layout.RefPointAlignEnum;
import org.capcaval.ermine.layout.RefPointEnum;

public class JfxEnumMain {
	
	enum TestEnum {
		newYork("New York"), paris("Paris"), london("London"), melbourne("Melbourne"), barcelona("Barcelona");
		
		protected String stringValue;
		
		TestEnum(String strValue){
			this.stringValue = strValue;
		}
		@Override
		public String toString(){
			return this.stringValue;
		}
		
	}

	public static void main(String[] args) {
		JfxFrame frame = JfxFrame.factory.newInstance(
				"EnumCombo", 10, 10, 400, 400, new JfxFrameCallBack() {
					@Override
					public void notifyFrameCreated(JfxFrame frame) {
						// We are into the JavaFX correct Thread
						System.out.println(Thread.currentThread().getName());

						// let's create 
						JfxRefPointPane pane = new JfxRefPointPane();
						
						JfxEnumComboBox<TestEnum> combo = JfxEnumComboBox.factory.newInstance(TestEnum.class);
						// Initialization of the control value
						combo.setValue(TestEnum.paris);
						
						pane.addNode(combo, RefPointEnum.middleCenterPoint, RefPointAlignEnum.topLeftAlign);
						
						frame.setView(pane);
					}
				});
		frame.display();
	}
}
