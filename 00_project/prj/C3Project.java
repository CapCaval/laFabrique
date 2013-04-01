package prj;

import org.capcaval.ccproject.AbstractProject;

public class C3Project extends AbstractProject{
	
	@Override
	public void defineProject(){
		version("1.3");
		author("CapCaval.org");
		copyright("CapCaval.org");
		this.licence("MIT");

		
		source("01_src");
		exclude("_test");
		excludeDirectoryShortname("_test");
		
		librairiePath("04_lib");
		librairiesForCompiling("ccTools");
		outputClass("06_bin");

		
		jar.name("C3.jar");
		jar.outputDirectory("20_prod");
		
		// tout compiler dans bin
		// copier la compil sans les rep _test dans temp/jar
		// rapport de compil
		// faire le manifest
		// faire le jar à partir fr temp/jar
		// passer les tests avec le jar
		// rapport de test
		
		
		// java -jar c3.jar GUI
		// java -jar c3.jar newProject d:/workspace/
		// java -jar c3.jar build
		// java _jar C3.jar performTest
		
		
	}


}
