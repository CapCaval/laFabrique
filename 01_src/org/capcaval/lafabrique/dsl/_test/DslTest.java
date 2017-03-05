package org.capcaval.lafabrique.dsl._test;

import org.capcaval.lafabrique.dsl.DSLManager;
import org.capcaval.lafabrique.dsl.DslTools;
import org.capcaval.lafabrique.dsl._test.plugins.HelloWorldPlugIn;
import org.capcaval.lafabrique.dsl._test.plugins.HelloWorldPlugInImpl;
import org.junit.Test;

public class DslTest {

	@Test
	public void DslCreationTest(){
		DSLManager dslManager = DSLManager.factory.newInstance();
		
		dslManager.registerPlugin(HelloWorldPlugInImpl.class);
		
		HelloWorldDSL dsl = dslManager.loadDSL(HelloWorldDSL.class);
		
	}
	
}
