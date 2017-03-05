package org.capcaval.lafabrique.lafab.command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.common.CommandResult.Type;
import org.capcaval.lafabrique.file.FileSeekerResult;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.file.JarZipTools;
import org.capcaval.lafabrique.file.pathfilter.IncludePathFinishingWith;
import org.capcaval.lafabrique.file.pathfilter.PathFilter;
import org.capcaval.lafabrique.lang.ListTools;
import org.capcaval.lafabrique.lang.listProcessor.RemoveContainsFileListProcessor;
import org.capcaval.lafabrique.project.Project;
import org.capcaval.lafabrique.project.ProjectTools;

public class CommandJar implements LaFabTask{
	public static CommandResult makeJar(Project proj){
		CommandResult cr = new CommandResult(Type.INFO, "Start making Jar " + proj.jar.name);
		
		if(proj.jar.isSourceIncluded == true){
			cr.addInfoMessage("Copy source inside jar.");
			cr.concat( copyAllJavaSource(proj));
		}
		
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
			// copy all the file inside the jar prod dir 
			for(String packageStr : proj.packageNameList){
				packageStr = packageStr.replace(".", "/");
				FileTools.copy(Paths.get(packageStr), ProjectTools.getProdJarDir(proj), proj.binDirPath);
			}
			
			// copy also the project
			r = FileTools.seekFiles(proj.getClass().getSimpleName() + "*.class", proj.binDirPath+"/prj");
			
			for(Path p : r.getPathList()){
				String pathStr = p.toString();
				pathStr = p.toString().substring(proj.binDirPath.toString().length()+1, pathStr.length());
				
				FileTools.copy( Paths.get(pathStr), ProjectTools.getProdJarDir(proj), proj.binDirPath); 
			}
			//FileTools.copy( Paths.get("prj/" + proj.getClass().getSimpleName() + ".class"), ProjectTools.getProdJarDir(proj), proj.binDirPath);
			//FileTools.copy( Paths.get("prj/" + proj.getClass().getSimpleName() + "$1.class"), ProjectTools.getProdJarDir(proj), proj.binDirPath);
			
			r = FileTools.seekFiles("*", ProjectTools.getProdJarDir(proj));
			
			File[] fileList = r.getFileList();
			// remove all unwanted directories
			fileList = ListTools.compute(fileList, new RemoveContainsFileListProcessor(proj.jar.excludedDirectoryList));
		
			JarZipTools.createJarFile(
					zipFile.toFile(), 
					createManifest(proj),
					ProjectTools.getProdJarDir(proj).toFile(), 
					fileList);
			
			cr.addInfoMessage("Jar successFully created at " + zipFile.toFile().getAbsolutePath());
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return cr;
	}
	
	public static Manifest createManifest(Project proj){
		Manifest manifest = new Manifest();

		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
		manifest.getMainAttributes().put(Attributes.Name.IMPLEMENTATION_TITLE, proj.name);
		manifest.getMainAttributes().put(Attributes.Name.IMPLEMENTATION_VENDOR, proj.copyright);
		manifest.getMainAttributes().put(Attributes.Name.IMPLEMENTATION_VERSION, proj.version);
		manifest.getMainAttributes().put(Attributes.Name.IMPLEMENTATION_URL, proj.url);
		manifest.getMainAttributes().put(new Attributes.Name("Implementation-Version-Name"), "Heb Chench");
		manifest.getMainAttributes().put(new Attributes.Name("Licence"), proj.licence);
		
		TimeZone timeZone = TimeZone.getTimeZone("Europe/Paris");
		Calendar cal = Calendar.getInstance(timeZone);
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String buildDate = formater.format(cal.getTime());
		manifest.getMainAttributes().put(new Attributes.Name("Build-date"), buildDate);
		
		return manifest;
	}
	
	
	private static CommandResult copyAllJavaSource(Project proj) {
		CommandResult cr = new CommandResult();
		PathFilter fileFilter = new IncludePathFinishingWith(".java");
		
		for(Path path : proj.sourceDirList){
			for(String packageName : proj.packageNameList){
				String packagePath = packageName.replace(".","/");
				Path p = Paths.get(packagePath);
				
				// 	let's copy
				FileTools.copy( p, ProjectTools.getProdJarDir(proj), path, fileFilter);
			}
		}
		return cr;
	}

	@Override
	public CommandResult run(Project proj) {
		CommandResult cr = new CommandResult();
		if(proj.jar.name != null){
			cr = makeJar(proj);
		} // else do nothing
			
		return cr;
	}
}
