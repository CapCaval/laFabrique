package org.capcaval.lafabrique.dsl._test;

import org.capcaval.lafabrique.dsl._test.plugins.HelloWorldPlugIn;
import org.capcaval.lafabrique.dsl.annotation.ConfigDSL;

public abstract class HelloWorldDSL implements HelloWorldPlugIn{
	@ConfigDSL
	public void init(){
		this.name("John");
	}
}
