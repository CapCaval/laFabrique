package prj;

import org.capcaval.ccoutils.lafabrique.AbstractProject;

public class ccOutils extends AbstractProject{
	

	@Override
	public void defineProject(){
		name("ccOutils");
		version("0.0.1");
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
		jdkVersion("jdk1.7.0_09");

		jar.name("ccOutils.jar");
		jar.excludeDirectoryName("_test", "_design");
	}

}
