package org.capcaval.ermine.jfx._test;

import org.capcaval.ermine.coves.AbstractCovesApplication;
import org.capcaval.ermine.jfx.JfxApplicationTools;
import org.capcaval.ermine.jfx.JfxCommand;
import org.capcaval.lafabrique.commandline.Command;

public class JfxApp extends AbstractCovesApplication{

	static public Thread testThread;
	
	@JfxCommand
	@Command
	public void test(){
		testThread = Thread.currentThread();
	}
	
	
	public static void main(String[] args) {
		JfxApplicationTools.runApplication(JfxApp.class, args);
	}
}
