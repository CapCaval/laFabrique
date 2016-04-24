package org.capcaval.ermine.jfx._sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import org.capcaval.ermine.jfx.JfxFrame;
import org.capcaval.ermine.jfx.JfxFrameCallBack;
import org.capcaval.ermine.jfx.panes.JfxCardPane;
import org.capcaval.ermine.jfx.panes.JfxTestPane;

public class JfxCardPaneMain {

	public static void main(String[] args) {
		JfxFrame frame = JfxFrame.factory.newInstance(
				"CardPane", 10, 10, 400, 400, new JfxFrameCallBack() {
					@Override
					public void notifyFrameCreated(JfxFrame frame) {
						frame.setbackgroundColor(Color.CADETBLUE);
						
						final JfxCardPane pane = new JfxCardPane();
						pane.setBackgroundColor(Color.LIGHTBLUE);
						frame.setView(pane);
						
						final JfxTestPane p1 = new JfxTestPane("pane 1");
						final JfxTestPane p2 = new JfxTestPane("pane 2");
						
						p1.setBackgroundColor(Color.ORANGERED);
						Button button1 = new Button("next");
						button1.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent arg0) {
								pane.display(p2);
							}
						});
						p1.addNode(button1, 50, 50);
						
						p2.setBackgroundColor(Color.ORANGE);
						Button button2 = new Button("previous");
						button2.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent arg0) {
								pane.display(p1);
							}
						});
						p2.addNode(button2, 120, 50);
						
						pane.display(p1);
						
						frame.display();
					}
				});
	}

}
