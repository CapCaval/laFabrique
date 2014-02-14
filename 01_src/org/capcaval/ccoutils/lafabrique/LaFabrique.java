package org.capcaval.ccoutils.lafabrique;

import org.capcaval.ccoutils.application.ApplicationTools;
import org.capcaval.ccoutils.application.annotations.AggregatedCommand;
import org.capcaval.ccoutils.application.annotations.AppInformation;
import org.capcaval.ccoutils.common.CommandResult;

@AppInformation (
		name="laFabrique",
		author="CapCaval.org",
		about= {"LaFabrique is an application builder, 100% pure Java. There is no XML,\nno DSL. It provides a command abstraction and a property one."
				},
		version="0.0_1"
		)

public class LaFabrique{
	@AggregatedCommand
	protected LaFabriqueCommands laFabriqueCommands = new LaFabriqueCommands();
	
	public static void main(String[] args) {
		CommandResult r = ApplicationTools.runApplication(LaFabrique.class, args);
		System.out.println(r.toString());
	}
}
