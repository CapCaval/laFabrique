package tools;

import org.capcaval.lafabrique.lafab.LaFabCommands;

public class BuildErmine {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LaFabCommands c = new LaFabCommands();
		String cr = c.build("Ermine");
		
		System.out.println(cr);
		
		
	}

}
