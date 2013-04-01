package org.capcaval.ccoutils.commandline.test;

import org.capcaval.ccoutils.commandline.CommandLineAbstractMain;

public class TestCommandLine extends CommandLineAbstractMain {
	static{
		commandList.add(new CommandSample());
		consoleName = "TestCommandLine";
	}
}
