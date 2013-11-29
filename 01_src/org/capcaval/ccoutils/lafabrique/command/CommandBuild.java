package org.capcaval.ccoutils.lafabrique.command;

import org.capcaval.ccoutils.lafabrique.AbstractProject;


public class CommandBuild {

	public static CommandResult build(AbstractProject proj) {
		// first compile
		CommandResult cr =CommandCompile.compile(proj);
		
		return cr;
	}

}
