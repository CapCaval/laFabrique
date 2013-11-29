package tools;

import java.nio.file.Paths;

import org.capcaval.ccoutils.lafabrique.LaFabriqueCommands;

public class NewProject {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LaFabriqueCommands laFab = new LaFabriqueCommands();
		System.out.println(laFab.newProject("tutu", "."));
	}

}
