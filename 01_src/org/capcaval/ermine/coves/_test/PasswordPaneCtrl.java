package org.capcaval.ermine.coves._test;

import org.capcaval.ermine.coves.Controller;

public class PasswordPaneCtrl implements Controller<PasswordPaneView> {
	PasswordPaneView view = new PasswordPaneView();

	@Override
	public PasswordPaneView getView() {
		return this.view;
	}

	@Override
	public void init() {
	} 
}
