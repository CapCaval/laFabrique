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
package org.capcaval.ccoutils.application;

import java.lang.reflect.Field;
import java.util.Arrays;

public class AppPropertyInfo {
	String propertyName;
	String comment;
	Class<?> type;
	double min;
	double max;
	boolean isPersistent = false;
	Field field;
	Object containerInstance;
	
	public AppPropertyInfo(String propertyName, String comment, Class<?> type, double min, double max, boolean isPersistent, Field field, Object containerInstance){
		this.propertyName = propertyName;
		this.comment = comment;
		this.type = type;
		this.min = min;
		this.max = max;
		this.isPersistent = isPersistent;
		this.field = field;
		this.containerInstance = containerInstance;
	}
	
	public String toString(){
		String enumValueList = null;
		if(this.type.isEnum()){
			enumValueList = " Enum values :"  + Arrays.asList(type.getEnumConstants()).toString();
		}
		String minMaxString = "";
		if((Double.isNaN(this.min) == false)&&(Double.isNaN(this.max) ==false)){
			minMaxString = "        min/max values : " + this.min + "/" + this.max;
		}
		
		return	"   " + this.propertyName + "\n" + 
				"        comment : " + this.comment + "\n" +
				"        type : " + this.type.getSimpleName() + (enumValueList==null?"":enumValueList) + "\n" +
				"        persistent : " + (this.isPersistent?"true":"false")+ "\n" +
				minMaxString; 
	}
}
