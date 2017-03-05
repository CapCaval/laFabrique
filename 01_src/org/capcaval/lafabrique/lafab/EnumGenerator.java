package org.capcaval.lafabrique.lafab;

import java.util.LinkedHashMap;

public class EnumGenerator {
	protected Class<?> type;

	public EnumGenerator(Class<? extends Enum<?>> enumType){
		this.type =enumType;
		
		LinkedHashMap<Object, Object> list = new LinkedHashMap<>();
		
		// allocate it
		try {
			Object[] array = this.type.getEnumConstants();
			
			for(Object obj : array){
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
