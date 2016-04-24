package org.capcaval.ermine.coves._test;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import org.capcaval.ermine.jfx.layout.JfxRefPointLayout;
import org.capcaval.ermine.jfx.panes.JfxPane;
import org.capcaval.ermine.layout.RefPointEnum;

public class PasswordPaneView extends JfxPane{
	
	public Label userlabel = new Label("Username:");
	public Label pswdLabel = new Label("Password:");
	public Button loginButton = new Button("Login");
	
	public PasswordField pswdTF = new PasswordField();;
	public TextField userTF = new TextField();;
	
	public PasswordPaneView() {
		JfxRefPointLayout layout = new JfxRefPointLayout(this);
		
		layout.addNode( this.userlabel, RefPointEnum.topLeftPoint, 80, 100);
		layout.addResizableNode(this.userTF, RefPointEnum.topLeftPoint, 180, 100, RefPointEnum.topRightPoint, -20, 120);
		layout.addNode(this.pswdLabel, RefPointEnum.topLeftPoint, 80, 150);
		layout.addResizableNode(this.pswdTF, RefPointEnum.topLeftPoint, 180, 150, RefPointEnum.topRightPoint, -20, 170);
		layout.addNode(this.loginButton, RefPointEnum.bottomCenterPoint, 0, -50);
	}
}
