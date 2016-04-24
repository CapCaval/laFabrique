package org.capcaval.c3.commandline;

import org.capcaval.lafabrique.commandline.CommandLineAbstractMain;

public class C3 extends CommandLineAbstractMain{
	
	static{
		commandList.add(new ProjectFileCreationCommandCommand());
		consoleName = "C3";
	}
}
