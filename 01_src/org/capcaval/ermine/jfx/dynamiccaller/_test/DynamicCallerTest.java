package org.capcaval.ermine.jfx.dynamiccaller._test;

import org.capcaval.ermine.jfx.JfxFrame;
import org.capcaval.ermine.jfx.dynamiccaller.JavaFXThreadAspect;
import org.capcaval.lafabrique.lang.dynamiccaller.DynamicCaller;
import org.capcaval.lafabrique.lang.dynamiccaller.DynamicCallerAspect;
import org.junit.Test;

public class DynamicCallerTest {
	
	public class FinalClass{
		
		public int multiplyValue(int val1, int val2){
			int result = val1*val2;
			System.out.println("Result : " + result + " in " + Thread.currentThread().getName());
			return result;
		}
		
	}
	

	
	@Test
	public void JfxTest(){
		FinalClass instance = new FinalClass();
		
		JfxFrame.factory.newInstance("", 0, 0, 0, 0);
		
		DynamicCaller dc = new DynamicCaller(instance);
		
		DynamicCallerAspect thread = new JavaFXThreadAspect(dc);
		
		try {
			Object result = thread.call(FinalClass.class.getMethods()[0], 2, 3);
			
			System.out.println(result);
			
			Thread.sleep(50);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
