package org.capcaval.lafabrique.lang.object._impl;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.capcaval.lafabrique.lang.BasicTypes;
import org.capcaval.lafabrique.lang.object.ObjectTools;

public class ObjectToolsImpl implements ObjectTools {

	public <T> T cloneObject(T object) {
		Class<?> type = object.getClass();
		// allocate a new instance
		Object newInstance = null;
		try {
			if (type.isArray()) {

				Class<?> componentType = type.getComponentType();

				if (componentType.isPrimitive() == true) {
					// this is primitive
					newInstance = this.clonePrimitiveArray(componentType, object);
				} else {
					// this is a POJO object
					newInstance = this.cloneObjectArray(componentType, object);
				}
				// copy all values
				copyObject(object, newInstance);

			} else {
				if (BasicTypes.isImmutableBasicType(type)) {
					// as there are immutable just point to it
					newInstance = object;
				} else {
					// it is a POJO
					newInstance = (T) type.newInstance();
					// copy all values
					copyObject(object, newInstance);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (T)newInstance;
	}

	public <T> T cloneObjectArray(Class<?> componentType, Object object) {
		T newInstance = null;

		Object[] inArray = (Object[]) object;
		newInstance = (T) Array.newInstance(componentType, inArray.length);
		Object[] outArray = (Object[]) newInstance;

		// clone all array's elements
		int i = 0;
		for (Object elementObj : inArray) {
			outArray[i++] = cloneObject(elementObj);
		}

		return newInstance;
	}

	public <T> T clonePrimitiveArray(Class<?> componentType, Object object) {
		T newInstance = null;

		if (componentType == int.class) {
			int[] array = (int[]) object;
			newInstance = (T) Arrays.copyOf(array, array.length);
		} else if (componentType == float.class) {
			float[] array = (float[]) object;
			newInstance = (T) Arrays.copyOf(array, array.length);
		} else if (componentType == double.class) {
			double[] array = (double[]) object;
			newInstance = (T) Arrays.copyOf(array, array.length);
		} else if (componentType == byte.class) {
			byte[] array = (byte[]) object;
			newInstance = (T) Arrays.copyOf(array, array.length);
		} else if (componentType == char.class) {
			char[] array = (char[]) object;
			newInstance = (T) Arrays.copyOf(array, array.length);
		} else if (componentType == long.class) {
			long[] array = (long[]) object;
			newInstance = (T) Arrays.copyOf(array, array.length);
		} else if (componentType == boolean.class) {
			boolean[] array = (boolean[]) object;
			newInstance = (T) Arrays.copyOf(array, array.length);

		}

		return newInstance;
	}

	public <T> T copyObject(T srcObject, T destObject) {
		Class<?> type = srcObject.getClass();

		try {

			// get all field
			for (Field field : type.getDeclaredFields()) {
				if (this.isFieldFinal(field) == true) {
					// do nothing on final fields
					// bye bye
					break;
				}

				field.setAccessible(true);

				Class<?> fieldType = field.getType();
				Object object = field.get(srcObject);

				if (BasicTypes.isImmutableBasicType(fieldType) == true) {
					// get field instance from the source
					field.set(destObject, object);
				} else {
					// clone the object
					Object clone = this.cloneObject(object);
					field.set(destObject, clone);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return destObject;
	}

	public boolean isFieldFinal(Field field) {
		int maskedValue = field.getModifiers() & Modifier.FINAL;
		return maskedValue == Modifier.FINAL;
	}

	@Override
	public byte[] objectToBinary(Object object) {
		// get size
		int size = this.sizeOf(object);
		// allocate the buffer
		ByteBuffer byteBuffer = ByteBuffer.allocate(size);
		// binarize the object
		return this.objectToBinary(byteBuffer, object);
	}
	
	public byte[] objectToBinary(ByteBuffer byteBuffer, Object object) {
		Class<?> type = object.getClass();

		// check if it a basic object
		if (BasicTypes.isImmutableBasicType(type)) {
			int byteSize = BasicTypes.getTypeSize(type);

			// special case string
			if (byteSize == -1) {
				String  str = (String)object;
				//byteBuffer = ByteBuffer.allocate(str.length()); // UTF 8
				byteBuffer.putInt(str.length());
				byteBuffer.put(str.getBytes(Charset.forName("UTF-16")));
			} else {
				//MLB byteBuffer = ByteBuffer.allocate(byteSize);

				if ((type == int.class) || (type == Integer.class)) {
					byteBuffer.putInt((int) object);
				} else if ((type == float.class) || (type == Float.class)) {
					byteBuffer.putFloat((float) object);
				} else if ((type == double.class) || (type == Double.class)) {
					byteBuffer.putDouble((double) object);
				} else if ((type == long.class) || (type == Long.class)) {
					byteBuffer.putLong((long) object);
				} else if ((type == char.class) || (type == Character.class)) {
					byteBuffer.putChar((char) object);
				} else if ((type == boolean.class) || (type == Boolean.class)) {
					byteBuffer.put((byte)object);
				} else if ((type == byte.class) || (type == Byte.class)) {
					byteBuffer.put((byte)object);
				}
			}
		}
		// check if it is a list
		else if (object instanceof List) {
			// cast the list
			List<?> list = (List<?>)object;
			// get size of the list
			// put size of the list
			byteBuffer.putInt(list.size());
			// put all other elements
			for(Object o : list){
				objectToBinary(byteBuffer, o);
			}
		}
		// check if it is an array
		else if (type.isArray()) {
			if (object instanceof int[]) {
				// cast the list
				int[] array = (int[])object;
				// put size of the list
				byteBuffer.putInt(array.length);
				// put all other elements
				for (int o : array) {
					objectToBinary(byteBuffer, o);
				}

			}else if(object instanceof double[]){
				// cast the list
				double[] array = (double[])object;
				// put size of the list
				byteBuffer.putInt(array.length);
				// put all other elements
				for (double o : array) {
					objectToBinary(byteBuffer, o);
				}

			}else if(object instanceof char[]){
				// cast the list
				char[] array = (char[])object;
				// put size of the list
				byteBuffer.putInt(array.length);
				// put all other elements
				for (char o : array) {
					objectToBinary(byteBuffer, o);
				}

			}else if(object instanceof short[]){
				// cast the list
				short[] array = (short[])object;
				// put size of the list
				byteBuffer.putInt(array.length);
				// put all other elements
				for (short o : array) {
					objectToBinary(byteBuffer, o);
				}

			}else if(object instanceof long[]){
				// cast the list
				long[] array = (long[])object;
				// put size of the list
				byteBuffer.putInt(array.length);
				// put all other elements
				for (long o : array) {
					objectToBinary(byteBuffer, o);
				}
			}else if(object instanceof float[]){
				// cast the list
				float[] array = (float[])object;
				// put size of the list
				byteBuffer.putInt(array.length);
				// put all other elements
				for (float o : array) {
					objectToBinary(byteBuffer, o);
				}
			}else if(object instanceof byte[]){
				// cast the list
				byte[] array = (byte[])object;
				// put size of the list
				byteBuffer.putInt(array.length);
				// put all other elements
				for (byte o : array) {
					objectToBinary(byteBuffer, o);
				}
				
			} else {
				// cast the list
				Object[] array = (Object[]) object;
				// put size of the list
				byteBuffer.putInt(array.length);
				// put all other elements
				for (Object o : array) {
					objectToBinary(byteBuffer, o);
				}
			}

		}
		// it is an aggregated object
		else {
			for (Field field : type.getDeclaredFields()) {
				field.setAccessible(true);
				try {
					Object fieldObject = field.get(object);
					System.out.println("Type :" + fieldObject.getClass());
					
					if(fieldObject.getClass().toString().contains("ObjectStreamField")){
						System.out.println("PB");
					}
						
						
					this.objectToBinary(byteBuffer, fieldObject);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return byteBuffer.array();
	}

	public int sizeOf(Object object) {
		Class<Object> type = (Class<Object>)object.getClass();
		return sizeOf(type, object);
	}

	
	public <T> int sizeOf(Class<T> type, T object) {
		// check if it is a basic type
		Integer size = BasicTypes.getTypeSize(type);
		
		if((size == null)||(size == -1)){
			size = new Integer(0);
			// special case -1 is a string
			if(type == String.class){
				String str = (String)object;
				size = str.length()*2 +4 +2; // UTF16 + 4 to store the string size +2 for 00 endstring 
			} else if(List.class.isAssignableFrom(type)==true){
				List<?> list = (List<?>)object;
				int length = list.size();
				
				Class<?> componentType = null;
				if(length>0){
					componentType = list.get(0).getClass();
					Integer componentSize = BasicTypes.getTypeSize(componentType);
					
					if(componentSize != null){
						for(Object o : list){
							size = size + sizeOf(o);
						}
					}
				}
				size = size + 4; // size of the lists length
			} else if(type.isArray()){
				int length = Array.getLength(object);
				Class<?> arrayType = type.getComponentType();
				Integer componentSize = BasicTypes.getTypeSize(arrayType);

				if((componentSize != null)&&(componentSize > 0)){ // check if there no size of if it an dynamic size as a string 
					size = componentSize * length;
				}
				else{ // cannot get the component size so do it member by member
					Object[] array = (Object[]) object;
					size=0;
					for(Object o : array){
						size = size + sizeOf(o);
					}
				}
				size = size + 4; // add the size of the array length
			}else{
				for(Field f : type.getDeclaredFields()){
					f.setAccessible(true);
					Object o = null;
					try {
						o = f.get(object);
					} catch (Exception e) {
						e.printStackTrace();
					}
					 
					size = size +  this.sizeOf(o);
				}
			}
		}
		return size;
	}

	@Override
	public <T> T binaryToObject(Class<T> type, byte[] binaryArray) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(binaryArray);
		return binaryToObject(type, byteBuffer);
	}
	
	public <T> T binaryToObject(Class<T> type, ByteBuffer byteBuffer) {
		Object object = null;
		// check if it a basic object
		if (BasicTypes.isImmutableBasicType(type)){
			int byteSize = BasicTypes.getTypeSize(type);

			// special case string
			if (byteSize == -1) {
				
				// get size
				int size = byteBuffer.getInt();
				//allocate the string byte array
				byte[] strArray = new byte[(size+1)*2];
				// get all data
				byteBuffer.get(strArray);
				
				object = new String(strArray, Charset.forName("UTF-16"));
			} else {
				if ((type == int.class) || (type == Integer.class)) {
					object = byteBuffer.getInt();
				} else if ((type == float.class) || (type == Float.class)) {
					object = byteBuffer.getFloat();
				} else if ((type == double.class) || (type == Double.class)) {
					object = byteBuffer.getDouble();
				} else if ((type == long.class) || (type == Long.class)) {
					object = byteBuffer.getLong();
				} else if ((type == char.class) || (type == Character.class)) {
					object = byteBuffer.getChar();
				} else if ((type == boolean.class) || (type == Boolean.class)) {
					object = byteBuffer.get()==-1?false:true;
				} else if ((type == byte.class) || (type == Byte.class)) {
					object = byteBuffer.get();
				}
			}
		}else{
			// allocate the object
			try {
				if(type.isArray()){
					// get array Type
					Class<?>componentType = type.getComponentType();
					// get the length
					int arraySize = byteBuffer.getInt();
					// create the array
					object = Array.newInstance(componentType, arraySize);
					
					Object[] array = (Object[])object;
					
					for(int i=0; i< array.length; i++){
						array[i]=binaryToObject(componentType, byteBuffer);
					}
				}else{
					// it is a simple object
					object = type.newInstance();
				}

				for (Field field : type.getDeclaredFields()) {
					field.setAccessible(true);
					Object value = this.binaryToObject(field.getType(), byteBuffer);
					field.set(object, value);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return (T)object;
	}

	@Override
	public void objectToPropertyFile(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T propertyFileToObject(Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

}

// public static double toDoubleWithBB(byte[] bytes) {
// return ByteBuffer.wrap(bytes).getDouble();
// }
//
// public static byte[] convertString(String val){
// return val.getBytes(Charset.forName("UTF-8"));
// }
//
//
// public static String convertByteArrayToString(byte[] array){
// return new String(array,Charset.forName("UTF-8"));
// }

