package org.capcaval.lafabrique.lang;

import java.util.HashMap;
import java.util.Map;

public class BasicTypes {
	
	static protected BasicTypes instance = new BasicTypes();
	
	private Map<Class<?>, Integer> basicTypeMap;

	private Map<Class<?>, Integer> immutableBasicTypeMap;

	private BasicTypes() {
		this.basicTypeMap = new HashMap<>();

		this.basicTypeMap.put(Integer.class, 4); 
		this.basicTypeMap.put(Long.class, 8);
		this.basicTypeMap.put(Character.class, 2); 
		this.basicTypeMap.put(Float.class, 4);
		this.basicTypeMap.put(Double.class, 8); 
		this.basicTypeMap.put(Short.class, 2);
		this.basicTypeMap.put(Byte.class, 2);
		this.basicTypeMap.put(Boolean.class, 1);
		this.basicTypeMap.put(long.class, 8);
		this.basicTypeMap.put(short.class, 2); 
		this.basicTypeMap.put(int.class, 4); 
		this.basicTypeMap.put(double.class, 8);
		this.basicTypeMap.put(float.class, 4); 
		this.basicTypeMap.put(char.class, 2);
		this.basicTypeMap.put(byte.class, 2);
		this.basicTypeMap.put(boolean.class, 1);
		
		this.immutableBasicTypeMap = new HashMap<>(this.basicTypeMap);
		this.immutableBasicTypeMap.put(String.class, -1); //unknown size
	}
	
	private boolean isContained(Class<?> type){
		return this.basicTypeMap.get(type)==null?false:true;
	}
	
	public Integer getSize(Class<?> type) {
		return this.immutableBasicTypeMap.get(type);
	}
	
	public static boolean isBasicType(Class<?> type){
		return instance.isContained(type);
	}
	
	public static boolean isImmutableBasicType(Class<?> type){
		return instance.isContainedInsideImmutable(type);
	}

	private boolean isContainedInsideImmutable(Class<?> type) {
		return this.immutableBasicTypeMap.containsKey(type);
	}

	public static Integer getTypeSize(Class<?> type) {
		return instance.getSize(type);
	}

}
