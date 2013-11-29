package org.capcaval.ccoutils.lafabrique.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.capcaval.ccoutils.file.FileSeekerResult;
import org.capcaval.ccoutils.file.FileTool;
import org.capcaval.ccoutils.file.JarTool;
import org.capcaval.ccoutils.lafabrique.AbstractProject;
import org.capcaval.ccoutils.lang.StringMultiLine;

public class CommandJar {
	public static CommandResult makeJar(AbstractProject proj){
		StringMultiLine returnedMessage = new StringMultiLine();
		
		returnedMessage.addLine("[laFabrique] INFO  : Start making Jar " + proj.jar.name);
		Path zipFile = null;
		try {
			Path jarPath = Paths.get(proj.jar.outputJar + "/" + proj.jar.name); 
			
			// delete the old one if existing
			FileTool.deleteFile(jarPath);
			
			zipFile = Files.createFile( jarPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		FileSeekerResult r = null;
		try {
			r = FileTool.seekFiles("*", proj.outputBinPath);
		
			JarTool.createJarFile(
					zipFile.toFile(), 
					createManifest(proj),
					proj.outputBinPath.toFile(), 
					r.getFileList());
			
			returnedMessage.addLine("[laFabrique] INFO  : Jar successFully created at " + zipFile.toFile().getAbsolutePath());
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new CommandResult(true, returnedMessage.toString());
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
