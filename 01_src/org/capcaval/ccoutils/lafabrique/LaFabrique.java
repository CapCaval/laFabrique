package org.capcaval.ccoutils.lafabrique;

import org.capcaval.ccoutils.application.ApplicationTool;
import org.capcaval.ccoutils.application.annotations.AggregatedCommand;
import org.capcaval.ccoutils.application.annotations.AppInformation;
import org.capcaval.ccoutils.commandline.CommandLineComputer.ComputeResult;

@AppInformation (
		name="laFabrique",
		author="CapCaval.org",
		about= {"LaFabrique is an application builder, 100% pure Java. There is no XML, no DSL. It provides a command abstraction and a property one."
				},
		version="0.0_1"
		)

public class LaFabrique{
	@AggregatedCommand
	protected LaFabriqueCommands laFabriqueCommands = new LaFabriqueCommands();
	
	public static void main(String[] args) {
		ComputeResult r = ApplicationTool.runApplication(LaFabrique.class, args);
		System.out.println(r.returnMessage);
	}
}
