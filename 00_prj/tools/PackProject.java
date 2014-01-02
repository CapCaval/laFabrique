package tools;

import org.capcaval.ccoutils.common.CommandResult;
import org.capcaval.ccoutils.lafabrique.command.CommandPack;

import prj.ccOutils;

public class PackProject {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//ccProjects project = new ccProjects();
		ccOutils project = new ccOutils();
		
		CommandResult cr = CommandPack.pack(project);
		System.out.println(cr);
	}

}
