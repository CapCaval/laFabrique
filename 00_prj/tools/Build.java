package tools;

import org.capcaval.ccoutils.lafabrique.command.CommandBuild;
import org.capcaval.ccoutils.lafabrique.command.CommandCompile;
import org.capcaval.ccoutils.lafabrique.command.CommandJar;
import org.capcaval.ccoutils.lafabrique.command.CommandResult;

import prj.C3;
import prj.ccOutils;

public class Build {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ccOutils project = new ccOutils();
		
		//compile sources
		CommandResult cr = CommandCompile.compile(project);
		System.out.println(cr);

		// make jar
		cr = CommandJar.makeJar(project);
		System.out.println(cr);
		
		// copy jar to lib
		
		
	}

}
