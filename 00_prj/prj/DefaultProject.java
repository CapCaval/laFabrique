package prj;

import org.capcaval.lafabrique.lafab.AbstractDefaultProject;


public class DefaultProject extends AbstractDefaultProject{
	@Override
	public void init() {
		// this define the default Project
		this.setDefaultProject(laFabrique.class);
	}
}
