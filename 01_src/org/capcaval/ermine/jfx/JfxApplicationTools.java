package org.capcaval.ermine.jfx;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javafx.application.Platform;

import org.capcaval.ermine.coves.AbstractCovesApplication;
import org.capcaval.ermine.coves.Controller;
import org.capcaval.ermine.coves._test.AppController;
import org.capcaval.lafabrique.application.ApplicationRunner;
import org.capcaval.lafabrique.application.ApplicationTools;
import org.capcaval.lafabrique.commandline._test.CommandHandler;
import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.common.CommandResult.Type;
import org.capcaval.lafabrique.converter.Converter;

public class JfxApplicationTools {

	public static CommandResult runApplication(Class<? extends AbstractCovesApplication> applicationType, String... args) {
		return runApplication(applicationType, args, (Converter<?,?>[])null);
	}
	
	public static CommandResult runApplication(final Class<? extends AbstractCovesApplication> applicationType, final String[] args, final Converter<?,?>... converterList) {
		// firstly start JavaFX
		JfxApplication.start();
		
		AbstractCovesApplication application = null;
		try {
			application = applicationType.newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		// do a final ref for inner class
		final AbstractCovesApplication finalApplication = application;
		
		// Create a specific application runner
		ApplicationRunner runner = ApplicationTools.newApplicationRunner();
		runner.addCommandHandler( JfxCommand.class, newJfxCommandHandler());
		runner.setConverterList(converterList);
		
		CommandResult cr = runner.run(applicationType, args);
		return cr;
	}

	private static CommandHandler newJfxCommandHandler() {
		CommandHandler ch = new CommandHandler() {
			@Override
			public Object[] handle(final Object proxy, final Method method, final Object[] paramArray) {
				final Object[] returnArray = new Object[1];
				JfxTools.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						try {
							Object o = method.invoke(proxy, paramArray);
							returnArray[0] = 0;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				return returnArray;
			};
		};
		return ch;
	}

	public static Class<? extends Controller<?>>[] getAllAppController(Class<?> applicationType) {
		List<Class<?>> controllerTypeList = new ArrayList<>();
		
		for(Field field :applicationType.getDeclaredFields()){
			field.setAccessible(true);
			AppController appCtrl = field.getAnnotation(AppController.class);
			if(appCtrl != null){
				// get it
				controllerTypeList.add(field.getType());
			}
		}
		
		return (Class<? extends Controller<?>>[])controllerTypeList.toArray(new Class<?>[0]);
	}
	
}
