package org.capcaval.lafabrique.lang;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Properties;

import org.capcaval.lafabrique.converter.Converter;
import org.capcaval.lafabrique.converter.ConverterManager;

public class PropertyTools {
	
	
	public static void saveAllPersistentProperties(Object obj, Path filePath, ConverterManager converterManager) {
		// get the object type
		Class<?> type = obj.getClass();
		// get all the attributes
		Field[] fieldArray = type.getDeclaredFields();
		
		Properties properties = new Properties();

		try {
			for (Field field : fieldArray) {
				if (field.getName().equals("this$0") == false) {

					Object fieldValue = null;
					// get the value of the property from instance
					try {
						field.setAccessible(true);
						fieldValue = field.get(obj);
					} catch (Exception e) {
						e.printStackTrace();
					}

					Converter<Object, String> converter = converterManager.getGenericOutConverter(field.getType(),
							String.class);

					if (converter != null) {
						String name = field.getName();
						String valueStr = converter.convert(fieldValue);

						properties.setProperty(name, valueStr);
					} else {
						System.err.println("[laFabrique PropertyTools] Error : unable to save the property "
								+ field.getName() + " with the unknown type : " + fieldValue.getClass());
					}
				}
			}
			
			// save properties to project root folder
			properties.store(new FileOutputStream( filePath.toFile()), null);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
