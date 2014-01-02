package ccoutils;

import org.capcaval.ccoutils.application.ApplicationTools;
import org.capcaval.ccoutils.common.CommandResult;

public class RunSample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CommandResult result = ApplicationTools.runApplication(RunSample.class, args);
	}

}
