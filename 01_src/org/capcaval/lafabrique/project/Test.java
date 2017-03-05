package org.capcaval.lafabrique.project;

import org.capcaval.lafabrique.lafab.LaFabTaskDesc;

public class Test extends LaFabTaskDesc{
	public String[] libArray = new String[0];

	public void lib(String... libArray) {
		this.libArray = libArray;
	}

}
