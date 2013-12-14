package prj;

import org.capcaval.ccoutils.lafabrique.AbstractProject;

public class ccProjects extends AbstractProject{
	

	@Override
	public void defineProject(){
		name("ccProject");
		version("0.0.1");
		author("CapCaval.org");
		copyright("CapCaval.org");
		licence("MIT");
		url("http://ccoutils.capcaval.org");

		projectDir("00_prj");
		source("01_src");
		libDir("02_lib");
		
		lib("junit-4.8.2.jar");
		
		jdkVersion("jdk1.7.0_09");
		prodDirPath("20_prod");
	}

}
