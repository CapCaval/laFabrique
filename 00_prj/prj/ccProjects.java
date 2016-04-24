package prj;

import org.capcaval.lafabrique.project.Project;

public class ccProjects extends Project{
	

	@Override
	public void defineProject(){
		name("ccProject");
		version("0.0.1");
		author("CapCaval.org");
		copyright("CapCaval.org");
		licence("MIT");
		url("http://capcaval.org");

		prjDir("00_prj");
		srcDir("01_src");
		libDir("02_lib");
		binDir("10_bin");
		prodDir("20_prod");
		
		lib("junit-4.8.2.jar");
	}

}
