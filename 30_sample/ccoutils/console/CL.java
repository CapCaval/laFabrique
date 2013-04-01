package sample.cctools.console;

import org.capcaval.ccoutils.commandline.Command;
import org.capcaval.ccoutils.commandline.CommandLineAbstractMain;
import org.capcaval.ccoutils.commandline.CommandParam;

public class CL extends CommandLineAbstractMain {
	static{
		consoleName = "CL";
		commandList.add(new CL());
	}
	
	@Command( desc="Performed values addition")
	public int add(
			@CommandParam( name="valueList", desc="list of values")
			int... valueList){
		int value = 0;
		for(int val : valueList){
			value = value + val;
		}
		return value;
	}
}
