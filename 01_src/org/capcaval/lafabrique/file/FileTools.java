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
package org.capcaval.lafabrique.file;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.capcaval.lafabrique.file._test.Rules;
import org.capcaval.lafabrique.file.pathfilter.PathFilter;
import org.capcaval.lafabrique.lang.ArrayTools;
import org.capcaval.lafabrique.lang.StringMultiLine;
import org.capcaval.lafabrique.lang.SystemTools;

public class FileTools {

	public enum FilePropertyEnum {
		___, x__, x_w, xr_, xrw, _r_, _rw, __w
	};

	public enum FilePosition {
		head, foot
	}

	static public void insertStringInFile(Path file, String strTobeInserted, FilePosition position){
		String str = FileTools.readStringfromFile(file);

		switch (position) {
		case foot:
			str = str + strTobeInserted;
			break;
		default:
			str = strTobeInserted + str;
			break;
		}
		FileTools.writeFile(file, str.getBytes(StandardCharsets.UTF_8));

	}

	static public String readStringfromFile(Path path){
		// get raw data file
		byte[] buffer;
		try {
			buffer = Files.readAllBytes(path);
		} catch (IOException e) {
			throw new RuntimeException("[laFabrique] Error : the file does not exist " + path);
		}
		// put it inside a string
		return new String(buffer);
	}

	static public String readStringfromFile(String pathStr){
		// get raw data file
		byte[] buffer;
		try {
			buffer = readByteArrayfromFile(pathStr);
		} catch (Exception e) {
			throw new RuntimeException("[laFabrique] Error : on file " + pathStr);		
		}
		// put it inside a string
		return new String(buffer);
	}

	static public byte[] readByteArrayfromFile(String pathStr){
		Path path = Paths.get(pathStr);
		// get raw data file
		byte[] buffer;
		try {
			buffer = Files.readAllBytes(path);
		} catch (IOException e) {
			throw new RuntimeException("[laFabrique] Error : the file does not exist " + path);
		}
		// put it inside a string
		return buffer;
	}

	static public void writeFile(String pathStr, byte[] byteArray){
		// use a path
		Path path = Paths.get(new File(pathStr).toURI());

		// write
		writeFile(path, byteArray);
	}

