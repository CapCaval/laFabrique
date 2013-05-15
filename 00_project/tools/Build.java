package tools;

import org.capcaval.c3.commandline.command.CommandBuild;
import org.capcaval.c3.commandline.command.CommandJar;
import org.capcaval.c3.commandline.command.CommandResult;

import prj.Project;

public class Build {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Project project = new Project();
		CommandResult cr = CommandBuild.build(project);
		System.out.println(cr);

		cr = CommandJar.makeJar(project);
		System.out.println(cr);
		
		
	}

}
