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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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

		try {
			buf.insert(index, strToBeInserted);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// concat the two string to do the job
		return buf.toString();
	}

	public static String multiLineString(String... stringlist) {
		String returnedValue = new String();
		for (String str : stringlist) {
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
		for (char c : exclusiveCharArray) {
			// add it twice even if the value is not used
			exclusiveCharMap.put(c, c);
		}

		// check all letter or before if the match is false
		for (int i = 0; i < stringToTest.length() && isMatch; i++) {
			char letter = stringToTest.charAt(i);
			// is the letter is one of those?
			if (exclusiveCharMap.containsKey(letter) == false) {
				isMatch = false;
			}
		}

		return isMatch;
	}

	public static String formatMultiLineToCharWidth(String[] stringArray, int charWidth) {
		StringBuffer formattedString = new StringBuffer();

		for (String line : stringArray) {
			String formattedLine = formatToCharWidth(line, charWidth);
			formattedString.append(formattedLine + "\n");
		}

		return formattedString.toString();
	}

	public static String formatToCharWidth(String string, int charWidth) {
		return formatToCharWidth(string, charWidth, "\n");
	}

	public static String formatToCharWidth(String inString, int charWidth, String stringToInsert) {
		for (int i = charWidth; i < inString.length(); i = i + charWidth) {
			// add a CR to format string
			inString = StringTools.insertCharacters(inString, stringToInsert, i);
			// as a new character has been added increase also the current index
			i = i + stringToInsert.length();
		}

		return inString;
	}

	public static String repeatString(String string, int repeatNb) {
		// pre-allocate the stringbuilder size
		StringBuilder repeatedString = new StringBuilder(string.length() * repeatNb);

		// add it n times
		for (int i = 0; i < repeatNb; i++) {
			repeatedString.append(string);
		}

		return repeatedString.toString();
	}

	public static String removeEnd(String endStr, String fullString) {
		String returnString = fullString;

		if (fullString.endsWith(endStr)) {
			int endSize = endStr.length();
			int fullSize = fullString.length();
			returnString = returnString.substring(0, fullSize - endSize);
		}

		return returnString;
	}

	public static String removeStart(String startStr, String fullString) {
		String returnString = fullString;

		if (fullString.startsWith(startStr)) {
			int startSize = startStr.length();
			returnString = returnString.substring(startSize);
		}

		return returnString;
	}

	public static String getFirstElement(String delimeter, String str) {
		String[] array = null;

		if (str.contains(delimeter)) {
			// split the string in an array
			array = str.split(Pattern.quote(delimeter));
		} else {
			throw new IllegalArgumentException("Error : " + str + " does not contain the delimeter " + delimeter);
		}
		return array[0];
	}

	public static String getLastElement(String delimeter, String str) {
		String[] array = null;

		if (str.contains(delimeter)) {
			// split the string in an array
			array = str.split(Pattern.quote(delimeter));
		} else {
			throw new IllegalArgumentException("Error : " + str + " does not contain the delimeter " + delimeter);
		}

		return array[array.length - 1];
	}

	public static String transformCharToUpperCase(String name, int i) {
		// get the char array
		char[] charArray = name.toCharArray();

		// get the requested char
		char c = charArray[i];

		// transform it to upper case
		c = Character.toUpperCase(c);

		// replace the new upper case char at the same place
		charArray[i] = c;

		// return a new string
		return new String(charArray);
	}

	public static String transformCharToLowerCase(String name, int i) {
		// get the char array
		char[] charArray = name.toCharArray();

		// get the requested char
		char c = charArray[i];

		// transform it to upper case
		c = Character.toLowerCase(c);

		// replace the new upper case char at the same place
		charArray[i] = c;

		// return a new string
		return new String(charArray);
	}

	public static String[] getLineArray(String lineStr) {
		String[] array = lineStr.split("\n");

		return array;
	}

	public static String getFirstLine(String lineStr) {
		String[] strArray = getLineArray(lineStr);

		String returnValue = null;

		// check out if it contains lines.
		if (strArray.length > 0) {
			returnValue = strArray[0];
		}

		return returnValue;
	}

	public static String getLastLine(String lineStr) {
		String[] strArray = getLineArray(lineStr);

		String returnValue = null;

		// check out if it contains lines.
		if (strArray.length > 0) {
			returnValue = strArray[strArray.length - 1];
		}

		return returnValue;
	}

	public static String getLineNumber(int lineNumber, String lineStr) {
		String[] strArray = getLineArray(lineStr);

		if ((lineNumber > strArray.length) || (lineNumber < 1)) {
			throw new RuntimeException("Error the line number " + lineNumber
					+ " is not correct. The line number shall be between 1 and " + strArray.length);
		}

		return strArray[lineNumber - 1]; // index start at 0 where line starts
											// at 1
	}

	public static String replaceString(String str, String oldValue, String newValue) {
		String retValue = str.replace(oldValue, newValue);

		return retValue;
	}

	public static String removeFirstCharactersPerLine(String str, char... charArray) {
		// get the multi line
		String[] lineArray = str.split("\n");

		StringBuilder strb = new StringBuilder();

		// remove requested characters for all lines
		for (String line : lineArray) {
			String lineStr = removeFirstCharactersOnString(line, charArray);
			strb.append(lineStr);
		}

		return strb.toString();
	}

	public static String removeFirstCharactersOnString(String str, char... charArray) {
		List<Character> list = new ArrayList<>(charArray.length);

		for (char c : charArray) {
			list.add(c);
		}
		int i = 0;
		for (; i < str.length(); i++) {
			// get char
			char c = str.charAt(i);

			// it is a special one
			if (list.contains(Character.valueOf(c)) == false) {
				// it is over
				break;
			}
			// otherwise continue
		}
		// cut from where it has
		String retValue = str.substring(i, str.length());

		return retValue;
	}

	public static String compareStringForDebug(String src1, String src2) {
		StringMultiLine diff = new StringMultiLine("");

		// count the number of line
		int lineCount = 0;

		// split in lines
		String[] src1LineArray = StringTools.getLineArray(src1);
		String[] src2LineArray = StringTools.getLineArray(src2);

		int size = src1LineArray.length;
		int size2 = src2LineArray.length;

		if (size2 > size) {
			size = size2;
		}

		for (int i = 0; i < size; i++) {
			String line1 = src1LineArray[i];

			String line2 = "";
			if (size2 >= i) {
				line2 = src2LineArray[i];
			}

			CompareStringResult result = compareLine(line1, line2);
			
			if(result.isEqual == false){
				diff.addLine(
						"There is a difference at line " + (i+1) + " and at position " + (result.getDiffIndex()+1),
						line1,
						line2,
						StringTools.repeatString(" ", result.getDiffIndex()) + "^"
						);
			}
		}

		return diff.toString();
	}

	private static CompareStringResult compareLine(String line1, String line2) {
		// by default not differences found
		CompareStringResult result = new CompareStringResult(true);
		
		int size = line1.length();
		int size2 = line2.length();

		// the size shall be the longest
		if (size2 > size) {
			size = size2;
		}

		for (int i = 0; i < size; i++) {
			char c = line1.charAt(i);
			char c2 = line2.charAt(i);

			if (c != c2) {
				// there is a diff 
				result = new CompareStringResult(false, i); 
				// found go out
				break;
			}
		}
		return result;
	}

	public static String shiftTextWithTab(String string) {
		// add a tab at first and
		// just replace new line by new line and tab
		return "\t" + string.replace("\n", "\n\t");
	}

	public static String removeCharSequence(char c, String str) {
		StringBuilder strb = new StringBuilder();
		int length = str.length();
		if(length > 0){
			strb.append(str.charAt(0));
		}
		// start at one because the first char can not be remove
		for (int i = 1; i < length ; i++) {
			char car = str.charAt(i);
			char prev = str.charAt(i-1);
			if((car == c)&&(car == prev)){
				// do nothing
			}else{
				strb.append(car);
			}
		}
		return strb.toString();
	}

	public static String removeLastLine(String multiLine) {
		// default delimiter is CR
		return removeLastLine(multiLine, "\n");
	}
	
	public static String removeLastLine(String multiLine, String delimiter) {
		// split multiline with the delimiter
		String[] lineArray = multiLine.split(delimiter);
		
		// remove the last line
		lineArray = ArrayTools.removeElementAt(lineArray, lineArray.length);
		
		return StringTools.multiLineString(lineArray);
	}

	public static String removeFirstLine(String multiLine) {
		// default delimiter is CR
		return removeFirstLine(multiLine, "\n");
	}
	
	public static String removeFirstLine(String multiLine, String delimiter) {
		// split multiline with the delimiter
		String[] lineArray = multiLine.split(delimiter);
		
		// remove the last line
		lineArray = ArrayTools.removeElementAt(lineArray, 1);
		
		return StringTools.multiLineString(lineArray);
	}

}
