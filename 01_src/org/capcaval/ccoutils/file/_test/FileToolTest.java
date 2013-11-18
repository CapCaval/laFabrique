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
package org.capcaval.ccoutils.file._test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.capcaval.ccoutils.file.FileTool;
import org.capcaval.ccoutils.file.FileTool.FilePosition;
import org.capcaval.ccoutils.lang.ArrayTools;

import junit.framework.Assert;



public class FileToolTest {

	@org.junit.Test
	public void testGetAllTokenFromReader() throws IOException {
				
		List<String> list = ArrayTools.newArrayList("ACTOR_1", "ACTOR_2");
		Path templateFile = Paths.get("01_src/org/capcaval/ccoutils/file/_test/lafontaine.txt");
		
		List<String> output = FileTool.getAllTokenFromFile(templateFile, '{', '}', 32);
		
		System.out.println("#testGetAllTokenFromReader : \n" + output);
		
		Assert.assertEquals(list, output);

	}
	
	@org.junit.Test
	public void testReplaceTokenFromFile() throws IOException {
				
		Path templateFile = Paths.get("01_src/org/capcaval/ccoutils/file/_test/lafontaine.txt");
		Path outputfile = Paths.get("01_src/org/capcaval/ccoutils/file/_test/lafontaine_output.txt");
		Path referencefile = Paths.get("01_src/org/capcaval/ccoutils/file/_test/lafontaine_reference.txt");
		
		// first delete the reference file if existing
		Files.deleteIfExists(outputfile);
		
		Map<String, String> map = ArrayTools.newMap("ACTOR_1","Corbeau","ACTOR_2","Renard");
		
		FileTool.replaceTokenInFile(templateFile, outputfile, map, '{', '}');
		boolean areFileTheSame = Arrays.equals(Files.readAllBytes(outputfile), Files.readAllBytes(referencefile));
		
		System.out.println("default charset : " + Charset.forName("latin1"));
		System.out.println(Files.readAllLines(outputfile, Charset.defaultCharset()));
		System.out.println("##############");
		System.out.println(Files.readAllLines(referencefile,Charset.defaultCharset()));
		System.out.println("##############");
		System.out.println(Files.readAllLines(templateFile,Charset.defaultCharset()));
		
		Assert.assertTrue(areFileTheSame);
	}
	
	@org.junit.Test
	public void testInsertStringInFile() throws Exception {
		
		Path file = Paths.get("./test/test.txt");
		
		final String str =	"line1\n"+
							"line2\n"+
							"line3\n"+
							"line4\n";
		
		// add a header
		FileTool.writeFile(file, str);
		FileTool.insertStringInFile(file, "Hello\n", FilePosition.head);
		String result = FileTool.readStringfromFile(file);
		Assert.assertEquals(result, "Hello\n" + str);
		
		// add at the bottom
		FileTool.writeFile(file, str);
		FileTool.insertStringInFile(file, "Hello\n", FilePosition.foot);
		result = FileTool.readStringfromFile(file);
		Assert.assertEquals(result, str + "Hello\n");
		
		Files.delete(file);
		
	}
	
	@org.junit.Test
	public void testReadStringFromFile() throws IOException {
		String pathStr = "temp/test.txt";
		String str = "abcdefghij\nklmnopq\nrstuvwxyz";
		
		FileTool.writeFile(pathStr, str.getBytes());
		
		String result = FileTool.readStringfromFile(pathStr);
		
		Assert.assertEquals(str, result);
		
		// delete it 
		FileTool.deleteFile(pathStr);
		
	}
	
	@org.junit.Test
	public void  deleteFileTest(){
		// create some file
		try {
			
			Path rootDir = Paths.get("testDir");
			Path secondDir = Paths.get("testDir/testDir2");
			Path file = Paths.get("testDir/testDir2/file.txt");
			
			
			if(Files.exists(rootDir) == false){
				Files.createDirectory(rootDir);}
			if(Files.exists(secondDir) == false){
				Files.createDirectory(secondDir);}
			if(Files.exists(file) == false){
				Files.write(file, 
					new byte[]{'a','b','c','d','e','f','g','h','i'});}
		
			FileTool.deleteFile("testDir");
			
			Assert.assertEquals(false, Files.exists(rootDir));
			Assert.assertEquals(false, Files.exists(secondDir));
			Assert.assertEquals(false, Files.exists(file));
		
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@org.junit.Test
	public void getFileSFromNamesAndRootDirsTest(){
		Path dir1 = Paths.get(FileToolTest.class.getResource("dir1").getPath());
		Path dir2 = Paths.get(FileToolTest.class.getResource("dir2").getPath());
		
		Path[] pathArray = FileTool.getFileSFromNamesAndRootDirs(new Path[]{dir1, dir2}, new String[]{"File1.txt","File2.txt"});
		
		Path result1 = Paths.get(FileToolTest.class.getResource("dir1/File1.txt").getPath());
		Path result2 = Paths.get(FileToolTest.class.getResource("dir2/File2.txt").getPath());
		
		Assert.assertEquals(result1, pathArray[0]);
		Assert.assertEquals(result2, pathArray[1]);
		
	}
}
