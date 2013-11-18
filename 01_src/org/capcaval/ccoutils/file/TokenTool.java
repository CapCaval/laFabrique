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
package org.capcaval.ccoutils.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.capcaval.ccoutils.lafabrique.command.CommandEclipseProject;
import org.capcaval.ccoutils.lang.StringTools;

enum TokenSeekState { seekStart, seekEnd}

public class TokenTool {
	
	static public Writer replaceTokenFromReader(Reader input, Map<String, String> map, char startTokenChar, char endTokenChar) throws IOException {
		return replaceTokenFromReader(input, map, startTokenChar, endTokenChar, 32);
	}

	static public String replaceTokenFromReader(String input, Map<String, String> map, char startTokenChar, char endTokenChar){
		return replaceTokenFromReader( input, map, startTokenChar, endTokenChar, 32);
	}

	
	static public String replaceTokenFromReader(String input, Map<String, String> map, char startTokenChar, char endTokenChar, int maxTokenCharSize){
		Reader source = new StringReader(input);
		Writer w = replaceTokenFromReader(source, map, startTokenChar, endTokenChar, maxTokenCharSize);
		
		return w.toString();
	}
	static public Writer replaceTokenFromReader(Reader input, Map<String, String> map, char startTokenChar, char endTokenChar, int maxTokenCharSize){
		PushbackReader pbreader = new PushbackReader(input);
		Writer output =  new StringWriter();
		
		StringBuilder  tokenName  = new StringBuilder();
		
		TokenSeekState state = TokenSeekState.seekStart;
		
		while (state != TokenSeekState.seekEnd) {
			int value = -1;
			try {
				value = pbreader.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(value == -1){
				state=TokenSeekState.seekEnd;
			}
			else if(value == startTokenChar){
				int counter = 0;
				boolean tokenValid = true;
				
				// get the token string or stop after 32 characters
				while(	(value != endTokenChar)&&
						(tokenValid == true) &&
						(counter<maxTokenCharSize)){
					tokenName.append((char)(value));
					counter++;
					// still read the token until the end char
					try {
						value = pbreader.read();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					if((value == '\n')||(value == '\t')||(value == ' ')){
						tokenValid = false;
					}
				}
				// check that the token exist if yes replace by the value
				String tokenValue = map.get(tokenName.substring(1));
				
				if(tokenValue != null){
					try {
						output.append(tokenValue);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					try {
						// this is not a real token copy it normally
						output.append(tokenName);
						output.append(new String(Character.toChars(value))); // Character is used to keep unicode information
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				//reset all
				tokenName.delete(0, tokenName.length());
				counter = 0;
			}
			else{
				try {
					output.append((char)(value));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return output;
	}
	
	static public List<String> getAllTokenFromReader(Reader input, char startTokenChar, char endTokenChar, int maxTokenCharSize) throws IOException {
		PushbackReader pbreader = new PushbackReader(input);
		List<String> output =  new ArrayList<String>();
		
		StringBuilder  tokenName  = new StringBuilder();
		
		TokenSeekState state = TokenSeekState.seekStart;

		while (state != TokenSeekState.seekEnd) {
			int value = pbreader.read();
			
			if(value == -1){
				state=TokenSeekState.seekEnd;
			}
			else if(value == startTokenChar){
				int counter = 0;
				boolean tokenValid = true;
				// get the token string or stop after 32 characters
				while(	(value != endTokenChar)&&
						(tokenValid == true) &&
						(counter<maxTokenCharSize)){
					tokenName.append((char)(value));
					counter++;
					// still read the token until the end char
					value = pbreader.read();
					
					if((value == '\n')||(value == '\t')||(value == ' ')){
						tokenValid = false;
					}
				}
				// retrieve the token
				String token = tokenName.substring(1);
				// check that the token already exist if yes add it
				if((output.contains(token) == false)&&(tokenValid == true)){
					output.add(token);}
				
				//reset all
				tokenName.delete(0, tokenName.length());
				counter = 0;
			}
		} 

		return output;
	}

	public static String replaceBlocks(String templateStr,
			List<Map<String, String>> list, 
			String startBlock, String stopBlock,
			char startTokenChar, char stopTokenChar) {
		
		int startBlockIndex = templateStr.indexOf(startBlock);
		int endBlockIndex = templateStr.indexOf(stopBlock) + stopBlock.length();
		
		int startIndex = startBlockIndex + startBlock.length();
		int endIndex = templateStr.indexOf(stopBlock);
		// Get the block
		String blockStr = templateStr.substring(startIndex, endIndex);
		
		// clean the template
		templateStr = StringTools.deleteCharacters(templateStr, startBlockIndex, endBlockIndex);
		
		for(Map<String, String> map : list){
			//map.
			Set<String> keyList = map.keySet();
			String newBlock = new String(blockStr);
			
			for(String key : keyList){
				// get the value to insert the string
				String val = map.get(key);
				// build the key
				String keyStr = startTokenChar + key + stopTokenChar;
				//replace the key
				//newBlock.replaceAll( Pattern.quote(keyStr), val);
				newBlock = newBlock.replace( keyStr, val);
			}
			// insert the new block
			templateStr = StringTools.insertCharacters(templateStr, newBlock, startBlockIndex);
		}
		
		return templateStr;
	}
	
	public static String replaceBlocksInsideFile(String file,
			List<Map<String, String>> list, 
			String startBlock, String stopBlock,
			char startTokenChar, char stopTokenChar) {
		
		StringBuilder templateStr = new StringBuilder();
		try {
			// old templateStr = FileTool.readStringfromFile(file);
			InputStream stream = CommandEclipseProject.class.getResourceAsStream(file);
			InputStreamReader reader = new InputStreamReader(stream);
			int val = reader.read();
			while( val != -1){
				// add the last read value
				templateStr.append((char)val);
				// check out for a new character
				val = reader.read();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return replaceBlocks(templateStr.toString(), list, startBlock, stopBlock, startTokenChar, stopTokenChar);
	}
}
