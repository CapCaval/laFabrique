package org.capcaval.ccoutils.lafabrique;

import org.capcaval.ccoutils.commandline.CommandLineAbstractMain;

public class LaFabrique extends CommandLineAbstractMain{
		
		static{
			commandList.add(new LaFabriqueCommands());
			consoleName = "laFabrique";
		}
}
