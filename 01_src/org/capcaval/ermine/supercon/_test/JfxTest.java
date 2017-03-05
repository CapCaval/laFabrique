package org.capcaval.ermine.supercon._test;


import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JfxTest extends Application{

	
	interface EventComputer{
		public void computeEvent(StringBuilder str, Rectangle cursor, AtomicInteger cursorPos);
	}
	
	public static void main(String[] args) {
		launch(args);

	}

	private double width;

	@Override
	public void start(Stage stage) throws Exception {
		stage.setX(150);
		stage.setY(1000);
		stage.setWidth(150);
        stage.setHeight(60);

        stage.setTitle("test");

        //Group root = new Group();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);
        scene.setFill(Color.WHITESMOKE);

        root.setStyle("-fx-background-color: lightgray;");
//        root.prefWidthProperty().bind(scene.widthProperty());
//		root.prefHeightProperty().bind(scene.heightProperty());
        
        StringBuilder text = new StringBuilder();
        Canvas canvas = new Canvas();
//        canvas.setWidth(60);
//        canvas.setHeight(30);
        
        // auto resize
		// Bind canvas size to stack pane size.
		canvas.widthProperty().bind(root.widthProperty());
		canvas.heightProperty().bind(root.heightProperty());


        //canvas.addEventHandler(eventType, eventHandler);
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
//        /gc.setFont(new Font("Monospaced", 12));

		// Redraw canvas when size changes.
        canvas.widthProperty().addListener(evt -> drawStr(gc, text));
        canvas.heightProperty().addListener(evt -> drawStr(gc, text));

        
        Font font = Font.font(java.awt.Font.MONOSPACED, 12);
        gc.setFont(font); 

        AtomicInteger cursorPos=new AtomicInteger(0);

        //double width = 7.4;
        this.width = 7.224609375;
        
        Rectangle cursor = new Rectangle(17, 0, width, 12);
        cursor.setFill(new Color(0.9,0.9,0.9, 0.5));
        
        Map<KeyCode, EventComputer> map = new IdentityHashMap<>();
        map.put(KeyCode.LEFT, createLeftKeyComputer());
        map.put(KeyCode.RIGHT, createRightKeyComputer());
        map.put(KeyCode.BACK_SPACE, createBackSpaceComputer());
        
        stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent e) -> {
        	KeyCode code = e.getCode();
        	
        	EventComputer ec = map.get(code);
        	if(ec != null){
        		ec.computeEvent(text, cursor, cursorPos);
        		drawStr(gc, text);
        	}
        	
        });

        scene.addEventHandler(KeyEvent.KEY_TYPED, (KeyEvent e) -> {
//        	/e.getCode()
        	String s = e.getCharacter();
        	byte bvalue = e.getCharacter().getBytes()[0];
        	if((bvalue>=30)&&(bvalue<127)){
        		text.insert(cursorPos.get(), s);
        		System.out.println("Inc " + Arrays.toString(s.getBytes()));
        		cursorPos.incrementAndGet(); // just increment
    			cursor.setX(cursor.getX() + width);
    			
        		drawStr(gc, text);
        	}
        });

        
        drawStr(gc, text);
        
        Rectangle cursor2 = new Rectangle(17+width*1, 0, width, 12);
        cursor2.setFill(new Color(0.3,0.3,0.3, 0.6));
        Rectangle cursor3 = new Rectangle(17+width*2, 0, width, 12);
        cursor3.setFill(new Color(0.3,0.3,0.3, 0.7));
        Rectangle cursor4 = new Rectangle(17+width*3, 0, width, 12);
        cursor4.setFill(new Color(0.3,0.3,0.3, 0.5));
        Rectangle cursor5 = new Rectangle(17+width*4, 0, width, 12);
        cursor5.setFill(new Color(0.3,0.3,0.3, 0.6));
        Rectangle cursor6 = new Rectangle(17+width*5, 0, width, 12);
        cursor6.setFill(new Color(0.3,0.3,0.3, 0.7));
        
        //root.getChildren().add(canvas);
        root.setCenter(canvas);
        //root.setCenter(new Button("HELLO WORLD !"));
        root.getChildren().add(cursor);
//        root.getChildren().add(cursor2);
//        root.getChildren().add(cursor3);
//        root.getChildren().add(cursor4);
//        root.getChildren().add(cursor5);
//        root.getChildren().add(cursor6);
        
        System.out.println("Width " + getFontWidth(font, "A"));
        System.out.println("Width " + getFontWidth(font, "i"));
        System.out.println("Width " + getFontWidth(font, "W"));
        
        
        stage.setScene(scene);
        stage.show();
	}

	private EventComputer createBackSpaceComputer() {
		EventComputer ec = new EventComputer(){
			@Override
			public void computeEvent(StringBuilder str, Rectangle cursor, AtomicInteger cursorPos) {
				if(cursorPos.get() > 0){
					str.deleteCharAt(cursorPos.decrementAndGet());
					System.out.println("BS " + cursorPos.get() + " " + str);
					cursor.setX(cursor.getX() - width);
				}
			}
		};

		return ec;
	}

	EventComputer createLeftKeyComputer(){
		EventComputer ec = new EventComputer(){
			@Override
			public void computeEvent(StringBuilder str, Rectangle cursor, AtomicInteger cursorPos) {
				System.out.println("<-");
				if(cursorPos.get()>0){
					cursorPos.decrementAndGet();
					cursor.setX(cursor.getX() - width);}
			}
		};
		return ec;
	}

	EventComputer createRightKeyComputer(){
		EventComputer ec = new EventComputer(){
			@Override
			public void computeEvent(StringBuilder str, Rectangle cursor, AtomicInteger cursorPos) {
				System.out.println("->" + cursorPos.get() + " " + str.length());
				if(cursorPos.get()<=str.length()-1){
					cursorPos.incrementAndGet();
					cursor.setX(cursor.getX() + width);}
			}
		};
		return ec;
	}

	
	public double getFontWidth(Font font, String car){
        Text t = new Text(car);
        new Scene(new Group(t));
        t.setFont(font);
        t.applyCss();
        double w = t.getLayoutBounds().getWidth();

        return w;
	}
	
	private void drawStr(GraphicsContext gc, StringBuilder text) {
		System.out.print('.');
		gc.setFill(Color.LIGHTCYAN);
        gc.clearRect(0, 0, 800, 100);
        gc.fillText(">" + text, 10, 10);
	}

}