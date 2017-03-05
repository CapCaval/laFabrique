package org.capcaval.ermine.jfx.dynamiccaller;

import java.lang.reflect.Method;
import java.util.concurrent.ScheduledExecutorService;

import javafx.application.Platform;

import org.capcaval.lafabrique.lang.dynamiccaller.DynamicCallerAspect;
import org.capcaval.lafabrique.lang.dynamiccaller.DynamicCallerInterface;

public class JavaFXThreadAspect extends DynamicCallerAspect{

	protected ScheduledExecutorService ses;
	protected Method method;
	protected Object[] argsArray;
	
	public JavaFXThreadAspect(DynamicCallerInterface originalInstance) {
		super(originalInstance);
	}

	@Override
	public Object call(Method method, Object... argsArray) throws Exception {
		// first a context Object
		MethodContext mc = new MethodContext(this.originalInstance, method, argsArray);

		// let's execute in javafx thread
		Platform.runLater(mc);
		
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
