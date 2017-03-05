package org.capcaval.lafabrique.lang.dynamiccaller._test;

import java.util.concurrent.ScheduledExecutorService;

import org.capcaval.lafabrique.lang.dynamiccaller.DynamicCaller;
import org.capcaval.lafabrique.lang.dynamiccaller.DynamicCallerAspect;
import org.capcaval.lafabrique.lang.dynamiccaller.aspects.CloneParametersAspect;
import org.capcaval.lafabrique.lang.dynamiccaller.aspects.ThreadAspect;
import org.capcaval.lafabrique.thread.SchedulerFactory;
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
	public void testThread(){
		FinalClass instance = new FinalClass();
		
		DynamicCaller dc = new DynamicCaller(instance);
		ScheduledExecutorService ses = SchedulerFactory.factory.newSingleThreadScheduledExecutor("TestThread");
		
		DynamicCallerAspect thread = new ThreadAspect(dc, ses);
		
		try {
			Object result = thread.call(FinalClass.class.getMethods()[0], 2, 3);
			
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void cloneTest(){
		FinalClass instance = new FinalClass();
		
		DynamicCaller dc = new DynamicCaller(instance);

		DynamicCallerAspect cloneAspect = new CloneParametersAspect(dc);
		
		try {
			Object result = cloneAspect.call(FinalClass.class.getMethods()[0], 2, 3);
			
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void multiAspectTest(){
		FinalClass instance = new FinalClass();
		
		DynamicCaller dc = new DynamicCaller(instance);

		DynamicCallerAspect cloneAspect = new CloneParametersAspect(dc);
		
		ScheduledExecutorService ses = SchedulerFactory.factory.newSingleThreadScheduledExecutor("TestThread");
		DynamicCallerAspect threadAspect = new ThreadAspect(cloneAspect, ses);

		
		try {
			Object result = threadAspect.call(FinalClass.class.getMethods()[0], 2, 3);
			
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void JfxTest(){
//		FinalClass instance = new FinalClass();
//		
//		JfxFrame.factory.newInstance("", 0, 0, 0, 0);
//		
//		DynamicCaller dc = new DynamicCaller(instance);
//		
//		DynamicCallerAspect thread = new JavaFXThreadAspect(dc);
//		
//		try {
//			Object result = thread.call(FinalClass.class.getMethods()[0], 2, 3);
//			
//			System.out.println(result);
//			
//			Thread.sleep(50);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
