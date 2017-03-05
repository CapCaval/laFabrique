package org.capcaval.lafabrique.commandline._test.c3;

import org.capcaval.lafabrique.commandline.CommandLineAbstractMain;

public class C3 extends CommandLineAbstractMain{
	
	static{
		commandList.add(new ProjectFileCreationCommandCommand());
		consoleName = "C3";
	}
}
