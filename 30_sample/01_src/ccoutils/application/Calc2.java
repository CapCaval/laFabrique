package ccoutils.application;

import org.capcaval.ccoutils.application.ApplicationTools;
import org.capcaval.ccoutils.application.annotations.AppInformation;
import org.capcaval.ccoutils.commandline.Command;
import org.capcaval.ccoutils.common.CommandResult;

@AppInformation(author = "FinistJUG",about = "calculator")
public class Calc2 {
	@Command
	public int adds(int a, int b){
		return a+b;
	}
	
	
	public static void main(String[] args) {
		CommandResult r = ApplicationTools.runApplication(Calc2.class, args);
		System.out.println(r.getReturnMessage());
	}
}
