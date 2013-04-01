package org.capcaval.ccoutils.commandline;

import java.util.List;

import org.capcaval.ccoutils.lang.ReflectionTools;

public class HelpCommand {

	protected List<CommandWrapper> commandList;
	protected String prefix;
	protected String consoleName;
	
	public HelpCommand(String consoleName, List<CommandWrapper> commandList, String prefix) {
		this.prefix = prefix;
		this.commandList = commandList;
		this.consoleName = consoleName;
	}

	@Command (desc="list of available command")
	public String h() {
		return help();
	}
	
	@Command(desc="list of available command")
	public String help() {
		StringBuffer buf = new StringBuffer();
		buf.append("---------------------------------------------\n");
		buf.append( this.consoleName + " help\n");
		buf.append("---------------------------------------------\n");
		for(CommandWrapper c : this.commandList){
			Command command = c.method.getAnnotation(Command.class);
			buf.append("\t" + this.prefix + c.commandStr);
			buf.append("  : " + command.desc());

			List<CommandParam> commandParamList = ReflectionTools.getAnnotationInstance(c.method, CommandParam.class);
			Class<?>[] classTypeList = c.method.getParameterTypes();
			
			int i=0;
			for(CommandParam commandParam : commandParamList){
				buf.append("\n\t\t" + commandParam.name() + " : " + classTypeList[i++] +", "+ commandParam.desc());}
			buf.append("\n");
		}
		return buf.toString();
	}

}
