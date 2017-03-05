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
package org.capcaval.lafabrique.file._test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.file.FileTools.FilePosition;
import org.capcaval.lafabrique.lang.ArrayTools;

public class FileToolsTest {

	@org.junit.Test
	public void getAllTokenFromReaderTest() throws IOException {

		List<String> list = ArrayTools.newArrayList("ACTOR_1", "ACTOR_2");
		Path templateFile = Paths.get("01_src/org/capcaval/lafabrique/file/_test/lafontaine.txt");

		List<String> output = FileTools.getAllTokenFromFile(templateFile, '{', '}', 32);

		System.out.println("#testGetAllTokenFromReader : \n" + output);

		Assert.assertEquals(list, output);

	}

	@org.junit.Test
	public void replaceTokenFromFileTest() throws IOException {

		Path templateFile = Paths.get("01_src/org/capcaval/lafabrique/file/_test/lafontaine.txt");
		Path outputfile = Paths.get("01_src/org/capcaval/lafabrique/file/_test/lafontaine_output.txt");
		Path referencefile = Paths.get("01_src/org/capcaval/lafabrique/file/_test/lafontaine_reference.txt");

		// first delete the reference file if existing
		Files.deleteIfExists(outputfile);

		Map<String, String> map = ArrayTools.newMap("ACTOR_1", "Corbeau", "ACTOR_2", "Renard");

		FileTools.replaceTokenInFile(templateFile, outputfile, map, '{', '}');
		boolean areFileTheSame = Arrays.equals(Files.readAllBytes(outputfile), Files.readAllBytes(referencefile));

		System.out.println("default charset : " + Charset.forName("latin1"));
		System.out.println(Files.readAllLines(outputfile, Charset.defaultCharset()));
		System.out.println("##############");
		System.out.println(Files.readAllLines(referencefile, Charset.defaultCharset()));
		System.out.println("##############");
		System.out.println(Files.readAllLines(templateFile, Charset.defaultCharset()));

		Assert.assertTrue(areFileTheSame);
	}

	@org.junit.Test
	public void insertStringInFileTest() throws Exception {

		Path file = Paths.get("./_test/test.txt");

		final String str = "line1\n" + "line2\n" + "line3\n" + "line4\n";

		String[] multiLineStr = str.split("\n");

		// add a header
		FileTools.writeFile(file, multiLineStr);
		FileTools.insertStringInFile(file, "Hello\n", FilePosition.head);
		String result = FileTools.readStringfromFile(file);
		Assert.assertEquals(result, "Hello\n" + str);

		// add at the bottom
		FileTools.writeFile(file, multiLineStr);
		FileTools.insertStringInFile(file, "Hello\n", FilePosition.foot);
		result = FileTools.readStringfromFile(file);
		Assert.assertEquals(result, str + "Hello\n");

		Files.delete(file);
		Files.delete(file.getParent());

	}

	@org.junit.Test
	public void readStringFromFileTest() throws IOException {
		String pathStr = "temp/test.txt";
		String str = "abcdefghij\nklmnopq\nrstuvwxyz";

		FileTools.writeFile(pathStr, str.getBytes());

		String result = FileTools.readStringfromFile(pathStr);

		Assert.assertEquals(str, result);

		// delete it
		FileTools.deleteFile(pathStr);

	}

