package prj;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lafab.LaFab;
import org.capcaval.lafabrique.lafab.command.LaFabTask;
import org.capcaval.lafabrique.project.Project;

public class laFabrique extends Project{
	
	@Override
	public void defineProject(){
		name("laFabrique");
		version("0.0.4");
		author("CapCaval.org");
		copyright("CapCaval.org");
		licence("MIT");
		url("http://laFrabrique.capcaval.org");

		prjDir("00_prj");
		srcDir("01_src");
		libDir("02_lib");
		prodDir("20_prod");
		
		packageName("org.capcaval.ccoutils", "org.capcaval.lafabrique");
		
		lib("junit-4.8.2.jar");
		
		script.add("laFab", LaFab.class);
		
		jar.name("laFabrique.jar");
		jar.includeSource(true);
		jar.excludedDirectoryName("_test", "_design");
		jar.postTask(new LaFabTask() {
			@Override
			public CommandResult run(Project project) {
				CommandResult cr = new CommandResult();
				Path src = Paths.get(project.productionDirPath.toString() + "/" + project.jar.name);
				Path dst = Paths.get("02_lib");
				FileTools.copy( src, dst);
				cr.addInfoMessage("copy the jar to " + dst.toString());
				
				Path testPath = Paths.get("/home/mik/workspace/laFabSample/02_lib");
				if(FileTools.isFileExist(testPath)==false){
					cr.addErrorMessage("Following path does not exist : " + testPath.toString());
				}
				FileTools.copy( src, testPath);
				cr.addInfoMessage("copy the jar to " + testPath.toString());
				return cr;
			}
		});
		
		pack.name("laFabrique_"+ this.version + ".zip");
	}

}
