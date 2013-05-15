package org.capcaval.ccoutils.commandline._test.c3;

import org.capcaval.ccoutils.commandline.CommandLineAbstractMain;

public class C3 extends CommandLineAbstractMain{
	
	static{
		commandList.add(new ProjectFileCreationCommandCommand());
		consoleName = "C3";
	}
}
