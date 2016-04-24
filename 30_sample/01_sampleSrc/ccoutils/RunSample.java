package ccoutils;

import org.capcaval.ccoutils.application.ApplicationTools;
import org.capcaval.ccoutils.application.annotations.AggregatedCommand;
import org.capcaval.ccoutils.application.annotations.AppInformation;
import org.capcaval.ccoutils.common.CommandResult;

import ccoutils.application.AppSampleAggregate;
import ccoutils.askii.AskiiImageSample;
import ccoutils.askii.AskiiLogoSample;
import ccoutils.compiler.CompilerSample;
import ccoutils.data.DataSample;
import ccoutils.factory.FactorySample;


@AppInformation(name="Sample", author = "CapCaval.org", asciiLogoGradient="%*' ")
public class RunSample {
	
	@AggregatedCommand
	AskiiLogoSample askiiLogoSample = new AskiiLogoSample();

	@AggregatedCommand
	AskiiImageSample askiiImageSample = new AskiiImageSample();
	
	@AggregatedCommand
	CompilerSample compilerSample = new CompilerSample();
	
	@AggregatedCommand
	FactorySample factorySample = new FactorySample();

	@AggregatedCommand
	DataSample dataSample = new DataSample();
	
	@AggregatedCommand
	AppSampleAggregate appSample = new AppSampleAggregate();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CommandResult result = ApplicationTools.runApplication(RunSample.class, args);
		System.out.println(result.toString());
	}
}
