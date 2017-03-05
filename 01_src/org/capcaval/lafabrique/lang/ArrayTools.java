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
package org.capcaval.lafabrique.lang;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayTools {
	static public boolean contains(char[] charList, char cSearch){
		boolean isFound = false;

		for(char c : charList){
			if(cSearch==c){
				// gotcha return true
				isFound = true;
				// go out when finish
				break;
			}
		}
		
		return isFound;
	}
	
	static public <T> List<T> newArrayList(T... objectList){
		//allocate the array at the correct size
		List<T> list = new ArrayList<T>(objectList.length);
		
		// add all element inside
		for(T obj : objectList){
			list.add(obj);
		}
		// this is it bye
		return list;
	}

	@SuppressWarnings("unchecked")
	static public <T> T[] newArray(T... objectList){
		// this is it bye
		return objectList;
	}

	@SuppressWarnings("unchecked")
	static public <T> Map<T,T> newMap(T... objectList){
		//allocate the array at the correct size
		Map<T,T> map = new HashMap<T,T>(objectList.length/2);
		
		// add all element inside
		for(int i =0; i<objectList.length; i=i+2){
			map.put( objectList[i], objectList[i+1]);
		}
		// this is it bye
		return map;
	}

	
	static public <T> List<T> newArrayList(T[][] objectList){
		//allocate the array at the correct size
		List<T> list = new ArrayList<T>(objectList.length);
		
		// add all element inside
		for(T[] objSubList : objectList){
			for(T obj : objSubList){
				list.add(obj);
				}
		}
		// this is it bye
		return list;
	}

	@SuppressWarnings("unchecked")
	static public <T> T[] convertToArray2(List<T> list, Class<T> type){
		return (T[])convertToArray((List<Object>)list, (Class<?>)type);
	}
	
	static public Object convertToArray(List<Object> list, Class<?> type){
		Object object = null;
		int i = 0;
		
		try{
		if(type.isPrimitive()){
			if(type == int.class){
				int[] array = (int[])Array.newInstance(type, list.size());
				for( i=0; i<array.length; i++){
						array[i] = (int)list.get(i);
				}
				object = array;
			}
			else if(type == short.class){
				short[] array = (short[])Array.newInstance(type, list.size());
				for( i=0; i<array.length; i++){
					array[i] = (short)list.get(i);
				}
				object = array;
			}
			else if(type == byte.class){
				byte[] array = (byte[])Array.newInstance(type, list.size());
				for( i=0; i<array.length; i++){
					array[i] = (byte)list.get(i);
				}
				object = array;
			}
			else if(type == long.class){
				long[] array = (long[])Array.newInstance(type, list.size());
				for( i=0; i<array.length; i++){
					array[i] = (long)list.get(i);
				}
				object = array;
			}
			else if(type == char.class){
				char[] array = (char[])Array.newInstance(type, list.size());
				for( i=0; i<array.length; i++){
					array[i] = (char)list.get(i);
				}
				object = array;
			}
			else if(type == float.class){
				float[] array = (float[])Array.newInstance(type, list.size());
				for( i=0; i<array.length; i++){
					array[i] = (float)list.get(i);
				}
				object = array;
			}
			else if(type == double.class){
				double[] array = (double[])Array.newInstance(type, list.size());
				for( i=0; i<array.length; i++){
					array[i] = (double)list.get(i);
				}
				object = array;
			}
		}
		else{
			Object[] array = (Object[])Array.newInstance(type, list.size());
			// copy values
			for(i=0; i<array.length; i++){
				array[i] = list.get(i);
			}
			object = array;
		}
		}catch(Exception e){
			object = new ParamError(i, e, list.get(i));
		}
		
		return object;
	}
	
	public static <T> T[] removeElementAt(T[] array, int itemIndex) {		
		@SuppressWarnings("unchecked")
		T[] outArray = (T[]) Array.newInstance(array[0].getClass(), array.length-1);

		//copy before index
		if(itemIndex>1){
		System.arraycopy(
	    		array, // source
	    		0,  
	    		outArray, // destination array is the same
	    		0, // destination index
	    		itemIndex-1 // data length to be copied
	    		);
		}
		// copy after
		if(itemIndex<array.length){
			System.arraycopy(
		    		array, // source
		    		itemIndex,  
		    		outArray, // destination array is the same
		    		itemIndex-1, // destination index
		    		array.length-itemIndex // data length to be copied
		    		);
		}
		
	    return outArray;
	    
	}
	
	public static <T> T[] removeElement(T value, T[] array) {
		@SuppressWarnings({"rawtypes"})
	    List<T> result = new ArrayList(array.length-1);

	    for(T element : array)
	        if( value.equals(element) == false)
	            result.add(element);

		// retrieve the correct type
		Class<?>type = array.getClass().getComponentType();
	    
	    return result.toArray(((T[])Array.newInstance(type, 0)));//array);
	}


	
	public static String toStringWithDelimiter(char separator, List<Object> objectList){
		StringBuilder str = new StringBuilder();
		
		for(Object o : objectList){
			str.append(o.toString());
			if(o == objectList.get(objectList.size())){
				str.append(separator);
			}
		}
		return str.toString();
	}
	
	public static String toStringWithDelimiter(char separator, Object[] objectList){
		StringBuilder str = new StringBuilder();
		
		for(Object o : objectList){
			// force windows format to java one
			str.append(o.toString().replace("\\", "/"));
			if(o != objectList[objectList.length-1]){
				str.append(separator);
			}
		}
		return str.toString();
	}

	public static String toMultiLine(Object... objectList){
		StringBuilder str = new StringBuilder();
		String endLine = "\n";
		
		for(Object o : objectList){
			str.append(o.toString());
			str.append(endLine);
		}
		return str.toString();
	}

	public static String[] toStringWithPrefix(String prefix, Object[] objectList){
		List<String> strList = new ArrayList<>();
		
		for(Object o : objectList){
			strList.add(prefix + o.toString());
			
		}
		return strList.toArray(new String[0]);
	}

	public static String[] toStringWithPosfix(String postfix, Object[] objectList){
		List<String> strList = new ArrayList<>();
		
		for(Object o : objectList){
			strList.add(o.toString() + postfix);
			
		}
		return strList.toArray(new String[0]);
	}
	
	
	public static String toMultiLine(List<?> objectList){
		StringBuilder str = new StringBuilder();
		String endLine = "\n";
		
		for(Object o : objectList){
			str.append(o.toString());
			str.append(endLine);
		}
		return str.toString();
	}

	public static <T> T[] concat(T[] array1, T[] array2) {
		// check wrong values
		if((array1 == null)||(array1.length == 0)){
			return array2;
		}
		if((array2 == null)||(array2.length == 0)){
			return array1;
		}
		
		
		int size = array1.length + array2.length;
		Class<?>type = array1.getClass().getComponentType();
		
		final T[] result = (T[]) Array.newInstance(type, size);

		// copy the first array
		System.arraycopy(array1, 0, result, 0, array1.length);
		
		// copy the second array
		System.arraycopy(array2, 0, result, array1.length, array2.length);

		return result;
	}
	
	public static <T> T[] add(T value, T[] array) {
		// allocate an array if needed
		if(array == null){
			array = (T[])Array.newInstance(value.getClass(), 0);
		}
		
		// compute size
		int size = array.length + 1;
		// retrieve the correct type
		Class<?>type = array.getClass().getComponentType();
		// Allocate the new Array 
		final T[] result = (T[]) Array.newInstance(type, size);
		// Copy all the values
		System.arraycopy(array, 0, result, 0, array.length);
		
		// add the new value
		result[result.length-1] = value;
		
		return result;
	}

	public static <T> T[] getSubArray(T[] array, int startIndex, int endIndex) {
		// check index validy
		if((startIndex<0)||(startIndex>endIndex)||(startIndex + endIndex>array.length)){
			throw new ArrayIndexOutOfBoundsException( "Start index : " + startIndex + " end index : " + endIndex + " array size : " + array.length);
		}
		int size = endIndex - startIndex + 1;
		Class<?>type = array.getClass().getComponentType();
		// Allocate the new Array 
		final T[] result = (T[]) Array.newInstance(type, size);
		// Copy all the values
		System.arraycopy( array, startIndex, result, 0, size);
		
		
		return result;
	}
	
	public static byte[] getSubArray(byte[] array, int startIndex, int endIndex) {
		// array can not be null 
		if( array == null){
			throw new NullPointerException("Array can not null.");
		}
		
		// check index validy
		if((startIndex<0)||(startIndex>endIndex)||(startIndex + endIndex>array.length)){
			throw new ArrayIndexOutOfBoundsException( "Start index : " + startIndex + " end index : " + endIndex + " array size : " + array.length);
		}
		int size = endIndex - startIndex + 1;
		// Allocate the new Array 
		final byte[] result = new byte[size];
		// Copy all the values
		System.arraycopy( array, startIndex, result, 0, size);
		
		return result;
	}
	
	public static String byteArrayToHexaString(byte[] byteArray){
		StringBuffer str = new StringBuffer();
		
		for(byte b : byteArray){
			str.append(Integer.toHexString(b).substring(6).toUpperCase());
			str.append(" ");
		}
		
		return StringTools.removeEnd(" ", str.toString());
	}

	public static String byteArrayToCompactHexaString(byte[] byteArray) {
		StringBuffer str = new StringBuffer();
		
		for(byte b : byteArray){
			String byteStr = Integer.toHexString(b);
			// negative number
			if(byteStr.length()>2){
				byteStr = byteStr.substring(6);
			// one digit number < 10
			}else if(byteStr.length()<2){
				byteStr = "0" + byteStr;
			}
			
			str.append(byteStr.toUpperCase());
		}
		
		return str.toString();
	} 
	
	public static byte[] compactHexaStringToByteArray(String str) {
	    int size = str.length();
	    byte[] array = new byte[size / 2];
	    for (int i = 0; i < size; i += 2) {
	    	array[i/2] = (byte) ((Character.digit(str.charAt(i), 16) << 4)
	                             + Character.digit(str.charAt(i+1), 16));
	    }
	    return array;
	}

}
