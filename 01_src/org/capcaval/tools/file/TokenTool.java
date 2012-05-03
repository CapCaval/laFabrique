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
package org.capcaval.tools.file;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

enum TokenSeekState { seekStart, seekEnd}

public class TokenTool {
	
	static public Writer replaceTokenFromReader(Reader input, Map<String, String> map, char startTokenChar, char endTokenChar) throws IOException {
		return replaceTokenFromReader(input, map, startTokenChar, endTokenChar, 32);
	}
	
	static public Writer replaceTokenFromReader(Reader input, Map<String, String> map, char startTokenChar, char endTokenChar, int maxTokenCharSize) throws IOException {
		PushbackReader pbreader = new PushbackReader(input);
		Writer output =  new StringWriter();
		
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
				// check that the token exist if yes replace by the value
				String tokenValue = map.get(tokenName.substring(1));
				
				if(tokenValue != null){
					output.append(tokenValue);
				}else{
					// this is not a real token copy it normally
					output.append(tokenName);
					output.append(new String(Character.toChars(value))); // Character is used to keep unicode information
				}
				//reset all
				tokenName.delete(0, tokenName.length());
				counter = 0;
			}
			else{
				output.append((char)(value));
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


}
