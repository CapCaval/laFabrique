package org.capcaval.ccoutils.lafabrique.command;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.capcaval.ccoutils.common.CommandResult;
import org.capcaval.ccoutils.file.PathFilter;
import org.capcaval.ccoutils.file.FileSeekerResult;
import org.capcaval.ccoutils.file.FileTools;
import org.capcaval.ccoutils.file.JarZipTools;
import org.capcaval.ccoutils.lafabrique.AbstractProject;
import org.capcaval.ccoutils.lang.ArrayTools;

public class CommandPack {

	public static CommandResult pack(AbstractProject proj){
		CommandResult result = new CommandResult("pack started on " + proj.name);
		
		// create the directories
		try {
			String projName = proj.productionDirPath + "/" + proj.name + "_" +proj.version;
			Path dirNamePath = Paths.get(projName);
			
			if(dirNamePath.toFile().exists()){
				// clean up if already existing
				FileTools.deleteFile(dirNamePath);
				result.addMessage("directory " + dirNamePath.toFile().getAbsolutePath() + " already exist clean it");
			}
			
			// create the directory
			Files.createDirectory(dirNamePath);
			
			// copy all the sample directory
			FileTools.copy( Paths.get("30_sample"), dirNamePath, Paths.get("30_sample"), new PathFilter(){
				List<String> excludeDirList = ArrayTools.newArrayList( "10_bin", "20_prod", ".classpath", ".project");
				
				@Override
				public boolean isPathValid(Path path) {
					boolean valid = true;
					
					if(path.toFile().getName().endsWith("~")){
						valid = false;
					}
					else if(this.containsListValues(path.toFile().getAbsolutePath(), this.excludeDirList)){
						valid = false;
					}
						
					return valid;
				}

				private boolean containsListValues(String absolutePath, List<String> excludeDirList) {
					boolean returnValue = false;
					for(String str : excludeDirList){
						if(absolutePath.contains(str) == true){
							returnValue = true;
							// bye bye
							break;
						}
					}
					return returnValue;
				}});
			
			FileSeekerResult fsResult = FileTools.seekFiles("*", false, dirNamePath);
			
			// create the zip file
			JarZipTools.createZipFile(
					new File( projName+ ".zip"), 
					proj.productionDirPath.toFile(), 
					fsResult.getFileList());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//FileTool.
		
		// copy all the sample directory
		//FileTools.copy(source, dest)
		
		
		// laFab + laFab.bat
		// 00_prj
		// 02_lib + ccOutils.jar
		// 30_sample
		
		return result;
	}
	
}
