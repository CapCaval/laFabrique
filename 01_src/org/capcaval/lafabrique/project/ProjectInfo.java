package org.capcaval.lafabrique.project;

public class ProjectInfo {

	protected String name;
	protected String author;
	protected String version;

	public ProjectInfo(){
		// Do nothing except allow child class to have their own
		// default constructor
	}
	
	public ProjectInfo(String name, String author, String version) {
		this.name = name;
		this.author = author;
		this.version =version;
	}
	
	public String getName() {
		return name;
	}

	public String getAuthor() {
		return author;
	}

	public String getVersion() {
		return version;
	}
}
