package org.capcaval.lafabrique.lafab;

import org.capcaval.lafabrique.project.Project;

public abstract class AbstractDefaultProject {
	protected Class<? extends Project> defaultProject = null;
	
	public AbstractDefaultProject(){
		this.init();
	}
	
	public abstract void init();
	
	public void setDefaultProject(Class<? extends Project> defaultProject){
		this.defaultProject = defaultProject;
	}
	
	public Class<? extends Project> getDefaultProject(){
		return this.defaultProject;
	}
}
