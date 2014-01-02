package org.capcaval.ccoutils.file._test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.capcaval.ccoutils.file.FileTools;
import org.capcaval.ccoutils.file.JarZipTools;
import org.junit.Test;

public class JarToolsTest {
	
	@Test
	public void addFileInsideJarTest(){
		try {
			FileTools.deleteFile("test.jar");
			
			File file = new File("temp/file.java");
			Writer writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(file), "utf-8"));
			writer.write("Something for you\n and me");
			writer.write("to write\n");
			writer.close();

			File file2 = new File("temp/file2.java");
			Writer writer2 = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(file2), "utf-8"));
			writer2.write("File number2\n hello ");
			writer2.write("how are you doing?\n");
			writer2.close();

			
			Path zipFile = Files.createFile(Paths.get("test.jar"));
			JarZipTools.createJarFile(zipFile.toFile(), null, file, file2);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
