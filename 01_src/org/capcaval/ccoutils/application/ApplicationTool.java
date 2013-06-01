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

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.nio.file.Path;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.capcaval.ccoutils.application.annotations.AppInformation;
import org.capcaval.ccoutils.application.annotations.AppProperty;
import org.capcaval.ccoutils.application.objectfactory.ColorFromStringValue;
import org.capcaval.ccoutils.application.objectfactory.DoubleFromStringValue;
import org.capcaval.ccoutils.application.objectfactory.FloatFromStringValue;
import org.capcaval.ccoutils.application.objectfactory.IntegerFromStringValue;
import org.capcaval.ccoutils.application.objectfactory.ObjectFactoryFromStringValue;
import org.capcaval.ccoutils.application.objectfactory.PathFromStringValue;
import org.capcaval.ccoutils.application.objectfactory.StringFromStringValue;



public class ApplicationTool {

	protected Map<Class<?>, ObjectFactoryFromStringValue<?>> objectFactoryMap = new IdentityHashMap<Class<?>, ObjectFactoryFromStringValue<?>>();

	public ApplicationTool(final ApplicationState application, String[] args) {
		// create all the object factory for the main type of objects
		this.createAllDefaultObjectFactories();
	}
	
	public void addNewObjectFactory(Class<?> objectFactoryType, ObjectFactoryFromStringValue<?> factory ){
		// add it inside the map
		this.objectFactoryMap.put( objectFactoryType, factory);
	}
	
	protected void createAllDefaultObjectFactories()
	{
		DoubleFromStringValue doubleFromStringValue = new DoubleFromStringValue();
		this.objectFactoryMap.put(Double.TYPE, doubleFromStringValue);
		this.objectFactoryMap.put(Double.class, doubleFromStringValue);

		FloatFromStringValue floatFromStringValue = new FloatFromStringValue();
		this.objectFactoryMap.put(Float.TYPE, floatFromStringValue);
		this.objectFactoryMap.put(Float.class, floatFromStringValue);

		IntegerFromStringValue intFromStringValue = new IntegerFromStringValue();
		this.objectFactoryMap.put(Integer.TYPE, intFromStringValue);
		this.objectFactoryMap.put(Integer.class, intFromStringValue);

		this.objectFactoryMap.put(Path.class, new PathFromStringValue());
		this.objectFactoryMap.put(String.class, new StringFromStringValue());
		this.objectFactoryMap.put(Color.class, new ColorFromStringValue());
	}

	private String getAllPropertiesDescription(ApplicationDescription appDesc) {
		StringBuffer outStr = new StringBuffer();


		outStr.append("**************************************************************************\n");
		outStr.append("Application Name : " + appDesc.applicationName
				+ "\n");
		if(appDesc.applicationVersion != null){
			outStr.append("Version : " + appDesc.applicationVersion + "\n");
		}
		outStr.append("About : " + appDesc.applicationInformation + "\n");
		outStr.append("-----------------------------------------------------------------------\n");
		outStr.append("The available properties are :\n");
		for (AppPropertyInfo prop : appDesc.getAppPropertyContainer().allAppPropertyInfoList) {
			outStr.append(prop.toString());
		}

		outStr.append("**************************************************************************\n");

		return outStr.toString();
	}

