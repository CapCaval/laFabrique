package tools;

import org.capcaval.lafabrique.lafab.LaFabCommands;

import prj.DefaultProject;

public class BuildC3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LaFabCommands c = new LaFabCommands();
		DefaultProject dp = new DefaultProject();
		String cr = c.build("C3");
		
		System.out.println(cr);
		
		
	}

}
