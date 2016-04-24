package org.capcaval.ermine.jfx;


import java.util.concurrent.atomic.AtomicReference;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class SimpleWindowApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
    	try{
    	
        stage.initStyle(StageStyle.UNDECORATED);
        BorderPane root = new BorderPane();

        //Rectangle rect = new Rectangle(5,5,40,30);
        Circle circleBG = new Circle(20,20,40);
        circleBG.setFill(Color.rgb(100, 200, 0, .8));
        root.getChildren().add(circleBG);

        //addDraggingSceneOnNode(circleBG, stage);
        

        // close button
        final Circle close = new Circle(35,5,5);
        close.setFill(Color.rgb(100, 20, 0, .8));
        root.getChildren().add(close);

        final Circle drag = new Circle(25,5,5);
        drag.setFill(Color.rgb(100, 20, 0, .8));
        root.getChildren().add(drag);
        
        addDraggingSceneOnNode(drag, stage);

        final Circle drag2 = new Circle(15,15,5);
        drag2.setFill(Color.rgb(200, 200, 0, .8));
        root.getChildren().add(drag2);
        
        setDraggingNode(drag2, stage);
        
        
        final Circle resize = new Circle(50,40,5);
        resize.setFill(Color.rgb(20, 20, 100, .8));
        root.getChildren().add(resize);

        addResizeSceneOnNode(resize, stage);
        
        close.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	System.exit(0);
            }
        });
        
        newZoomAnimation(close, new Duration(2000));
        
        Scene scene = new Scene(root, 100, 60);
        //scene.setFill(Color.BLUEVIOLET);
        scene.setFill(Color.rgb(125	, 0, 0, .8));
        
        stage.initStyle(StageStyle.TRANSPARENT);
        
        if(JfxTools.isTransparencySupported() == true){
        	System.out.println("transparency is ok");
        	stage.initStyle(StageStyle.TRANSPARENT);}
        
        stage.setScene(scene);
        stage.show();
    	}catch (Exception e) {
    		e.printStackTrace();
    		System.out.println("****************************************");
    	}
    }
    
    public static Timeline newZoomAnimation(final Node node, final Duration duration){
    	final Timeline tl = new Timeline();
        
        KeyFrame kf1 = new KeyFrame(
        		Duration.ZERO, 
        		new KeyValue(node.scaleXProperty(), 1),
        		new KeyValue(node.scaleYProperty(), 1));

        KeyFrame kf2 = new KeyFrame(
        		new Duration(duration.toMillis()/2), 
        		new KeyValue(node.scaleXProperty(), 2),
        		new KeyValue(node.scaleYProperty(), 2));
        
        KeyFrame kf3 = new KeyFrame(
        		duration, 
        		new KeyValue(node.scaleXProperty(), 1),
        		new KeyValue(node.scaleYProperty(), 1));


        tl.getKeyFrames().addAll(kf1, kf2, kf3);
        tl.setAutoReverse(true);
        tl.setCycleCount(Timeline.INDEFINITE);
        
        
        node.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				tl.play();
			}
		});
        
        node.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				node.scaleXProperty().set(1);
				node.scaleYProperty().set(1);
				tl.stop();
			}
		});
        
        return tl;
    }
    
    public static void addDraggingSceneOnNode(final Node node, final Stage stage){
    	final AtomicReference<Double> xOffsetRef = new AtomicReference<Double>();
    	final AtomicReference<Double> yOffsetRef = new AtomicReference<Double>();
    	
    	node.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	xOffsetRef.set(event.getSceneX());
                yOffsetRef.set(event.getSceneY());
            }
        });
        
    	node.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffsetRef.get());
                stage.setY(event.getScreenY() - yOffsetRef.get());
            }
        });

    }
    
    public static void setDraggingNode(final Node node, final Stage stage){
    	final AtomicReference<Double> deltaX = new AtomicReference<Double>();
    	final AtomicReference<Double> deltaY = new AtomicReference<Double>();
    	
    	node.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	deltaX.set(event.getSceneX()-node.getTranslateX());
            	deltaY.set(event.getSceneY()-node.getTranslateY());
            }
        });
        
    	node.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                node.setTranslateX(event.getSceneX() - deltaX.get());
                node.setTranslateY(event.getSceneY() - deltaY.get());
            }
        });

    }
    

    enum CardinalPositionEnum{northWest, northEast, southWest, southEast}
    
    public static CardinalPositionEnum getCardinalPosition(Node node, int width, int height){
    	int x =  (int)node.getTranslateX();
    	int y =  (int)node.getTranslateY();
    	
    	CardinalPositionEnum card=CardinalPositionEnum.northEast;

    	if(x<width/2){
    		// west we are
    		if(y<height/2){ 
    			// north we are
    			card = CardinalPositionEnum.northWest;
    		}else{
    			// south we are
    			card = CardinalPositionEnum.southWest;
    		}
    	}else if(y<height/2){ // east we are 
    		// east and north we are
    		card = CardinalPositionEnum.northEast;
   		}
    	
    	return card;
    } 
    
    public static void addResizeSceneOnNode(final Node node, final Stage stage){
    	setDraggingNode(node, stage);
    	
        final AtomicReference<Double> xRef = new AtomicReference<Double>();
        final AtomicReference<Double> yRef = new AtomicReference<Double>();
    	
        final AtomicReference<Double> widthRef = new AtomicReference<Double>();
        final AtomicReference<Double> heightRef = new AtomicReference<Double>();

        final AtomicReference<Double> xCornerRef = new AtomicReference<Double>();
        final AtomicReference<Double> yCornerRef = new AtomicReference<Double>();

        
    	node.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	xRef.set(event.getScreenX());
                yRef.set(event.getScreenY());
                
                widthRef.set(stage.getWidth());
                heightRef.set(stage.getHeight());
            }
        });
        
    	node.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	double deltaX = event.getScreenX() - xRef.get();
            	double deltaY = event.getScreenY() - yRef.get();
                
                stage.setWidth(widthRef.get() + deltaX);
                stage.setHeight(heightRef.get() + deltaY);
                
                // move also the resize gadget
                //node.setTranslateX(event.getX()-deltaX);
                //node.setTranslateY(event.getY()-deltaY);
            }
        });

    }

}