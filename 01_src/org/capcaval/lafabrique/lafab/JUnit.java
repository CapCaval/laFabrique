package org.capcaval.lafabrique.lafab;

import org.capcaval.lafabrique.lafab.command.JUnitTask;
import org.capcaval.lafabrique.project.Project;
import org.capcaval.lafabrique.task.TaskDesc;

public class JUnit extends TaskDesc<Project> {
	public JUnit(){
		this.task = new JUnitTask();
	}
}
