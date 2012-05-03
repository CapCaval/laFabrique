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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

import org.capcaval.tools.file.FileSeeker.FileSeekerResult;


public class FileTool {

	public enum FilePosition{ head, foot}
	
	static public void insertStringInFile(Path file, String strTobeInserted, FilePosition position) throws Exception {
		String str = FileTool.readStringfromFile(file);
		
		switch(position){
			case foot:
				str = str + strTobeInserted;
				break;
			default:
				str = strTobeInserted + str;
				break;
		}
		FileTool.writeFile(file, str);
		
	}
	
	static public String readStringfromFile(Path path) throws java.io.IOException{
		// get raw data file
		byte[] buffer = Files.readAllBytes(path);
	    // put it inside a string
	    return new String(buffer);
	}
	
	static public String readStringfromFile(String pathStr) throws java.io.IOException{
		// get raw data file
		byte[] buffer = readByteArrayfromFile(pathStr);
	    // put it inside a string
	    return new String(buffer);
	}
	
	static public byte[] readByteArrayfromFile(String pathStr) throws java.io.IOException{
		Path path = Paths.get(pathStr);
		// get raw data file
		byte[] buffer = Files.readAllBytes(path);
	    // put it inside a string
	    return buffer;
	}
	
	static public void writeFile(String pathStr, byte[] byteArray) throws IOException{
		// use a path 
		Path path = Paths.get(pathStr);
		
		// write
		writeFile(path, byteArray);
	}
	
	static public void writeFile(Path path, byte[] byteArray) throws IOException{
		// create directory if needed
		Files.createDirectories(path.getParent());
		// write file over writte if necessary
		Files.write(path, byteArray, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	}
	
	static public void writeFile(Path path, String value) throws IOException{
		// write string with byte array method
		writeFile(path, value.getBytes());
	}
	
	static public void writeFile(String pathStr, String value) throws IOException{
		// write string with byte array method
		writeFile(pathStr, value.getBytes());
	}
	
	static public void deleteFile(String pathStr) throws IOException{
		// use a path 
		Path path = Paths.get(pathStr);
		// delete it
		Files.delete(path);
	}
	
	
	public static FileSeekerResult seekFiles(String pattern, Path startingDir) throws IOException{
		FileSeeker fileSeeker = new FileSeeker(pattern);
		FileSeekerResult result = fileSeeker.seek(startingDir);
		return result;
	}
//	static public byte[] readFileAsByteArray(Path path)
//			throws java.io.IOException {
//		// get file from path
//		File file = path.toFile();
//		// allocate the buffer to be read
//		byte[] buffer = new byte[(int) file.length()];
//		BufferedInputStream bis = null;
//		try {
//			// create BufferedInputStream to read file
//			bis = new BufferedInputStream(
//					new FileInputStream(file));
//			// copy the file data
//			bis.read(buffer);
//		} finally {
//			if (bis != null){
//				bis.close(); 
//			}
//		}
//		// return the filled buffer
//		return buffer;
//	}

	static public void replaceTokenInFile(Path templateFile, Path outputfile, Map<String, String> map, char startTokenChar, char endTokenChar) throws IOException {
		FileReader input = new FileReader(templateFile.toFile());

		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outputfile.toFile())));
		
		Writer writer = TokenTool.replaceTokenFromReader(input, map, startTokenChar, endTokenChar);
		output.write(writer.toString());
		output.close();
	}	
	
	static public List<String> getAllTokenFromFile(Path templateFile, char startTokenChar, char endTokenChar, int maxTokenCharSize) throws IOException {
		FileReader input = new FileReader(templateFile.toFile());
		
		return TokenTool.getAllTokenFromReader(input, startTokenChar, endTokenChar, maxTokenCharSize);
	}

	
}
