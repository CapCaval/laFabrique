package org.capcaval.lafabrique.lafab;

import org.capcaval.lafabrique.application.ApplicationTools;
import org.capcaval.lafabrique.application.annotations.AggregatedCommand;
import org.capcaval.lafabrique.application.annotations.AppInformation;
import org.capcaval.lafabrique.common.CommandResult;

@AppInformation (
		name="laFabrique",
		about= {"LaFabrique is an application factory, 100% pure Java. There is no XML,\nno DSL. It provides a command abstraction and a property one."
				}
		)

public class LaFab{
	@AggregatedCommand
	protected LaFabCommands laFabriqueCommands = new LaFabCommands();
	
	public static void main(String[] args) {
		CommandResult r = ApplicationTools.runApplication(LaFab.class, args);
		System.out.println(r.toString());
	}
}
