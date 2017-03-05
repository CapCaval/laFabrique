package org.capcaval.lafabrique.lang.object;

import org.capcaval.lafabrique.factory.FactoryTools;
import org.capcaval.lafabrique.factory.GenericFactory;
import org.capcaval.lafabrique.lang.object._impl.ObjectToolsImpl;

public interface ObjectTools {
	
	public static GenericFactory<ObjectTools>factory = FactoryTools.newGenericFactory(ObjectToolsImpl.class);
	
	public <T> T cloneObject(T object);
	public <T> T cloneObjectArray(Class<?> componentType, Object object);
	public <T> T clonePrimitiveArray(Class<?> componentType, Object object);
	public <T> T copyObject(T srcObject, T destObject);
	public byte[] objectToBinary(Object object);
	public <T> T binaryToObject(Class<T> type, byte[] binaryArray);
	
	public void objectToPropertyFile(Object object);
	public <T> T propertyFileToObject(Class<T> type);
}
