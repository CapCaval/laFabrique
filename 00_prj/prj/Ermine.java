package prj;

import org.capcaval.lafabrique.project.Project;

import system.JDKEnum;

//import system.JDKEnum;

public class Ermine extends Project{
	

	@Override
	public void defineProject(){
		name("Ermine");
		version("0.0.2");
		author("CapCaval.org");
		copyright("CapCaval.org");
		licence("MIT");
		url("http://ccoutils.capcaval.org");

		prjDir("00_prj");
		srcDir("01_src");
		libDir("02_lib");
		prodDir("20_prod");
		
		//lib("junit-4.8.2.jar");
		test.lib("junit-4.8.2.jar");
		jdkVersion(JDKEnum.jdk1_8_0_25);
		
		jar.name("Ermine.jar");
		jar.includeSource(true);
		jar.excludedDirectoryName("_design");
		
		pack.name("Ermine_"+ this.version + ".zip");
	}
}
