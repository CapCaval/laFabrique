package org.capcaval.lafabrique.dsl._test.plugins;


public class HelloWorldPlugInImpl implements HelloWorldPlugIn {

	protected String name;

	@Override
	public void name(String name) {
		this.name = name;
	}
}
