package tools;

import org.capcaval.ccoutils.lafabrique.command.CommandEclipseProject;
import org.capcaval.ccoutils.lafabrique.command.CommandResult;

import prj.C3;

public class UpdateEclipseProject {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		C3 project = new C3();
		CommandResult cr = CommandEclipseProject.updateEclipseProject(project);
		System.out.println(cr);
	}

}
