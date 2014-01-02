package org.capcaval.ccoutils.lafabrique.command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.capcaval.ccoutils.common.CommandResult;
import org.capcaval.ccoutils.file.FileSeekerResult;
import org.capcaval.ccoutils.file.FileTools;
import org.capcaval.ccoutils.file.JarZipTools;
import org.capcaval.ccoutils.lafabrique.AbstractProject;
import org.capcaval.ccoutils.lang.ListTools;
import org.capcaval.ccoutils.lang.StringMultiLine;
import org.capcaval.ccoutils.lang.listProcessor.RemoveContainsFileListProcessor;

public class CommandJar {
	public static CommandResult makeJar(AbstractProject proj){
		StringMultiLine returnedMessage = new StringMultiLine();
		
		returnedMessage.addLine("[laFabrique] INFO  : Start making Jar " + proj.jar.name);
		Path zipFile = null;
		try {
			Path jarPath = Paths.get(proj.jar.outputJar + "/" + proj.jar.name); 
			
			// delete the old one if existing
			FileTools.deleteFile(jarPath);
			
			zipFile = Files.createFile( jarPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		FileSeekerResult r = null;
		try {
			Path srcPath = proj.productionDirPath.resolve(Paths.get(proj.tempProdSource));
			
			r = FileTools.seekFiles("*", srcPath);
			
			File[] fileList = r.getFileList();
			// remove all unwanted directories
			fileList = ListTools.compute(fileList, new RemoveContainsFileListProcessor("_test", "_design"));
		
			JarZipTools.createJarFile(
					zipFile.toFile(), 
					createManifest(proj),
					srcPath.toFile(), 
					fileList);
			
			returnedMessage.addLine("[laFabrique] INFO  : Jar successFully created at " + zipFile.toFile().getAbsolutePath());
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new CommandResult(returnedMessage.toString());
	}
	
	public static Manifest createManifest(AbstractProject proj){
		Manifest manifest = new Manifest();

		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
		manifest.getMainAttributes().put(Attributes.Name.IMPLEMENTATION_TITLE, proj.name);
		manifest.getMainAttributes().put(Attributes.Name.IMPLEMENTATION_VENDOR, proj.copyright);
		manifest.getMainAttributes().put(Attributes.Name.IMPLEMENTATION_VERSION, proj.version);
		manifest.getMainAttributes().put(Attributes.Name.IMPLEMENTATION_URL, proj.url);
		manifest.getMainAttributes().put(new Attributes.Name("Implementation-Version-Name"), "Heb Chench");
		manifest.getMainAttributes().put(new Attributes.Name("Licence"), proj.licence);
		
		
		return manifest;
	}
}
