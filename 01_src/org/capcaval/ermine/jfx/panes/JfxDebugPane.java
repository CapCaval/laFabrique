package org.capcaval.ermine.jfx.panes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import org.capcaval.ermine.jfx.JfxTools;

public class JfxDebugPane extends Pane{

	Canvas canvas = new Canvas(500, 600);
	int i=0;
	
	public JfxDebugPane(){
		ChangeListener<Number> listener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				System.out.println(getWidth() + " " + getHeight());
				paint(canvas.getGraphicsContext2D());
			}
		};
		
		this.getChildren().add(this.canvas);
		
		this.widthProperty().addListener(listener);
		this.heightProperty().addListener(listener);
	}
	
	public void paint(GraphicsContext gc){
	    // clean
		gc.setFill(Color.WHITE);
	    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		gc.setFill(Color.DARKBLUE);
		gc.fillText("Hello " + this.i++, 50, 60);
	}
	
	public void setBackgroundColor(Color color){
		this.setStyle("-fx-background-color: #" + JfxTools.toStyleString(color));
	}
}
