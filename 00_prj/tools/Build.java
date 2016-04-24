package tools;

import org.capcaval.lafabrique.lafab.LaFabCommands;

import prj.DefaultProject;

public class Build {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LaFabCommands c = new LaFabCommands();
		DefaultProject dp = new DefaultProject();
		String cr = c.build(dp.getDefaultProject().getSimpleName());
		
		System.out.println(cr);
		
		
	}

}
