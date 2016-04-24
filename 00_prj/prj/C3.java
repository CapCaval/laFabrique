package prj;

import org.capcaval.lafabrique.project.Project;

public class C3 extends Project{
	

	@Override
	public void defineProject(){
		name("C3");
		version("1.3");
		author("CapCaval.org");
		copyright("CapCaval.org");
		licence("MIT");

		prjDir("00_prj");
		srcDir("01_src");
		libDir("02_lib");
		
		lib("junit-4.8.2.jar");
		test.lib("junit-4.8.2.jar");
		
		
//		librairiePath("04_lib");
//		librairiesForCompiling("ccTools");
		prodDir("20_prod");

		jar.name("C3.jar");
		jar.excludedDirectoryName("_test");
		
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
