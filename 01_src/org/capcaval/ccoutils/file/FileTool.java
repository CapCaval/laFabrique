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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileTool {

	public enum FilePropertyEnum{ ___, x__, x_w, xr_, xrw, _r_, _rw, __w};
	
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
		Path path = Paths.get(new File(pathStr).toURI());
		
		// write
		writeFile(path, byteArray);
	}
	
	static public void writeFile(Path path, byte[] byteArray) throws IOException{
		// create directory if needed
		Files.createDirectories(path.getParent());
		// write file overwrite if necessary
		Files.write(path, byteArray, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	}
	
	static public void writeFile(Path path, String value, Charset charset) throws IOException{
		// create directory if needed
		Files.createDirectories(path.getParent());
		// build the list
		List<String> strList = new ArrayList<>();
		strList.add(value);
		// write file overwrite if necessary
		Files.write(path, strList, charset, 
				StandardOpenOption.CREATE, 
				StandardOpenOption.TRUNCATE_EXISTING);
	}

	static public void writeFile(String pathStr, String value, Charset charset) throws IOException{
		// build a path to wrtite on this file
		writeFile(Paths.get(pathStr), value, charset);
	}

	
	static public void writeFile(Path path, String value) throws IOException{
		// write string with byte array method
		writeFile(path, value, StandardCharsets.UTF_8);
	}

	
	static public void writeFile(String pathStr, String value) throws IOException{
		// write string with byte array method
		writeFile(pathStr, value.getBytes());
	}
	
	static public void deleteFile(String pathStr) throws IOException{
		// use a path 
		Path path = Paths.get(pathStr);
		FileTool.deleteFile(path);
	}

	static public void deleteFile(Path path) throws IOException{
		FileTool.deleteFile(path.toFile());
	}

	
	static public void deleteFile(File file) throws IOException{
		if(file.exists() == false){
			// do nothing bye bye
			return;
		}
		
		if(file.isDirectory()){
			File[] fileList = file.listFiles();
			
			for(File f : fileList){
				if(f.isDirectory()){
					// recursive method call
					FileTool.deleteFile(f);
					// delete the directory
					f.delete();
				}else{
					f.delete();
				}
			}
			// delete t
			file.delete();
		}
		else{
			// delete it
			file.delete();
		}
	}
	
	
	static public FileSeekerResult seekFiles(String pattern, Path... pathList) throws IOException{
		FileSeeker fileSeeker = new FileSeeker(pattern);
		// allocate a file result 
		FileSeekerResult result = new FileSeekerResult();
		for(Path path :  pathList){
			result = fileSeeker.seek( path, result);
		}
		return result;
	}

	static public void replaceTokenInFile(Path templateFile, Path outputfile, Map<String, String> map, char startTokenChar, char endTokenChar) throws IOException {
		FileReader input = new FileReader(templateFile.toFile());

		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outputfile.toFile())));
		
		Writer writer = TokenTool.replaceTokenFromReader(input, map, startTokenChar, endTokenChar);
		output.write(writer.toString());
		output.close();
	}	

	static public void replaceTokenInFile(InputStreamReader streamReader, Path outputfile, Map<String, String> map, char startTokenChar, char endTokenChar) throws IOException {

		// check out if directory has to be created
		File parentDir = outputfile.toFile().getParentFile(); 
		if(parentDir.exists() == false){
			// create them if not here
			parentDir.mkdirs();
		}
		
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outputfile.toFile())));
		
		Writer writer = TokenTool.replaceTokenFromReader(streamReader, map, startTokenChar, endTokenChar);
		output.write(writer.toString());
		output.close();
	}	

	
	
	static public List<String> getAllTokenFromFile(Path templateFile, char startTokenChar, char endTokenChar, int maxTokenCharSize) throws IOException {
		FileReader input = new FileReader(templateFile.toFile());
		
		return TokenTool.getAllTokenFromReader(input, startTokenChar, endTokenChar, maxTokenCharSize);
	}
	
	public static void copy(Path source, Path dest) {
		FileTool.copy(source, dest, null);
	}
	
	public static void copy(Path source, Path dest, FileFilter filter) {
		if(source.toFile().isDirectory() == true){
			FileTool.copyDirectory(source, dest, source, filter);
		}
		else{ // it is a file
			FileTool.copyFile(source, dest, source, filter);
		}
	}

	public static void copy(Path source, Path dest, Path rootDir, FileFilter filter) {
		if(source.toFile().isDirectory() == true){
			FileTool.copyDirectory(source, dest, rootDir, filter);
		}
		else{ // it is a file
			FileTool.copyFile(source, dest, rootDir, filter);
		}
	}

	
	public static void copyFile(Path source, Path dest, Path rootDir, FileFilter filter) {
		// check it out
		if( source.toFile().isFile() == false){
			throw new RuntimeException("[ccOutils] copyFile : the following path is not a file : " + source.toString()); 
		}
		
		boolean hasToBeAdded = true; 
		
		if(filter != null){
			hasToBeAdded = filter.isFileValid(source);
		}
		
		if(hasToBeAdded == true){
		
			// compute the name from the root dir
			Path fileRelativePath = rootDir.relativize(source); 
			
			// keep the same file name
			Path fileDest = Paths.get( dest.toString(), fileRelativePath.toString());
			try {		
				// let's copy 
				Files.copy(source, fileDest, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				System.out.println("src : " + source + "  dest : " + dest);
				e.printStackTrace();
			}
		}
	}

	
	public static void copyDirectory(Path source, Path dest, Path rootDir, FileFilter filter) {
		File dirSrc = source.toFile();
		
		if(dirSrc.isDirectory() == false){
			throw new RuntimeException("[ccOutils] copyDirectory : the following path is not a directory : " + source.toString()); 
		}
		
		// create the destination directory if not existing
		if(dest.toFile().exists() == false){
			// create it 
			try {
				Files.createDirectory(dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// create the source directory
		// compute the name from the root dir
		Path dirRelativePath = rootDir.relativize(source); 
		Path fullDestDir = Paths.get(dest.toString(), dirRelativePath.toString());
		if(fullDestDir.toFile().exists() == false){
			// create it 
			try {
				Files.createDirectory(fullDestDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// get all children
		File[] fileList = dirSrc.listFiles();
		
		for(File f : fileList){
			if(f.isDirectory()){
				FileTool.copyDirectory(f.toPath(), dest, rootDir, filter);
			}
			else{ // it is a file
				FileTool.copyFile(f.toPath(), dest, rootDir, filter);
			}
		}
	}
	
	public static void saveInputStream(InputStream inputStream, Path savePath, FilePropertyEnum prop){
		int read = 0;
		byte[] bytes = new byte[1024];
		
		try {
			FileOutputStream outFile = new FileOutputStream(savePath.toFile());
		
			while ((read = inputStream.read(bytes)) != -1) {
				outFile.write(bytes, 0, read);
			}
			// get the file
			File file = new File(savePath.toUri());
			
			if(	prop == FilePropertyEnum.x__ || 
				prop == FilePropertyEnum.x_w ||
				prop == FilePropertyEnum.xr_ ||
				prop == FilePropertyEnum.xrw){
				file.setExecutable(true);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isFileExist(String appPropertyFile) {
		return Files.exists(Paths.get(appPropertyFile));
	}
	
	public static Path[] getFileSFromNamesAndRootDirs(Path[] rootPathArray, String[] fileNameArray){
		List<Path> returnPath = new ArrayList<>();
		// for each file name check them out inside root dirs
		for(String libName : fileNameArray){
			boolean isFound = false;
			int i=0;
			// let's found it inside each root dir
			while(isFound == false){
				Path rootDir = rootPathArray[i++];
				String fullPathStr = rootDir + "/" + libName;
				if(FileTool.isFileExist(fullPathStr) == true){
					returnPath.add(Paths.get(fullPathStr));
					isFound=true;
				}
			}
		}
		return returnPath.toArray(new Path[0]);
	}
}

	
