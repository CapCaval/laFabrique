package org.capcaval.ermine.jfx;


import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.capcaval.ermine.coves.AbstractCovesApplication;
import org.capcaval.ermine.coves.Controller;

public class JfxFrame {

	public static JfxFrameFactory factory = new JfxFrameFactoryImpl();

	protected Stage stage;
	protected Scene scene;
	protected AbstractCovesApplication application;

	protected Controller<?> mainCtrl;

	private BorderPane mainPane;
	
	List<JfxFrameEvent> observerList = new ArrayList<>(); 

	protected JfxFrame(){
//		this.init("", 0, 0, null);
	}
	
	protected JfxFrame(final String label, final int x, final int y) {
		this.init(label, x, y, null);
	}
	
	protected JfxFrame(String label, int x, int y, AbstractCovesApplication application) {
		this.init(label, x, y, application);
	}

	protected JfxFrame(String label, int x, int y, int width, int height, AbstractCovesApplication application) {
		this.init(label, x, y, width, height, application);
	}
	
	protected void init(final String label, final int x, final int y,
			AbstractCovesApplication application) {
		this.init(label, x, y, 200, 200, application); // default width and height are set to 200
	}

	protected void init(final String label, final int x, final int y,
			int width, int height, AbstractCovesApplication application) {
		// check out if JavaFx is started, do it if not. 
		JfxApplication.start();
		
		
		this.stage = new Stage();
		this.stage.setX(x);
		this.stage.setY(y);
		this.stage.setWidth(width);
		this.stage.setHeight(height);
		this.stage.setTitle(label);

		this.mainPane  = new BorderPane();
		
		this.scene = new Scene(mainPane);
		
		this.stage.setScene(JfxFrame.this.scene);
		
		this.application = application;
		
		this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				// call all observers
				for(JfxFrameEvent observer: observerList){
					observer.notifyFrameIsClosing();
				}
			}
		});
	}

	public Stage getStage() {
		return this.stage;
	}

	public Scene getScene() {
		return this.scene;
	}

	public void display() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if(mainCtrl!=null){
					mainCtrl.init();}
				JfxFrame.this.stage.show();
			}
		});
	}

	public void setMainCtrl(Controller<?> controller) {
		this.mainCtrl = controller;
		
		// apply language if any
		if(this.application != null){
			this.application.applyLanguage(controller);}
		
		Node node = (Node)this.mainCtrl.getView();
	
		this.mainPane.setCenter(node);
	}
	
	public void setView(Node node){
		this.mainPane.setCenter(node);
	}
	
	public void setbackgroundColor(Color color){
		JfxTools.setBackgroundColor(this.mainPane, color);
	}
	
	public void subscribeToFrameEvent(JfxFrameEvent observer){
		this.observerList.add(observer);
	}
	
	public void unsubscribeToFrameEvent(JfxFrameEvent observer){
		this.observerList.remove(observer);
	}

}
