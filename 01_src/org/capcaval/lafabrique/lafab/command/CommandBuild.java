package org.capcaval.lafabrique.lafab.command;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.project.Project;


public class CommandBuild {

	public static CommandResult build(Project proj) {
		// first compile
		CommandResult cr =CommandCompile.compile(proj);
		
		return cr;
	}

}
