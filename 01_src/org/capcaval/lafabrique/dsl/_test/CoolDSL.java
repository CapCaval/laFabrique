package org.capcaval.lafabrique.dsl._test;

import org.capcaval.lafabrique.dsl.annotation.ConfigDSL;

public abstract class CoolDSL implements Plugin1, Plugins2{
	
	@ConfigDSL
	public void init(){
		
//		inkscapePath("");
		svgToPng("dir");
//		svgToPng("dir", 100, 100);
//		ftpPut("path");
	}
}
