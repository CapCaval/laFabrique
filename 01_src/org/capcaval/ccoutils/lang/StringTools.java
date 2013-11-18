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

import java.util.HashMap;
import java.util.Map;


public class StringTools {
	public static String removeChar(String s, char... charList) {

		String result = "";
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (ArrayTools.contains(charList, c) == false) {
				result = result + c;
			}
		}
		return result;
	}
	
	public static String deleteCharacters(String str, int indexStart, int startEnd) {
		// get the string before and after the two indexes
		String beforeString = str.substring(0, indexStart);
		String afterString = str.substring(startEnd, str.length());
		
		// concat the two string to do the job
		return beforeString + afterString;
	}

	public static String insertCharacters(String str, String strToBeInserted, int index) {
		// get the string before and after the two indexes
		StringBuffer buf = new StringBuffer(str);
		
		buf.insert(index, strToBeInserted);
		
		// concat the two string to do the job
		return buf.toString();
	}
	
	public static String multiLineString(String...stringlist){
		String returnedValue = new String();
		for(String str : stringlist){
			returnedValue = returnedValue + str + "\n";
		}
		
		return returnedValue;
	}

	public static boolean isExclusiveMadeOf(String stringToTest, String exclusiveChars) {
		return isExclusiveMadeOf(stringToTest, exclusiveChars.toCharArray());
	}

	public static boolean isExclusiveMadeOf(String stringToTest, char... exclusiveCharArray) {
	    boolean isMatch = true; 
	    
	    Map<Character, Character> exclusiveCharMap = new HashMap<>();
	    // fill it
	    for(char c : exclusiveCharArray){
	    	// add it twice even if the value is not used
	    	exclusiveCharMap.put(c, c);
	    }

	    // check all letter or before if the match is false
	    for (int i = 0; i < stringToTest.length() && isMatch; i++) {
	        char letter = stringToTest.charAt(i);
	        // is the letter is one of those?
	        if(exclusiveCharMap.containsKey(letter)== false){
	        	isMatch = false;}
	    }
		
		return isMatch;
	}

	public static String formatMultiLineToCharWidth(String[] stringArray, int charWidth) {
		StringBuffer formattedString = new StringBuffer();
		
		for(String line : stringArray){
			String formattedLine = formatToCharWidth(line, charWidth);
			formattedString.append(formattedLine + "\n");
		}
		
		return formattedString.toString();
	}
	
	public static String formatToCharWidth(String string, int charWidth) {
		return formatToCharWidth(string, charWidth, "\n");
	}

	public static String formatToCharWidth(String inString, int charWidth, String stringToInsert) {
		for(int i=charWidth; i<inString.length(); i=i+charWidth){
			// add a CR to format string
			inString = StringTools.insertCharacters(inString, stringToInsert, i);
			// as a new character has been added increase also the current index
			i = i + stringToInsert.length();
		}
		
		return inString;
	}

	
	public static String repeatString(String string, int repeatNb) {
		// pre-allocate the stringbuilder size
		StringBuilder repeatedString = new StringBuilder(string.length()*repeatNb);
		
		// add it n times
		for(int i=0; i<repeatNb; i++){
			repeatedString.append(string);
		}
		
		return repeatedString.toString();
	}

}
