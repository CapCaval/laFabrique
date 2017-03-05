package org.capcaval.lafabrique.project;

import java.util.ArrayList;
import java.util.List;

import org.capcaval.lafabrique.lafab.LaFabTaskDesc;
import org.capcaval.lafabrique.lafab.ScriptDesc;


public class Script extends LaFabTaskDesc{
	
	public List<ScriptDesc> list = new ArrayList<>();

	public void add(String name, Class<?> classToExecute) {
		// allocate a description of the task to create
		ScriptDesc desc = new ScriptDesc(name, classToExecute);
		// keep it for next build
		this.list.add(desc);
	}

	public void add(String name, String classToExecuteStr) {
		// allocate a description of the task to create
		ScriptDesc desc = new ScriptDesc(name, classToExecuteStr);
		// keep it for next build
		this.list.add(desc);
	}

	
}
