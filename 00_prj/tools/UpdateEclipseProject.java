package tools;

import org.capcaval.ccoutils.common.CommandResult;
import org.capcaval.ccoutils.lafabrique.command.CommandEclipseProject;

import prj.ccProjects;

public class UpdateEclipseProject {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ccProjects project = new ccProjects();
		CommandResult cr = CommandEclipseProject.updateEclipseProject(project);
		System.out.println(cr);
	}

}
