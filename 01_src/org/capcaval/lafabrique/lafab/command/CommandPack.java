package org.capcaval.lafabrique.lafab.command;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.common.CommandResult.Type;
import org.capcaval.lafabrique.file.FileSeekerResult;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.file.JarZipTools;
import org.capcaval.lafabrique.lafab.ScriptDesc;
import org.capcaval.lafabrique.lang.StringTools;
import org.capcaval.lafabrique.lang.SystemTools;
import org.capcaval.lafabrique.lang.SystemTools.OSType;
import org.capcaval.lafabrique.project.Project;

public class CommandPack implements LaFabTask{

	public static CommandResult pack(Project proj){
		CommandResult cr = new CommandResult(Type.INFO, "Start to pack " + proj.name + "project.");
		
		// create the directories
		try {
			// remove the zip at the end is any
			String shortName = StringTools.removeEnd(".zip", proj.pack.name);
			
			String prodName = proj.productionDirPath + "/" + shortName;
			Path dirNamePath = Paths.get(prodName);
			
			if(dirNamePath.toFile().exists()){
				// clean up if already existing
				FileTools.deleteFile(dirNamePath);
				cr.addInfoMessage("Directory " + dirNamePath.toFile().getAbsolutePath() + " already exist clean it");
			}
			
			// create the directory
			FileTools.createDir(dirNamePath);
			
			// create the subdirectory
			Path libDir = Paths.get(dirNamePath.toString(), "02_lib");
			Files.createDirectory(libDir);

			Path libDirPath = proj.libDirList.get(0);
			// copy all libs
			for(String lib : proj.libList){
				// copy each lib into the pack
				FileTools.copy(libDirPath.resolve(lib), libDir);
				
			}
			
			// copy the lafab scripts
			Path lafabUnixScript = Paths.get("./laFab");
			Path lafabWinScript = Paths.get("./laFab.bat");
			
			FileTools.copy(lafabUnixScript, dirNamePath);
			FileTools.copy(lafabWinScript, dirNamePath);
			
			// copy the project scripts
			for(ScriptDesc script : proj.script.list){
				Path unixScript = Paths.get(script.getName());
				Path winScript = Paths.get(script.getName() + ".bat");
				
				FileTools.copy(unixScript, dirNamePath);
				FileTools.copy(winScript, dirNamePath);
			}
			
			// copy the produced jar if any
			if(proj.jar.name != null){
				FileTools.copy(proj.productionDirPath.resolve(proj.jar.name), libDir);
			}
			
			// Copy sources if requested
			if(proj.pack.packSource == true){
				cr.addInfoMessage("copy source directories");
				for(Path srcPath : proj.sourceDirList){
					FileTools.copy(srcPath, dirNamePath);}
			}
			
			// Copy bin if requested
			if(proj.pack.packBin == true){
				cr.addInfoMessage("copy binaries files");
				for(Path binPath : proj.binDirPath){
					FileTools.copy(binPath, dirNamePath);}
			}
			

			// Copy proj if requested
			if(proj.pack.packProj == true){
				cr.addInfoMessage("copy prj directory");
				FileTools.copy(proj.projectDir, dirNamePath);}

			
			// Copy extra things
			if(proj.pack.extraPathToPackArray != null){
				cr.addInfoMessage("copy extra files or directories" + Arrays.toString(proj.pack.extraPathToPackArray));
				
				for(String path : proj.pack.extraPathToPackArray){
					if( FileTools.isFileExist(path) == false){
						cr.addWarningMessage("Can not copy \"" + path + "\", it does not exist.");		
					}
					else{
						FileTools.copy(Paths.get(path), Paths.get(prodName));
					}
				}
			}
			
			// delete the zip if already existing
			Path zipPath = Paths.get(prodName + ".zip");
			if(proj.pack.name != null){
				zipPath = Paths.get(proj.pack.name);
			}
			
			if(Files.exists(zipPath)){
				FileTools.deleteFile(zipPath);
			}
			
			if (SystemTools.getOSType() == OSType.Windows) {

				FileSeekerResult fsResult = FileTools.seekFiles("*", false, dirNamePath);

				// create the zip file
				JarZipTools.createZipFile(
						zipPath.toFile(), 
						proj.productionDirPath.toFile(),
						fsResult.getFileList());
			}else{ // linux
				String dirNameStr = dirNamePath.getFileName().toString();
				String command = "zip -r " + dirNameStr+".zip " + dirNameStr;
				Runtime.getRuntime().exec(command, null, dirNamePath.getParent().toFile());
			}
			cr.addInfoMessage("File " + zipPath.toString() + " is created.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cr;
	}

	@Override
	public CommandResult run(Project proj) {
		CommandResult cr = new CommandResult();
		if(proj.pack.name != null){
			cr = pack(proj);
		} // else do nothing

		return cr;
	}
	
}
