package org.capcaval.lafabrique.application;

import java.lang.annotation.Annotation;

import org.capcaval.lafabrique.commandline._test.CommandHandler;
import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.common.CommandResult.Type;
import org.capcaval.lafabrique.converter.Converter;
import org.capcaval.lafabrique.converter.ConverterManager;
import org.capcaval.lafabrique.lang.SystemTools;

public class ApplicationRunner {
	
	Class<? extends Annotation> annotationType;
	CommandHandler commandHandler;
	Converter<?, ?>[] converterList;

	public ApplicationRunner(){
		
	}

	public void addCommandHandler(Class<? extends Annotation> annotationType, CommandHandler commandHandler) {
		this.annotationType = annotationType;
		this.commandHandler = commandHandler;
	}

	public void setConverterList(Converter<?, ?>[] converterList) {
		this.converterList = converterList;
	}

//	public CommandResult run(Class<? extends AbstractCovesApplication> applicationType, AbstractCovesApplication appInstance, String[] args) {
//		return null;
//	}

	public CommandResult run(Class<?> applicationType, String[] paramArray) {
		Object instance = null;
		// allocate the application instance
		try {
			instance = applicationType.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this.run(applicationType, instance, paramArray);
	}
	
	public CommandResult run(Class<?> applicationType, Object instance, String[] paramArray) {
		return this.run(applicationType, instance, paramArray, null);
	}
	
	public CommandResult run(Class<?> applicationType, Object appInstance, String[] paramArray, Converter<?,?>[] converterList) {
		CommandResult result = null;
		
		if(SystemTools.isJavaVersionBelowVersion("1.7") == true){
			return new CommandResult(Type.ERROR, "Java Version shall be at least 1.7 or above. The current version is " + SystemTools.getCurrentJavaVersion().toString() +  ".");
		}
		
		try {
			// create a converter manager
			ConverterManager converterManager = new ConverterManager();
			// all other converter if any
			if(converterList != null){
				converterManager.addConverter(converterList);}

			// secondly get all application informations
			// name, property file etc..
			ApplicationDescription appDescription = ApplicationTools.newApplicationDescription(appInstance,
					applicationType, converterManager);
			
			// set a command handler if any
			appDescription.commandLineComputer.addCommandHandler(this.annotationType, this.commandHandler);
			
			// secondly bis, configure the CommandResult with the application name
			CommandResult.applicationName.set(appDescription.appDetails.applicationName);
			
			// Thirdly add an application shutter
			ApplicationTools.newApplicationShutter(appDescription, converterManager);
			
			// Fourth and last, launch the command if any
			if(paramArray == null){
				if(appDescription.defaultCommand == null){
					// display the help
					System.out.println("["+appDescription.appDetails.applicationName+"] There is no command and no default command");
					result = appDescription.commandLineComputer.computeCommandLine("help");
				}
				else{
					// run the default
					result = appDescription.commandLineComputer.computeCommandLine(appDescription.defaultCommand.commandStr);
				}
			}else{
				result = appDescription.commandLineComputer.computeCommandLine(paramArray);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
