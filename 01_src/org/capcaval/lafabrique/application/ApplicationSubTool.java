package org.capcaval.lafabrique.application;

import java.io.FileInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.capcaval.lafabrique.application.ApplicationDetails.HelpDisplayDetails;
import org.capcaval.lafabrique.application.annotations.AppInformation;
import org.capcaval.lafabrique.application.annotations.AppProperty;
import org.capcaval.lafabrique.application.annotations.DefaultApplicationCommand;
import org.capcaval.lafabrique.commandline.CommandLineComputer;
import org.capcaval.lafabrique.commandline.CommandWrapper;
import org.capcaval.lafabrique.converter.Converter;
import org.capcaval.lafabrique.converter.ConverterManager;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lang.ClassTools;
import org.capcaval.lafabrique.lang.StringTools;
import org.capcaval.lafabrique.project.ProjectInfo;

public class ApplicationSubTool {

	static public ApplicationDetails getApplicationInformation(AppInformation info, Class<?> applicationType) {

		String appName = "";
		String appVersion = "";
		String appAuthor = "";
		String[] appAboutArray = {};
		String appPropertyFile = "";
		boolean asciiLogo = false;
		HelpDisplayDetails logoDetails = null;
		
		// set the project info name
		String classFileName = "prj/"+ applicationType.getSimpleName()+"ProjectInfo.java";
		
		// get the projectInfo
		ProjectInfo projectInfo = ClassTools.getFileNewInstance(classFileName);
		
		// if null check for a default one which is not link with a script
		if(projectInfo == null){
			projectInfo = ClassTools.getFileNewInstance("prj/Default.java");}
		
		// if build with laFabrique use the project info
		if(projectInfo != null){
			appVersion = projectInfo.getVersion();
			appAuthor = projectInfo.getAuthor();
		}
		
		// the annotation information override the project
		// information
		if (info != null) {
			String anotAppName = info.name();
			if(anotAppName.equals("")==false){
				appName = anotAppName;
			}
			
			String anotAppVersion = info.version();
			if(anotAppVersion.equals("")==false){
				appVersion = anotAppVersion;
			}

			String anotAuthor = info.author();
			if(anotAuthor.equals("")==false){
				appAuthor = anotAuthor;
			}

			appAboutArray = info.about();
			appPropertyFile = info.propertyFile();
			asciiLogo = info.asciiLogo();
		}

		// set default values
		// Application Name ----------------
		if (appName.equals("")) {
			// if no property set use the class name
			appName = applicationType.getSimpleName();
		}

		// Application version ----------------
		if (appVersion.equals("")) {
			// if no property set use the class name
			appVersion = "None";
		}

		// Application about ----------------
		if (appAboutArray.length == 0) {
			// if no property set use the class name
			appAboutArray = new String[] { "None" };
		}
		if (appAuthor.equals("")) {
			appAuthor = null;
		}

		String appAboutStr = StringTools.multiLineString(appAboutArray);

		// Application property file
		if (appPropertyFile.equals("")) {
			appPropertyFile = appName.replace(' ', '_') + ".properties";

			// check if the file exist
			if (FileTools.isFileExist(appPropertyFile) == false) {
				// if not set it to null
				appPropertyFile = null;
			}
		}

		if (asciiLogo == true) {

			String asciiLogoGradient = info.asciiLogoGradient();
			int asciiLogoWidthInChar = info.helpWidthInChar();

			logoDetails = new ApplicationDetails.HelpDisplayDetails(asciiLogoGradient, asciiLogoWidthInChar);
		}
		//

		// set it all
		ApplicationDetails appProp = new ApplicationDetails(appName, appVersion, appAboutStr, appPropertyFile,
				appAuthor, appAboutArray, logoDetails);

		return appProp;
	}

	public static ApplicationProperties newApplicationProperties(Object appInstance, Class<?> applicationType,
			String propertyFile, ConverterManager converterManager) {
		Properties propFromFile = null;

		if (propertyFile != null) {
			propFromFile = ApplicationSubTool.loadPropertiesFromFile(propertyFile);
		}

		List<AppPropertyInfo> propList = new ArrayList<>();

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
					propList.add(new AppPropertyInfo(propertyName, // name
							prop.comment(), // comment
							type, prop.min(), prop.max(), // min and max
							prop.persistence(), // is it persistent
							field, // keep also the field
							appInstance)); // keep the instance on the container

					String stringValueFromFile = null;
					if (propFromFile != null) {
						stringValueFromFile = propFromFile.getProperty(propertyName);
					}
					String stringValue = System.getProperty(propertyName);

					// "-D" property values overwrite the property file ones.
					// this is just a choice
					if (stringValue == null) {
						stringValue = stringValueFromFile;
					}

					// inject the value if any
					if (stringValue != null) {
						Object o = newObjectFromString(field, stringValue, prop.min(), prop.max(), converterManager);
						field.setAccessible(true);
						try {
							if (o != null) {
								field.set(appInstance, o);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		// allocate the instance to be returned
		ApplicationProperties appProperties = new ApplicationProperties(propList);

		return appProperties;
	}

	public static Properties loadPropertiesFromFile(String propertyFile) {
		Properties prop = new Properties();
		try {
			// check out if there is
			FileInputStream fileStream = new FileInputStream(propertyFile);

			// load a properties file
			prop.load(fileStream);
		} catch (Exception e) {
			System.err.println("[ccOutils Application] Error : cannot load the property file " + propertyFile);
		}

		return prop;
	}

	static protected Object newObjectFromString(Field field, String stringValue, double min, double max,
			ConverterManager converterManager) {
		Class<?> type = field.getType();

		Converter<String, ?> converter = converterManager.getConverter(String.class, type);

		if (converter == null) {
			throw new RuntimeException("[ccOutils Application] ERROR : the field " + field.getName()
					+ " which is a property is an unmanaged type : " + field.getType());
		}

		Object obj = converter.convert(stringValue);

		checkMinAndMax(field, obj, min, max);

		return obj;
	}

	static protected void checkMinAndMax(Object obj, Object obj2, double min, double max) {
		// check if min and max value is used
		if (Double.isNaN(min) == false) {
			double dvalue = convertObjToDouble(obj);
			if (dvalue < min) {
				throw new RuntimeException("[ccOutils Application] ERROR : the property value  " + obj
						+ " is under the min " + min);
			}
		}
		if (Double.isNaN(max) == false) {
			double dvalue = convertObjToDouble(obj);
			if ((Double) dvalue > max) {
				throw new RuntimeException("[ccOutils Application] ERROR : the property value is " + obj
						+ " which is above the max " + max);
			}
		}

	}

	static protected double convertObjToDouble(Object obj) {
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

	public static CommandWrapper getDefaultCommand(CommandLineComputer clc) {
		CommandWrapper[] commandList = clc.getCommandWrapperList();

		CommandWrapper defaultCommand = null;

		for (CommandWrapper cw : commandList) {
			DefaultApplicationCommand annotation = cw.method.getAnnotation(DefaultApplicationCommand.class);

			if (annotation != null) {
				// the default has been found
				defaultCommand = cw;
				break;
			}
		}

		return defaultCommand;
	}

	static public ApplicationDetails newApplicationDetails(Object application, Class<?> applicationType) {
		// get all the annotation information
		AppInformation info = (AppInformation) applicationType.getAnnotation(AppInformation.class);

		// retrieve all informations from annotations
		ApplicationDetails appInformation = ApplicationSubTool.getApplicationInformation(info, applicationType);

		return appInformation;
	}

}
