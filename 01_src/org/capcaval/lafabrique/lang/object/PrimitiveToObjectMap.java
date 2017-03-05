package org.capcaval.lafabrique.lang.object;

import java.util.HashMap;
import java.util.Map;

public class PrimitiveToObjectMap{
	
	Map<Class<?>, Class<?>> primToObjectMap = new HashMap<Class<?>, Class<?>>();
	Map<Class<?>, Class<?>> objectToPrimMap = new HashMap<Class<?>, Class<?>>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PrimitiveToObjectMap() {
		this.addPrimToObject( int.class, Integer.class);
		this.addPrimToObject( double.class , Double.class);
		this.addPrimToObject( float.class , Float.class);
		this.addPrimToObject( long.class , Long.class);
		this.addPrimToObject( boolean.class , Boolean.class);
		this.addPrimToObject( char.class , Character.class);
		this.addPrimToObject( short.class , Short.class);
		this.addPrimToObject( byte.class , Byte.class);
	}
	
	public void addPrimToObject(Class<?> primType, Class<?> objType){
		// keep the prim to object link
		this.primToObjectMap.put( primType, objType);
		
		// keep also the object tp prim link
		this.objectToPrimMap.put( objType, primType);
	}

	public boolean isPrimitive(Class<?> type){
		return this.primToObjectMap.containsKey(type);
	}
	
	public boolean isObjectWithPrimitive(Class<?> type){
		return this.objectToPrimMap.containsKey(type);
	}
	
	public Class<?> getPrimTypeFromObjectType(Class<?> objType){
		return this.objectToPrimMap.get(objType);
	}

	public Class<?> getObjectTypeFromPrimType(Class<?> primType){
		return this.primToObjectMap.get(primType);
	}
}
