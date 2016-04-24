package tools;

import org.capcaval.lafabrique.lafab.LaFabCommands;

public class NewProject {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LaFabCommands laFab = new LaFabCommands();
		System.out.println(laFab.newProject("tutu", "."));
	}

}
