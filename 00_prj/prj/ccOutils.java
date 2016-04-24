package prj;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lafab.command.LaFabTask;
import org.capcaval.lafabrique.project.Project;

public class ccOutils extends Project{
	
	@Override
	public void defineProject(){
		name("ccOutils");
		version("0.0.3");
		author("CapCaval.org");
		copyright("CapCaval.org");
		licence("MIT");
		url("http://ccoutils.capcaval.org");

		prjDir("00_prj");
		srcDir("01_src");
		libDir("02_lib");
		prodDir("20_prod");
		packageName("org.capcaval.ccoutils");
		
		lib("junit-4.8.2.jar");

		jar.name("ccOutils.jar");
		jar.includeSource(true);
		jar.excludedDirectoryName("_test", "_design");
		jar.postTask(new LaFabTask() {
			@Override
			public CommandResult run(Project project) {
				CommandResult cr = new CommandResult();
				Path src = Paths.get(project.productionDirPath.toString() + "/" + project.jar.name);
				Path dst = Paths.get("30_sample/02_lib/"+ project.jar.name);
				FileTools.copy( src, dst);
				cr.addInfoMessage("copy the jar to " + dst.toString());
				
				return cr;
			}
		});
		
		pack.name("ccoutils.zip");
	}

}
