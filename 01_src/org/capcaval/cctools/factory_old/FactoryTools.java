package org.capcaval.cctools.factory_old;

import java.lang.reflect.Field;


public class FactoryTools {
	public static <T> void injectNewFactoryImplmentation(Class<T> factoryType, Class<? extends T> implementationType){
		
	}
	
	public static <T> void injectNewFactoryImplmentation(Class<T> factoryType, T implementationInstance) throws IllegalArgumentException, IllegalAccessException{
		Field[] fieldList =  factoryType.getDeclaredFields();
		
		for(Field field : fieldList){
			boolean isFactory = field.isAnnotationPresent(FactoryInjector.class);
			if(isFactory == true){
				field.setAccessible(true);
				field.set(null, implementationInstance);
			}
		}
	}
}
