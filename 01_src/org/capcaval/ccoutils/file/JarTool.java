package org.capcaval.ccoutils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class JarTool {
//	public static void createZipFile(String zipFileName, File rootDir, File... fileList){
//		try{
//			Path zipFile = Files.createFile(Paths.get(zipFileName));
//			JarTool.createJarFile(zipFile.toFile(), rootDir, fileList);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public static boolean isFileInsideZip(File zipFile, String fileName){
		boolean isFileInside = false;
		ZipInputStream zis = null;

		try {
			zis = new ZipInputStream(new FileInputStream(zipFile));
			
			boolean isOver = false;

			while (isOver == false) {
				ZipEntry zentry = zis.getNextEntry();
				if (zentry == null) {
					// nothing more to be read, bye
					isOver = true;
				} else {
					String name = zentry.getName();

					if (fileName.equals(name)) {
						isFileInside = true;
						// It is found, bye
						isOver = true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				zis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return isFileInside; 
	}
	
	public static void createJarFile(File jarFile, Manifest manifest, File rootDirFile, File... fileList) throws IOException {
		byte[] buf = new byte[1024];
		
		
		JarOutputStream zos = new JarOutputStream(new FileOutputStream(jarFile),manifest);
		
		//ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
		
		// Compress the files
		for (int i = 0; i < fileList.length; i++) {
			InputStream in = new FileInputStream(fileList[i]);
			// compute relative name
			String fileName = fileList[i].getPath().substring(rootDirFile.getPath().length()+1);
			
			// Add ZIP entry to output stream.
			zos.putNextEntry(new ZipEntry(fileName));
			// Transfer bytes from the file to the ZIP file
			int len;
			while ((len = in.read(buf)) > 0) {
				zos.write(buf, 0, len);
			}
			// Complete the entry
			zos.closeEntry();
			in.close();
		}
		// Complete the ZIP file
		zos.close();
	}

	public static void addFile(ZipOutputStream jarFileStream, File file){
		try {
			// add a new entry inside the JAR
			jarFileStream.putNextEntry(new ZipEntry(file.getName()));
			
			// get a stream from file
			InputStream in = new FileInputStream(file);
			// write from file stream into Jar stream
			int len;
			byte[] buf = new byte[1024];

			while ((len = in.read(buf)) > 0) {
				jarFileStream.write(buf, 0, len);
			}
			// close resources
			in.close();
			jarFileStream.closeEntry();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
