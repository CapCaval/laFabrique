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

public class CommandJar {
	public static CommandResult makeJar(AbstractProject proj){
		
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
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new CommandResult(true, "yes");
	}
	
	public static Manifest createManifest(AbstractProject proj){
		Manifest manifest = new Manifest();

		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
		manifest.getMainAttributes().put(Attributes.Name.IMPLEMENTATION_VENDOR, Arrays.toString(proj.authorList));
		manifest.getMainAttributes().put(Attributes.Name.IMPLEMENTATION_VERSION, proj.version);
		
		return manifest;
	}
}
