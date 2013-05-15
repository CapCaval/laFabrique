package prj;

import java.nio.file.Paths;

import org.capcaval.ccproject.AbstractProject;

public class Project extends AbstractProject{
	
	@Override
	public void defineProject(){
		version("1.3");
		author("CapCaval.org");
		copyright("CapCaval.org");
		licence("MIT");

		source("01_src");
		lib("junit-4.8.2.jar");
		
		librairiePath("04_lib");
		librairiesForCompiling("ccTools");
		prodDirPath("20_prod");

		jar.name("C3.jar");
		jar.excludeDirectoryName("_test");
		
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
