package org.capcaval.ermine.jfx.layout;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import org.capcaval.ermine.layout.RefPointLayout;

public class JfxRefPointLayout extends RefPointLayout<Pane, Node>{

	public JfxRefPointLayout(Pane pane) {
		super(pane);
	}

	@Override
	protected void init(final Pane pane) {
		ChangeListener<Number> listener = new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> arg0,
					Number oldValue, Number newValue) {
				
				computeLayout(
						(int)pane.widthProperty().get(),
						(int)pane.heightProperty().get()); 
			}};

			
		ChangeListener<Boolean> visibilityListener = new ChangeListener<Boolean>(){

				@Override
				public void changed(ObservableValue<? extends Boolean> arg0,
						Boolean oldValue, Boolean newValue) {
					
					computeLayout(
							(int)pane.widthProperty().get(),
							(int)pane.heightProperty().get()); 
				}};
		
		// handle the resize, move and default events
		pane.widthProperty().addListener(listener);		
		pane.heightProperty().addListener(listener);
		pane.visibleProperty().addListener(visibilityListener);
	}

	@Override
	public void addNode(Node node) {
		this.pane.getChildren().add(node);
	}

	@Override
	protected void setXOnNode(Node node, double x) {
		node.setTranslateX(x);
	}

	@Override
	protected void setYOnNode(Node node, double y) {
		node.setTranslateY(y);
	}

	@Override
	protected void setWidthOnNode(Node node, double width) {
		// resize the node
		node.resize(width, node.getBoundsInLocal().getHeight());
		
		if(node.isResizable() == true){
			// I do not like the cast but I have to do it
			// resize can only be performed on Control level.
			if(Control.class.isAssignableFrom(node.getClass())){
				Control ctrl = (Control)node;
				ctrl.setPrefWidth(width);
			} else if(Region.class.isAssignableFrom(node.getClass())){
				Region region = (Region)node;
				region.setPrefWidth(width);
			}
		}
	}

	@Override
	protected void setHeightOnNode(Node node, double height) {
		// resize the node
		node.resize(node.getBoundsInLocal().getWidth(), height);
		
		if(node.isResizable() == true){
			// I do not like the cast but i have to do it
			// resize can only be performed on Control level.
			if(Control.class.isAssignableFrom(node.getClass())){
				Control ctrl = (Control)node;
				ctrl.setPrefHeight(height);
			} else if(Region.class.isAssignableFrom(node.getClass())){
				Region region = (Region)node;
				region.setPrefHeight(height);
			}
		}
	}

	@Override
	public void removeNodeinParent(Node node) {
		this.pane.getChildren().remove(node);
	}

	@Override
	protected int getWidth(Node node) {
		return (int)node.getBoundsInLocal().getWidth();
	}

	@Override
	protected int getHeight(Node node) {
		return (int)node.getBoundsInLocal().getHeight();
	}
}
