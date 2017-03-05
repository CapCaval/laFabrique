/*
Copyright (C) 2012 by CapCaval.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
package org.capcaval.lafabrique.application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.common.CommandResult.Type;
import org.capcaval.lafabrique.converter.Converter;
import org.capcaval.lafabrique.converter.ConverterManager;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lang.SystemTools;
import org.capcaval.lafabrique.lang.Version;

public class ApplicationTools {

	
	public static CommandResult runApplication(Class<?> type, String... args) {
		Object instance = null;
		try {
			instance = type.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ApplicationTools.runApplication(type, instance, args);
	}
	
	public static CommandResult runApplication(Class<?> applicationType, Object appInstance, String[] args) {
		return runApplication(applicationType, appInstance, args, (Converter<?,?>[])null);
	}
	
	public static CommandResult runApplication(Class<?> applicationType, Object appInstance, String[] args, Converter<?,?>... converterList) {
		CommandResult result = null;
		
		if(isThisJavaVersionAboveVersion(Version.factory.newVersion("1.7"))){
			return new CommandResult(Type.ERROR, "Java Version shall be at least 1.7 or above.");
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
			
			// secondly bis, configure the CommandResult with the application name
			CommandResult.applicationName.set(appDescription.appDetails.applicationName);
			
			// Thirdly add an application shutter
			newApplicationShutter(appDescription, converterManager);
			
			// Fourth and last, launch the command if any
			if(args == null){
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
				result = appDescription.commandLineComputer.computeCommandLine(args);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static boolean isThisJavaVersionAboveVersion(Version v) {
		// get the current version
		Version version = SystemTools.getCurrentJavaVersion();
		// remove release candidate version
		
		
		// compare with the requested and return the result
		return version.isThisVersionLowerThan(v);
	}

	protected static ApplicationDescription newApplicationDescription(Object appInstance, Class<?> applicationType, ConverterManager converterManager) {
		return new ApplicationDescription(appInstance, applicationType, converterManager);
	}
	
	protected static Thread newApplicationShutter(final ApplicationDescription appDesc, final ConverterManager converterManager){
		Thread t = new Thread(appDesc.appDetails.applicationName + " shutter") {
			@Override
			public void run() {
				// raise event if any observer
				ApplicationEvent observer = appDesc.applicationEvent;
				if(observer != null){
					observer.notifyApplicationToBeClosed();}

				// save all persistent properties if any
				AppPropertyInfo[] propList = appDesc.applicationProperties.getPersistentAppPropertyInfoList();

				if(propList.length > 0){
					saveAllPersistentProperties(appDesc, converterManager);}
			}
		};
		Runtime.getRuntime().addShutdownHook(t);
		
		return t;
	}

	public static void cleanApplicationProperties(Class<?> applicationType) {
		// first allocate the application
		Object appInstance = null;
		try {
			appInstance = applicationType.newInstance();

			// Compute all the application details
			ApplicationDetails d = ApplicationSubTool.newApplicationDetails(appInstance, applicationType);
		
			if(d.applicationPropertyFile != null){
				FileTools.deleteFile(d.applicationPropertyFile);}
			// else do nothing there is nothing to delete
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static void saveAllPersistentProperties(ApplicationDescription appDesc, ConverterManager converterManager) {

		Properties properties = new Properties();

		try {
			for (AppPropertyInfo prop : appDesc.applicationProperties.persistentPropList) {
				Object obj = null;
				// get the value of the property from instance
				try {
					prop.field.setAccessible(true);
					obj = prop.field.get(prop.containerInstance);
				} catch (Exception e) {
					e.printStackTrace();
				}

				Converter<Object,String> converter = converterManager.getGenericOutConverter(prop.field.getType(), String.class);

				if (converter != null) {
					properties.setProperty(
							prop.propertyName, converter.convert(obj));
				} else {
					System.err
							.println("[ccOutils Application] Error : unable to save the property "
									+ prop.propertyName
									+ " with the unknown type : "
									+ obj.getClass());
				}
			}

			String propFileName = appDesc.appDetails.applicationPropertyFile;
			// check out if a property filename is provided
			// if not make a default one
			if(propFileName == null){
				propFileName = appDesc.appDetails.applicationName.replace(' ', '_') + ".properties";
			}
			
			// save properties to project root folder
			properties.store(new FileOutputStream( propFileName), null);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static ApplicationRunner newApplicationRunner() {
		return new ApplicationRunner();
	}
}