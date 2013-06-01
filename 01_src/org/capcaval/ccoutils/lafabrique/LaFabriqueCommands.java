package org.capcaval.ccoutils.lafabrique;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import org.capcaval.ccoutils.commandline.Command;
import org.capcaval.ccoutils.lang.ArrayTools;
import org.capcaval.ccoutils.lang.StringMultiLine;

import sun.misc.IOUtils;

public class LaFabriqueCommands {

	@Command
	public String newProject(String name, String path) {
		Path projectPath = Paths.get(path, name);
		List<String> subDirList = ArrayTools.newArrayList(
				"00_prj", 
				"01_src",
				"02_lib");

		StringMultiLine returnedMessage = new StringMultiLine("[laFabrique] INFO : create all following sub-dirs at " + projectPath.toString());

		try {
			for (String dir : subDirList) {
				Path p = Paths.get(projectPath.toString(), dir);
				
				if(p.toFile().exists() == true){
					System.out.print("Directory " + p + " already exist, do you want to overwrite it ? ");
					String str = System.console().readLine();
					if(str.contains("y")){
						Files.createDirectories(p);
					}
				}else{
					Files.createDirectories(p);
					returnedMessage.addLine("[laFabrique] INFO : " + p + " is created");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			returnedMessage.addLine("[laFabrique] ERROR : can not create the directory : " + projectPath.toString());
		}

		try{
			returnedMessage.addLine("#### " + this.getClass().toString());
			returnedMessage.addLine(this.getClass().getResource("laFab").toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		File outFile = new File(".");
//		FileOutputStream outStream = new FileOutputStream(outFile);
//		IOUtils.
		
		// copy the ccoutils lib
		//this.copyFile( returnedMessage, this.getClass().getResource("laFab").getFile(), projectPath.toString());
		this.copyFile( returnedMessage, "02_lib/C3.jar", projectPath.toString());
		// copy the linux script
		this.copyFile( returnedMessage, "laFab", projectPath.toString());
		// copy the windows script
		this.copyFile( returnedMessage, "laFab.bat", projectPath.toString());
		
		return returnedMessage.toString();
	}

	public void copyFile(StringMultiLine returnedMessage, String srcFile, String destDir) {
		try {
			// copy the file
			Files.copy(Paths.get(srcFile), Paths.get( destDir +"/"+ srcFile),
					StandardCopyOption.REPLACE_EXISTING);
			returnedMessage.addLine("[laFabrique] INFO : "
					+ Paths.get(destDir + "/" + srcFile).toFile().toString() + " is added");

		} catch (IOException e) {
			returnedMessage.addLine("[laFabrique] ERROR : can not copy the file : " + Paths.get(srcFile) + " to "
					+ Paths.get(destDir + "/" + srcFile));
			returnedMessage.addLine( e.toString());
			if(e.getCause() != null){
				returnedMessage.addLine(e.getCause().toString());
				}
		}
	}

	@Command
	public String build(){
		StringMultiLine returnedMessage = new StringMultiLine("[laFabrique] INFO : Build start"); 
		
		return returnedMessage.toString();
	}
	
}