	static public Path writeFile(Path path, byte[] byteArray) {
		Path parent = path.getParent();

		// create directory if needed
		if (parent != null) {
			try {
				Files.createDirectories(parent);
			} catch (IOException e) {
				throw new RuntimeException("[laFabrique] Error : can not create dir " + parent);
			}
		}

		// write file overwrite if necessary
		Path p;
		try {
			p = Files.write(path, byteArray, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("[laFabrique] Error : can not write on " + path);
		}
		
		return p;
	}

	static public Path writeFile(Path path, Charset charset, String... valueArray) {
		Path p = null;

		try {
			Path parent = path.getParent();
			// create directory if needed
			if (parent != null) {
				Files.createDirectories(parent);
			}
			// build the list
			List<String> strList = ArrayTools.newArrayList(valueArray);

			// write file overwrite if necessary
			p = Files.write(path, strList, charset, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (Exception e) {
			throw new RuntimeException("[laFabrique] Error : can not write on " + path);
		}
		return p;
	}

	static public Path writeFile(String pathStr, Charset charset, String... valueArray){
		// build a path to write on this file
		return writeFile(Paths.get(pathStr), charset, valueArray);
	}

	static public Path writeFile(Path path, String... valueArray) {
		// write string with byte array method
		return writeFile(path, StandardCharsets.UTF_8, valueArray);
	}

	static public Path writeFile(String pathStr, String... valueArray) {
		// write string with defined path
		return writeFile(Paths.get(pathStr), valueArray);
	}

	static public void deleteFile(String pathStr) {
		// use a path
		Path path = Paths.get(pathStr);
		FileTools.deleteFile(path);
	}

	static public void deleteFile(Path path) {
		FileTools.deleteFile(path.toFile());
	}

	static public void deleteFile(File file) {
		if (file.exists() == false) {
			// do nothing bye bye
			return;
		}

		if (file.isDirectory()) {
			File[] fileList = file.listFiles();

			for (File f : fileList) {
				if (f.isDirectory()) {
					// recursive method call
					FileTools.deleteFile(f);
					// delete the directory
					f.delete();
				} else {
					f.delete();
				}
			}
			// delete t
			file.delete();
		} else {
			// delete it
			file.delete();
		}
	}
	
	static public FileSeekerResult seekDirectory(String... pathStrList){
		List<Path> pathList = new ArrayList<>();

		for (String pathStr : pathStrList) {
			pathList.add(Paths.get(pathStr));
		}

		return seekDirectory(pathList.toArray(new Path[0]));
	}

	public static FileSeekerResult seekDirectory(Path... array) {
		FileSeekerResult result = new FileSeekerResult();
		
		for(Path path: array){
			File[] files = path.toFile().listFiles();
	        for (File file : files) {
	            if (file.isDirectory()) {
	            	result.addFiles(file.toPath());
	            }
	        }
		}
		
		return result;
	}

	static public FileSeekerResult seekFiles(String pattern, String... pathStrList){
		List<Path> pathList = new ArrayList<>();

		for (String pathStr : pathStrList) {
			pathList.add(Paths.get(pathStr));
		}
		FileSeekerResult result = seekFiles(pattern, pathList);
		
		return result;
	}

	static public FileSeekerResult seekFiles(String pattern, Path... pathList){
		FileSeeker fileSeeker = new FileSeeker(pattern);
		// allocate a file result
		FileSeekerResult result = new FileSeekerResult();
		for (Path path : pathList) {
			result = fileSeeker.seek(path, result);
		}
		return result;
	}

	static public FileSeekerResult seekFiles(String pattern, boolean insideJar, Path... pathList) {
		FileSeeker fileSeeker = new FileSeeker(pattern, insideJar);
		// allocate a file result
		FileSeekerResult result = new FileSeekerResult();
		for (Path path : pathList) {
			result = fileSeeker.seek(path, result);
		}
		return result;
	}
	
	static public FileSeekerResult seekFiles(String pattern, List<Path> rootPathList) {
		return seekFiles(pattern, rootPathList, null);
	}
	
	static public FileSeekerResult seekFiles(String pattern, List<Path> rootPathList, List<Path> excludePathList) {
		FileSeeker fileSeeker = null;
		
		if(excludePathList == null){
			fileSeeker = new FileSeeker(pattern);
		}else{
			fileSeeker = new FileSeeker(pattern, excludePathList);
		}
		// allocate a file result
		FileSeekerResult result = new FileSeekerResult();
		for (Path path : rootPathList) {
			result = fileSeeker.seek(path, result);
		}
		return result;
	}
	
	public static FileSeekerResult seekFiles(Rules rules){
		return seekFiles(rules.pattern, rules.rootPathList, rules.excludePathList);
	}

	static public void replaceTokenInFile(Path templateFile, Path outputfile, Map<String, String> map,
			char startTokenChar, char endTokenChar){
		FileReader input;
		try {
			input = new FileReader(templateFile.toFile());
		} catch (FileNotFoundException e) {
			throw new RuntimeException("[laFabrique] Error : the file does not exist " + templateFile.toFile());
		}

		BufferedWriter output;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputfile.toFile())));
			Writer writer = TokenTool.replaceTokenFromReader(input, map, startTokenChar, endTokenChar);
			output.write(writer.toString());
		} catch (IOException e) {
			throw new RuntimeException("[laFabrique] Error : can not write ", e);
		}
		
