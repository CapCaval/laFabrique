package org.capcaval.lafabrique.lafab._test;
import org.capcaval.lafabrique.application.ApplicationTools;
import org.capcaval.lafabrique.application.annotations.AppInformation;
import org.capcaval.lafabrique.common.CommandResult; 

@AppInformation (
		name="CoolApp",
		author="CapCaval.org",
		about= {"describe here what your application does."
				},
		version="0.0_1"
		)

public class CoolApp{
	public static void main(String[] args) {
		CommandResult r = ApplicationTools.runApplication(CoolApp.class, args);
		System.out.println(r.toString());
	}
}
