package tools;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.lafab.command.CommandEclipseProject;

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