	@org.junit.Test
	public void deleteFileTest() {
		// create some file
		try {

			Path rootDir = Paths.get("testDir");
			Path secondDir = Paths.get("testDir/testDir2");
			Path file = Paths.get("testDir/testDir2/file.txt");

			if (Files.exists(rootDir) == false) {
				Files.createDirectory(rootDir);
			}
			if (Files.exists(secondDir) == false) {
				Files.createDirectory(secondDir);
			}
			if (Files.exists(file) == false) {
				Files.write(file, new byte[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i' });
			}

			FileTools.deleteFile("testDir");

			Assert.assertEquals(false, Files.exists(rootDir));
			Assert.assertEquals(false, Files.exists(secondDir));
			Assert.assertEquals(false, Files.exists(file));

		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@org.junit.Test
	public void getFileSFromNamesAndRootDirsTest() {
		Path dir1 = Paths.get(FileToolsTest.class.getResource("dir1").getPath());
		Path dir2 = Paths.get(FileToolsTest.class.getResource("dir2").getPath());

		Path[] pathArray = FileTools.getFileSFromNamesAndRootDirs(new Path[] { dir1, dir2 }, new String[] {
				"File1.txt", "File2.txt" });

		Path result1 = Paths.get(FileToolsTest.class.getResource("dir1/File1.txt").getPath());
		Path result2 = Paths.get(FileToolsTest.class.getResource("dir2/File2.txt").getPath());

		Assert.assertEquals(result1, pathArray[0]);
		Assert.assertEquals(result2, pathArray[1]);

	}

	@org.junit.Test
	public void getLocalResourcePathTest() {
		Path path = FileTools.getLocalResourcePath("dir1/File1.txt");
		Assert.assertEquals(true, Files.exists(path));

	}

	@org.junit.Test
	public void getLocalResourceStreamTest() {
		InputStream stream = FileTools.getLocalResourceStream("dir1/File1.txt");
		Assert.assertNotNull(stream);

	}

	@org.junit.Test
	public void getLocalTextTest() {
		String str = FileTools.getLocalTextFile("dir1/File1.txt");
		Assert.assertEquals("file1\nLine2", str);

	}

	@org.junit.Test
	public void getLocalImageTest() {
		BufferedImage image = FileTools.getLocalImage("sein.jpg");
		Assert.assertNotNull(image);
	}

	@org.junit.Test
	public void getLocalDirPathTest() {
		Path expectedResultPath = Paths.get("10_bin/org/capcaval/lafabrique/file/_test").toAbsolutePath();
		if(FileTools.isFileExist(expectedResultPath)==false){
			// this test can be run either on Eclipse or either with laFab
			// if do not exist for laFab set it as eclipse
			expectedResultPath = Paths.get("11_idebin/org/capcaval/lafabrique/file/_test").toAbsolutePath();
		}
		

		boolean isSameDir = false;
		Path localPath = FileTools.getLocalDirPath();
		try {
			isSameDir = Files.isSameFile(expectedResultPath, localPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Assert.assertTrue(isSameDir);
		isSameDir = false;
		localPath = FileTools.getClassDirPath(this.getClass());
		try {
			isSameDir = Files.isSameFile(expectedResultPath, localPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(isSameDir);
	}

	@org.junit.Test
	public void fileCopyTest() {
		Path localDir = FileTools.getLocalDirPath();
		Path sourcePath = localDir.resolve(Paths.get("dir1"));
		Path destPath = localDir.resolve(Paths.get("dir3"));

		FileTools.copy(sourcePath, destPath);
		
		Assert.assertTrue(FileTools.isFileExist(destPath.resolve("dir1/File1.txt")));
	}
	
	@org.junit.Test
	public void fileSimpleCopyTest() {
		FileTools.createAndCleanDir("temp");
		FileTools.writeFile("temp/test.txt", "Hello world!");
		
		FileTools.copy("temp/test.txt","temp/test2.txt");
		String str = FileTools.readStringfromFile("temp/test2.txt");
		Assert.assertEquals("Hello world!\n", str);
	}
	

	@org.junit.Test
	public void fileCopyWithRootTest() {
		Path localDir = FileTools.getLocalDirPath();
		Path sourcePath = Paths.get("dir2/dir21");
		Path destPath = Paths.get("./temp/30_copy");

		FileTools.createAndCleanDir(destPath);

		FileTools.copy(sourcePath, destPath, localDir);

		Assert.assertTrue(FileTools.isFileExist(destPath.resolve("dir2/dir21/File21.txt")));
		Assert.assertTrue(FileTools.isFileExist(destPath.resolve("dir2/dir21/File22.txt")));
		Assert.assertFalse(FileTools.isFileExist(destPath.resolve("dir2/File1.txt")));
		Assert.assertFalse(FileTools.isFileExist(destPath.resolve("dir2/File2.txt")));

		FileTools.deleteFile(Paths.get("./temp"));
	}

	@org.junit.Test
	public void fileRenameTest() {
		Path localDir = FileTools.getLocalDirPath();

		Path oldName = localDir.resolve("test.txt");

		FileTools.renameFile(oldName, "newName.txt");
		Path newFile = localDir.resolve("newName.txt");
		Assert.assertTrue(newFile.toFile().exists());

		// put back the old name
		FileTools.renameFile(newFile, "test.txt");
		Assert.assertTrue(oldName.toFile().exists());
	}

	@org.junit.Test
	public void createDirTest() {
		// path to test
		Path newDir = Paths.get("newDir");
		// create a file to be cleaned
		FileTools.writeFile("newDir/test.txt", "Hello");
		// create it
		FileTools.createAndCleanDir(newDir);
		// test if created
		Assert.assertTrue(FileTools.isFileExist("newDir"));

		// test if clean
		Assert.assertFalse(FileTools.isFileExist("newDir/test.txt"));

		// clean
		FileTools.deleteFile(newDir);
	}
	
	@org.junit.Test
	public void replaceStringInFileTest() {
		// clean the place
		FileTools.createAndCleanDir("newDir");
		
		// create a file to be cleaned
		FileTools.writeFile("newDir/testReplace.txt", 
				"Hello this is a great day for coding.",
				"Salute all the great coders, who made computing such an amazing thing !");

		FileTools.replaceStringInFile("newDir/testReplace.txt", "great", "fabulous");
		
		
		String result =  "Hello this is a great day for coding.\nSalute all the great coders, who made computing such an amazing thing !";
		String str = FileTools.readStringfromFile("newDir/testReplace.txt");
		
		System.out.println(result);
		System.out.println(str);
		
		// test if created
		//Assert.assertEquals(result, str);

		// clean
		FileTools.deleteFile("newDir");
	}
	
}