		try {
			output.close();
		} catch (IOException e) {
			throw new RuntimeException("[laFabrique] Error : can not close " + outputfile.toFile(), e);
		}
	}

	static public void replaceTokenInFile(InputStreamReader streamReader, Path outputfile, Map<String, String> map,
			char startTokenChar, char endTokenChar){

		// check out if directory has to be created
		File parentDir = outputfile.toFile().getParentFile();
		if (parentDir.exists() == false) {
			// create them if not here
			parentDir.mkdirs();
		}

		BufferedWriter output;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputfile.toFile())));

			Writer writer = TokenTool.replaceTokenFromReader(streamReader, map, startTokenChar, endTokenChar);
			output.write(writer.toString());
			output.close();
		
		} catch (FileNotFoundException e) {
			throw new RuntimeException("[laFabrique] Error : can not found the file : " + outputfile.toFile(), e);
		} catch (IOException e) {
			throw new RuntimeException("[laFabrique] Error : on file : " + outputfile.toFile(), e);
		}

	}

	static public List<String> getAllTokenFromFile(Path templateFile, char startTokenChar, char endTokenChar,
			int maxTokenCharSize){
		FileReader input;
		try {
			input = new FileReader(templateFile.toFile());
		} catch (FileNotFoundException e) {
			throw new RuntimeException("[laFabrique] Error : can not found the file : " + templateFile.toFile(), e);
		}

		return TokenTool.getAllTokenFromReader(input, startTokenChar, endTokenChar, maxTokenCharSize);
	}

	public static void copy(Path source, Path dest) {
		FileTools.copy(source, dest, (PathFilter[]) null);
	}

	public static void copy(Path source, Path dest, PathFilter... filter) {
		Path srcParent = source.getParent();
		if (srcParent == null) {
			srcParent = Paths.get("./");
		}

		if (source.toFile().isDirectory() == true) {
			FileTools.copyDirectory(source, dest, srcParent, filter);
		} else { // it is a file
			FileTools.copyFile(source, dest, srcParent, filter);
		}
	}

	public static void copy(Path source, Path dest, Path rootDir, PathFilter... filterArray) {
		source = rootDir.resolve(source);

		// TODO rajoute des exceptions et des tests

		if (source.toFile().isDirectory() == true) {
			FileTools.copyDirectory(source, dest, rootDir, filterArray);
		} else { // it is a file
			FileTools.copyFile(source, dest, rootDir, filterArray);
		}
	}

	public static void copyFile(Path source, Path dest, Path rootDir, PathFilter... filterArray) {
		// check it out
		if (source.toFile().isFile() == false) {
			throw new RuntimeException("[laFabrique] copyFile : the following path is not a file : " + source.toString());
		}

		boolean hasToBeAdded = true;

		if (filterArray != null) {
			hasToBeAdded = isPathValid(source, filterArray);
		}

		if (hasToBeAdded == true) {
			// make sure that both path are in absolute
			rootDir = rootDir.toAbsolutePath().normalize();
			source = source.toAbsolutePath().normalize();

			// compute the name from the root dir
			Path fileRelativePath = rootDir.relativize(source);

			// keep the same file name
			Path fileDest = Paths.get(dest.toString(), fileRelativePath.toString());

			// make that the parent exist
			Path parent = fileDest.getParent();
			if (FileTools.isFileExist(parent) == false) {
				// create it
				FileTools.createAndCleanDir(parent);
			}

			try {
				// let's copy
				Files.copy(source, fileDest, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				System.out.println("src : " + source + "  dest : " + dest);
				e.printStackTrace();
			}
		}
	}

	public static boolean isPathValid(Path path, PathFilter... filterArray) {
		boolean returnValue = true;

		if (filterArray == null)
			filterArray = new PathFilter[0];

		for (PathFilter filter : filterArray) {
			boolean isValid = filter.isPathValid(path);

			if (isValid == false) {
				returnValue = false;
				// bye bye
				break;
			}
		}

		return returnValue;
	}

	public static void copyDirectory(Path source, Path dest, Path rootDir, PathFilter... filterArray) {
		File dirSrc = source.toFile();

		if (dirSrc.isDirectory() == false) {
			throw new RuntimeException("[laFabrique] copyDirectory : the following path is not a directory : "
					+ source.toString());
		}

		// create the destination directory if not existing
		if (dest.toFile().exists() == false) {
			// create it
			try {
				if (isPathValid(dest, filterArray) == true) {
					Files.createDirectory(dest);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// put all the dir in absolute for a bug on relativize
		rootDir = rootDir.toAbsolutePath().normalize();
		source = source.toAbsolutePath().normalize();
		// create the dest directory
		// compute the name from the root dir
		Path dirRelativePath = rootDir.relativize(source);
		Path fullDestDir = Paths.get(dest.toString(), dirRelativePath.toString());
		if (fullDestDir.toFile().exists() == false) {
			// create it
			if (isPathValid(source, filterArray) == true) {
				FileTools.createAndCleanDir(fullDestDir);
			}
		}
		// get all children
		File[] fileList = dirSrc.listFiles();

		for (File f : fileList) {
			if (f.isDirectory()) {
				FileTools.copyDirectory(f.toPath(), dest, rootDir, filterArray);
			} else { // it is a file
				FileTools.copyFile(f.toPath(), dest, rootDir, filterArray);
			}
		}
	}

	public static void saveInputStream(InputStream inputStream, Path savePath, FilePropertyEnum prop) {
		int read = 0;
		byte[] bytes = new byte[1024];
		FileOutputStream outFile = null;

		try {
			outFile = new FileOutputStream(savePath.toFile());

			while ((read = inputStream.read(bytes)) != -1) {
				outFile.write(bytes, 0, read);
			}
			// get the file
			File file = new File(savePath.toUri());

			if (prop == FilePropertyEnum.x__ || prop == FilePropertyEnum.x_w || prop == FilePropertyEnum.xr_
					|| prop == FilePropertyEnum.xrw) {
				file.setExecutable(true);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isFileExist(String file) {
		return isFileExist(Paths.get(file));
	}

	public static boolean isFileExist(Path path) {
		return Files.exists(path);
	}

	public static Path[] getFileSFromNamesAndRootDirs(String[] rootPathArray, String[] fileNameArray) {
		// convert the list
		List<Path> rootPathList = new ArrayList<>();
		for (String path : rootPathArray) {
			rootPathList.add(Paths.get(path));
		}

		// call the method handling paths
		return getFileSFromNamesAndRootDirs(rootPathList.toArray(new Path[0]), fileNameArray);
	}

	public static Path[] getFileSFromNamesAndRootDirs(Path[] rootPathArray, String[] fileNameArray) {
		List<Path> returnPath = new ArrayList<>();

		// protect against null pointer
		if (fileNameArray == null) {
			fileNameArray = new String[0];
		}

		// for each file name check them out inside root dirs
		for (String libName : fileNameArray) {
			boolean isFound = false;
			int i = 0;
			// let's found it inside each root dir
			while ((isFound == false) && (i < rootPathArray.length)) {
				Path rootDir = rootPathArray[i++];
				String fullPathStr = rootDir + "/" + libName;
				if (FileTools.isFileExist(fullPathStr) == true) {
					returnPath.add(Paths.get(fullPathStr));
					isFound = true;
				}
			}
		}
		return returnPath.toArray(new Path[0]);
	}

	public static Path getLocalDirPath() {
		// get the type of the upper caller
		Class<?> type = SystemTools.getCallerType(3);

		return getClassDirPath(type);
	}

	public static Path getClassDirPath(Class<?> type) {
		// transform from package name to file name
		Package p = type.getPackage();
		String packageStr = p.getName();
		packageStr = packageStr.replace(".", "/");
		// use the system classloader
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		// add the file name to the ref path
		URL url = classLoader.getResource(packageStr);

		Path path = null;
		try {
			path = Paths.get(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return path;
	}

	public static Path getLocalResourcePath(String fileName) {
		// get the type of the upper caller
		Class<?> type = SystemTools.getCallerType(3);

		// transform from package name to file name
		Package p = type.getPackage();
		String packageStr = p.getName();
		packageStr = packageStr.replace(".", "/");
		// use the system classloader
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		// add the file name to the ref path
		URL url = classLoader.getResource(packageStr + "/" + fileName);

		Path path = null;
		try {
			path = Paths.get(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return path;
	}

	public static InputStream getLocalResourceStream(String fileName) {
		// get the type of the upper caller
		Class<?> type = SystemTools.getCallerType(3);

		return type.getResourceAsStream(fileName);
	}

	public static BufferedImage getLocalImage(String fileName) {
		// get the type of the upper caller
		Class<?> type = SystemTools.getCallerType(3);

		BufferedImage image = null;
		try {
			image = ImageIO.read(type.getResource(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}

	public static Path getLocalPath(String fileName) {
		// get the local path from a typical 3 level
		return getLocalPath(fileName, 3);
	}

	public static Path getLocalPath(String fileName, int level) {
		// get the type of the upper caller
		Class<?> type = SystemTools.getCallerType(level);

		// get the local path
		return getLocalPathFromClass(fileName, type);
	}

	public static Path getLocalPathFromClass(String fileName, Class<?> type) {

		Path local = getLocalDirPath();
		Path path = local.resolve(Paths.get(fileName));

		return path;
	}

	public static String getLocalTextFile(String fileName) {
		// get the type of the upper caller
		Class<?> type = SystemTools.getCallerType(3);
		InputStream stream = type.getResourceAsStream(fileName);

		getLocalResourceStream(fileName);

		StringMultiLine sm = new StringMultiLine();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

		String line = null;

		try {
			do {
				line = reader.readLine();
				if (line != null) {
					sm.addLine(line);
				}
			} while (line != null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sm.toString();
	}

	public static void renameFile(Path filePath, String newName) {
		if (filePath.toFile().exists() == false) {
			throw new RuntimeException("[laFabrique] ERROR : File does not exist " + filePath);
		}
		try {
			Files.move(filePath, filePath.resolveSibling(newName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createAndCleanDir(String dirPath) {
		createAndCleanDir(Paths.get(dirPath));
	}

	public static void createAndCleanDir(Path dirPath) {
		// delete if already existing
		FileTools.deleteFile(dirPath);

		// create the directory
		createDir(dirPath);
	}

	public static void createDir(String dirPathStr) {
		// create the path
		Path path = Paths.get(dirPathStr);
		// create the directory
		createDir(path);
	}

	public static void createDir(Path dirPath) {
		// create the directory
		dirPath.toFile().mkdirs();
	}

	public static void copy(String src, String dest) {
		try {
			Path srcPath = Paths.get(src);
			Path destPath = Paths.get(dest);
			Path parentDestPath = destPath.getParent();
			
			// check if dest directoty exists
			if(FileTools.isFileExist(parentDestPath) == false){
				// create it if no
				FileTools.createDir(parentDestPath);
			}
			
			Files.copy( srcPath, destPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void replaceStringInFile(String pathStr, String oldValue, String newValue) {
		replaceStringInFile(Paths.get(pathStr), oldValue, newValue);
	}

	public static String replaceStringInFile(Path path, String oldValue, String newValue) {
		// check out that the file exist
		if (path.toFile().exists() == false) {
			throw new RuntimeException("[laFabrique] ERROR : File does not exist " + path);
		}

		// read
		String str = FileTools.readStringfromFile(path);
		
		//replace
		str = str.replace(oldValue, newValue);
		
		// write it back
		FileTools.writeFile(path, str);
		
		return str;
	}


}
