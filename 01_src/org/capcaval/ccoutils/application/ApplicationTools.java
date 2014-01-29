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
package org.capcaval.ccoutils.application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.capcaval.ccoutils.application._test.GreeterApp;
import org.capcaval.ccoutils.common.CommandResult;
import org.capcaval.ccoutils.common.CommandResult.Type;
import org.capcaval.ccoutils.converter.Converter;
import org.capcaval.ccoutils.converter.ConverterManager;
import org.capcaval.ccoutils.file.FileTools;
import org.capcaval.ccoutils.lang.SystemTools;
import org.capcaval.ccoutils.lang.Version;

public class ApplicationTools {

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

	public static CommandResult runApplication(Class<?> applicationType, String... args) {
		return runApplication(applicationType, args, (Converter<?,?>[])null);
	}
	
	public static CommandResult runApplication(Class<?> applicationType, String[] args, Converter<?,?>... converterList) {
		CommandResult result = null;
		
		if(isJavaVersionAboveVersion(Version.factory.newVersion("1.6"))){
			return new CommandResult(Type.ERROR, "Java Version shall be at least 1.7 or above.");
		}
		
		try {
			// first allocate the application
			Object appInstance = applicationType.newInstance();
			
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
			CommandResult.applicationName = appDescription.appDetails.applicationName;
			
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

	private static boolean isJavaVersionAboveVersion(Version v) {
		Version version = SystemTools.getCurrentJavaVersion();
		System.out.println(v);
		System.out.println(version);
		return version.isHigherVersionThan(v);
	}

	protected static ApplicationDescription newApplicationDescription(Object appInstance, Class<?> applicationType, ConverterManager converterManager) {
		return new ApplicationDescription(appInstance, applicationType, converterManager);
	}
	
	protected static void newApplicationShutter(final ApplicationDescription appDesc, final ConverterManager converterManager){
		Runtime.getRuntime().addShutdownHook(new Thread() {
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
		});
	}

	public static void cleanApplicationProperties(Class<GreeterApp> applicationType) {
		// first allocate the application
		Object appInstance = null;
		try {
			appInstance = applicationType.newInstance();

			// Compute all the application details
			ApplicationDetails d = ApplicationSubTool.newApplicationDetails(appInstance, applicationType);
		
			FileTools.deleteFile(d.applicationPropertyFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}