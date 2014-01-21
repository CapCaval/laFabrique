package ccoutils;

import org.capcaval.ccoutils.application.ApplicationTools;
import org.capcaval.ccoutils.application.annotations.AggregatedCommand;
import org.capcaval.ccoutils.application.annotations.AppInformation;
import org.capcaval.ccoutils.common.CommandResult;

import ccoutils.askii.AskiiSample;
import ccoutils.compiler.CompilerSample;


@AppInformation(name="Sample", author = "CapCaval.org", asciiLogoGradient="%*' ")
public class RunSample {
	
	@AggregatedCommand
	AskiiSample askiiSample = new AskiiSample();

	@AggregatedCommand
	CompilerSample compilerSample = new CompilerSample();

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CommandResult result = ApplicationTools.runApplication(RunSample.class, args);
		System.out.println(result.getReturnMessage());
	}
}
