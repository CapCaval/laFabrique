package org.capcaval.ermine.coves._test;

import org.capcaval.ermine.coves.AbstractCovesApplication;
import org.capcaval.ermine.jfx.JfxApplicationTools;
import org.capcaval.ermine.jfx.JfxCommand;
import org.capcaval.ermine.jfx.JfxFrame;
import org.capcaval.lafabrique.commandline.Command;
import org.capcaval.lafabrique.common.CommandResult;

public class CovesAppExampleMain extends AbstractCovesApplication {
	@AppController
	public PasswordPaneCtrl mainCtrl;

	@JfxCommand
	@Command
	public void start() {
		System.out.println(Thread.currentThread().getName());
		
		JfxFrame frame = JfxFrame.factory.newInstance("Test ceci est.", 100, 100, 400, 400);

		PasswordPaneCtrl passwordCtrl = new PasswordPaneCtrl();

		frame.setMainCtrl(passwordCtrl);
		frame.display();
	}

	public static void main(String[] args) {
		//JfxApplicationTools.runApplication(CovesAppExampleMain.class, new String[]{"start"});
		CommandResult cr = JfxApplicationTools.runApplication(CovesAppExampleMain.class, args);
		System.out.println(cr);
	}
}