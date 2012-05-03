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
package org.capcaval.tools.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayUtil {
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

	
}