	ApplicationDescription seekAndSetProperties(ApplicationState application, ApplicationDescription inOutAppDesc) {
		// allocate a container to return the result
		AppPropertyContainer outContainer= inOutAppDesc.getAppPropertyContainer();
		
		Properties propFromFile = new Properties();
		try {
			if(( inOutAppDesc.applicationPropertyFile != null)&&
					(doesItContainPersistentProperties(inOutAppDesc.appPropertyContainer) == true)&&
					(inOutAppDesc.applicationPropertyFile.isEmpty() == false)){
				FileInputStream fileStream = new FileInputStream(inOutAppDesc.applicationPropertyFile);
				
				//load a properties file
				propFromFile.load(fileStream);}
		}catch (Exception e) {
			System.err.println("[C³ Application] Error : cannot load the property file " + inOutAppDesc.applicationPropertyFile);
			
		}
		
		Class<?> applicationType = application.getClass();
		// Seek all the properties
		for (Field field : applicationType.getDeclaredFields()) {
			Annotation[] annotationList = field.getDeclaredAnnotations();
			for (Annotation annotation : annotationList) {

				if (annotation.annotationType() == AppProperty.class) {
					AppProperty prop = (AppProperty) annotation;
					String propertyName = null;
					// checkout if a value has been defined
					if (prop.isFullName() == true) {
						// full Name
						propertyName = field.getType().toString();
					} else {
						// short name
						propertyName = field.getName();
					}

					// get the type
					Class<?> type = field.getType();
					// add information
					outContainer.add(new AppPropertyInfo(propertyName, prop
							.comment(), type, prop.min(), prop.max(), 
							prop.persistence(),
							field, application));

					String stringValueFromFile =  propFromFile.getProperty(propertyName);
					String stringValue = System.getProperty(propertyName);
		
					if(stringValue == null){
						stringValue = stringValueFromFile;
					}

					// inject the value if any
					if (stringValue != null) {
						Object o = this.newObjectFromString(field, stringValue,
								prop.min(), prop.max());
						field.setAccessible(true);
						try {
							if( o != null){
								field.set(application, o);}
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		return inOutAppDesc;
	}
	
	protected boolean doesItContainPersistentProperties(
			AppPropertyContainer appPropertyContainer) {
		int listSize = appPropertyContainer.allPersistentAppPropertyInfoList.size();

		return listSize>0?true:false;
	}


	private Object newObjectFromString(Field field, String stringValue,
			double min, double max) {
		Object returnObj = null;
		Class<?> type = field.getType();

		// error string
		StringBuffer errorMessage = new StringBuffer();
		// get the object factory from a string value and a type
		ObjectFactoryFromStringValue<?> factory = objectFactoryMap.get(type);

		if (factory != null) {
			try{
			returnObj = factory.newObjectFromStringValue(stringValue,
					errorMessage);}
			catch(Exception e){
				// put the orignal value
				returnObj = null;
				errorMessage.append("On the property " + field.getName() + " with the type "+ type.getSimpleName() +" cannot be set with this value : " + stringValue);
			}
		} else if (type.isEnum() == true) {
			try {
				// special case for enum type
				Enum e = Enum.valueOf((Class) type, stringValue);
				returnObj = e;
			} catch (Exception e) {
				// put the orignal value
				returnObj = null;
				errorMessage.append("On the property " + field.getName() + " the enum "+ type.getSimpleName() +" do not have the following constant : " + stringValue);
			}
		} else {
			// there is no factory to build this kind of objects
			errorMessage
					.append("The type "
							+ type
							+ " is not handle by the application. You can create a new ObjectFactoryFromStringValue handling and add it to the Application.");
		}


		if (errorMessage.length() > 0) {
			// error has been raised display them
			System.err.println("[C³ Application] Error on attribut "
					+ field.getName() + " : " + errorMessage);
		}
		else{
			checkMinAndMax(returnObj, min, max, errorMessage);
		}

		return returnObj;
	}

	private void checkMinAndMax(Object returnObj, double min, double max,
			StringBuffer errorMessage) {
		// check if min and max value is used
		if (Double.isNaN(min) == false) {
			double dvalue = convertObjToDouble(returnObj);
			if (dvalue < min) {
				errorMessage.append("the property value  " + returnObj
						+ " is under the min " + min);
			}
		}
		if (Double.isNaN(max) == false) {
			double dvalue = convertObjToDouble(returnObj);
			if ((Double) dvalue > max) {
				errorMessage.append("the property value is " + returnObj
						+ " which is above the max " + max);
			}
		}

	}

	private double convertObjToDouble(Object obj) {
		double returnValue = Double.NaN;
		if (Double.class.isAssignableFrom(obj.getClass())) {
			returnValue = (Double) obj;
		} else if (Float.class.isAssignableFrom(obj.getClass())) {
			returnValue = (Float) obj;
		} else if (Integer.class.isAssignableFrom(obj.getClass())) {
			returnValue = (Integer) obj;
		} else if (Short.class.isAssignableFrom(obj.getClass())) {
			returnValue = (Short) obj;
		} else if (Byte.class.isAssignableFrom(obj.getClass())) {
			returnValue = (Byte) obj;
		}

		return returnValue;
	}

	public String launchApplication(final ApplicationState application, final ApplicationDescription appDesc) {
		// FIRST STATE -- the application is launched the appProperties are ready 
		application.notifyApplicationToBeLaunched();
				
		// THIRD STATE -- the application Component have been allocated and are ready to be configured
		application.notifyApplicationToBeConfigured();
				
		// get the application description
		String appDescription = this.getAllPropertiesDescription( appDesc);
		
		// FOURTH STATE -- the application Components have been started
		application.notifyApplicationToBeRun(appDescription);
		
		// FIFTH STATE -- the application is closing notify the application and stop all components
		// register for detecting application stop
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// raise event
				application.notifyApplicationToBeClosed();
				
				// save all persistent properties if any
				AppPropertyInfo[] propList=appDesc.appPropertyContainer.getAllPersistentAppPropertyInfo();
				if(propList.length > 0){
					saveAllPersistentProperties(appDesc);}
			}
		});
		
		return "hello!";
	}
	
	
	protected void saveAllPersistentProperties(
			ApplicationDescription appDesc) {
		
		
    	Properties properties = new Properties();
    	StringBuffer errorMessage = null;
	
    	try {
    		for(AppPropertyInfo prop : appDesc.appPropertyContainer.allPersistentAppPropertyInfoList){
    			Object obj = null;
    			// get the value of the property from instance
    			try {
    				prop.field.setAccessible(true);
					obj = prop.field.get(prop.containerInstance);
				} catch (Exception e) {
					e.printStackTrace();
				}
    			
    			ObjectFactoryFromStringValue<Object> factory = this.getObjectStringFactory(obj.getClass());
    			
    			if( factory != null){
    				properties.setProperty(prop.propertyName, factory.newStringFromObjectValue(obj, errorMessage));}
    			else{
    				System.err.println("[C³ Application] Error : unable to save the property " + prop.propertyName + " with the unknown type : " + obj.getClass());
    			}
    		}
 
    		//save properties to project root folder
    		properties.store(new FileOutputStream(appDesc.applicationPropertyFile), null);
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
		

		
	}

	private ObjectFactoryFromStringValue<Object> getObjectStringFactory(Class<?> type) {
		ObjectFactoryFromStringValue<Object> factory = (ObjectFactoryFromStringValue<Object>)this.objectFactoryMap.get(type);
		
		if(factory == null){
			// check all the type if they are null sub call
			Set<Class<?>> typeList = this.objectFactoryMap.keySet();
			
			for(Class<?> keyType : typeList){
				if(keyType.isAssignableFrom(type) == true)
					return (ObjectFactoryFromStringValue<Object>)this.objectFactoryMap.get(keyType);
			}
			
		}
		return factory;
	}


	public void displayHelpToSysOut(ApplicationDescription appDesc) {
		String description = this.getAllPropertiesDescription(appDesc);
		System.out.println(description);
	}

	public ApplicationDescription newApplicationDescription( ApplicationState application) {
		// allocate the instance which keeps the application information
		ApplicationDescription appDesc = new ApplicationDescription();
		
		// get the type of the application to extract the annotations
		Class<?> applicationType = application.getClass();
		
		// get all the annotation information
		AppInformation info = (AppInformation) applicationType
				.getAnnotation(AppInformation.class);
		if(info!=null){
			// get all the information from annotations
			appDesc = this.getAppInformation(info, appDesc, applicationType);
		}else{
			//just set the name from the class name
			appDesc.applicationName = applicationType.getSimpleName();
		}
			
		// Application property file ----------------
		// set the default if no file defined
		if(appDesc.applicationPropertyFile.equals("")){
			appDesc.applicationPropertyFile = appDesc.applicationName+".properties";}

		return appDesc;
	}

	private ApplicationDescription getAppInformation(AppInformation info,
			ApplicationDescription appDesc, Class<?> applicationType) {
		// Application Name ----------------
		String appName = info.name();
		if(appName.length()==0){
			// if no property set use the class name
			appName = applicationType.getSimpleName();
		}
		// set it
		appDesc.applicationName = appName;
		
		// Application version ----------------
		String version = info.version();
		if(version.length()==0){
			// if no property set use the class name
			version = "None";
		}
		// set it
		appDesc.applicationVersion = version;
		
		// Application about ----------------
		String about = info.about();
		if(about.length()==0){
			// if no property set use the class name
			about = "None";
		}
		// set it
		appDesc.applicationInformation = about;
		
	
		return appDesc;
	}

	public void displayGHelpToSysOut(ApplicationDescription appDesc) {
		new GHelpApplication(appDesc);
	}
}
