package tools;

import org.capcaval.ccoutils.lafabrique.command.CommandEclipseProject;
import org.capcaval.ccoutils.lafabrique.command.CommandResult;

import prj.Project;

public class UpdateEclipseProject {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Project project = new Project();
		CommandResult cr = CommandEclipseProject.updateEclipseProject(project);
		System.out.println(cr);
	}

}
