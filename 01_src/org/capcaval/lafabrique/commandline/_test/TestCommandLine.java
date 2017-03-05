package org.capcaval.lafabrique.commandline._test;

import org.capcaval.lafabrique.commandline.CommandLineAbstractMain;

public class TestCommandLine extends CommandLineAbstractMain {
	static{
		commandList.add(new CommandSample());
		consoleName = "TestCommandLine";
	}
}
