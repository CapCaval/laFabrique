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
package org.capcaval.ccoutils.lang;

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


	static public <T> T[] newArray(T... objectList){
		// this is it bye
		return objectList;
	}

	
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
			object = new ArrayError(i, e, list.get(i));
		}
		
		return object;
	}
	
	public static void removeElement(Object[] array, int itemIndex) {
	    System.arraycopy(
	    		array, // source
	    		itemIndex+1,  
	    		array, // destination array is the same
	    		itemIndex, // destination index
	    		array.length-1-itemIndex // data length to be copied
	    		);
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
			str.append(o.toString());
			if(o == objectList[objectList.length-1]){
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

}
