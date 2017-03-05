package org.capcaval.lafabrique.lang.dynamiccaller.aspects;

import java.lang.reflect.Method;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;

import org.capcaval.lafabrique.lang.dynamiccaller.DynamicCallerAspect;
import org.capcaval.lafabrique.lang.dynamiccaller.DynamicCallerInterface;

public class ThreadAspect extends DynamicCallerAspect{

	protected ScheduledExecutorService ses;
	protected Method method;
	protected Object[] argsArray;
	
	public ThreadAspect(DynamicCallerInterface originalInstance, ScheduledExecutorService ses) {
		super(originalInstance);
		this.ses = ses;
	}

	@Override
	public Object call(Method method, Object... argsArray) throws Exception {
		// first a context Object
		MethodContext mc = new MethodContext(this.originalInstance, method, argsArray);
		// let's execute in another thread
		this.ses.schedule(mc, 0, TimeUnit.NANOSECONDS);
		
		// no return value for async call
		return null;
	}
	
	public class MethodContext implements Runnable{
		protected Method method;
		protected Object[] argsArray;
		protected DynamicCallerInterface originalInstance;
		
		public MethodContext(DynamicCallerInterface originalInstance, Method method, Object... argsArray){
			this.method = method;
			this.argsArray = argsArray;
			this.originalInstance = originalInstance;
		}

		@Override
		public void run() {
			try {
				this.originalInstance.call(this.method, this.argsArray);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
