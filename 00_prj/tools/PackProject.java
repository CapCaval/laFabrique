package tools;

import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.lafab.command.CommandPack;
import org.capcaval.lafabrique.project.Project;

import prj.DefaultProject;

public class PackProject {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Class<?> defaultProjType = new DefaultProject().getDefaultProject();
		Project proj=null;
		try {
			proj = (Project)defaultProjType.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CommandResult cr = CommandPack.pack(proj);
		System.out.println(cr);
	}

}
