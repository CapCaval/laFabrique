package prj;

import org.capcaval.ccoutils.lafabrique.AbstractProject;


public class Sample extends AbstractProject{
	
	@Override
	public void defineProject(){
		name("Sample");
		version("1.3");
		author("CapCaval.org");
		copyright("CapCaval.org");
		licence("MIT");
		url("http://ccoutils.capcaval.org");

		srcDir("01_sampleSrc");
		
		libDir("02_lib");
		lib("ccOutils.jar");
		
		binDir("10_bin");
		
		jdkVersion("jdk1.7.0_09");
	}
}
