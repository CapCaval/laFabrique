package org.capcaval.ermine.supercon;

import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import org.capcaval.ermine.jfx.JfxTools;
import org.capcaval.lafabrique.lang.SystemTools;

public class SuperCon extends BorderPane{

	private AtomicInteger cursorPos;
	private StringBuilder text;
	private Rectangle cursor;
	private GraphicsContext gc;
	private double width;


	// TODO add a context  for key
	// TODO ajout d'appli
	public SuperCon(){
        this.text = new StringBuilder();
        Canvas canvas = new Canvas();
        
        canvas.setWidth(600);
        canvas.setHeight(300);
        
        this.gc = canvas.getGraphicsContext2D();
        
        Font font = Font.font(java.awt.Font.MONOSPACED, 12);
        gc.setFont(font); 

        this.cursorPos=new AtomicInteger(0);

        this.width = JfxTools.getFontWidth(font, "a");
        
        this.cursor = new Rectangle(17, 0, width, 12);
        this.cursor.setFill(new Color(0.3,0.3,0.3, 0.5));
        
        
        this.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
            	System.out.println("added to scene");
            	
            	// subscribe to key
            	newScene.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent e) -> {
            		KeyCode code = e.getCode();
            		computeKeyCode(code);
            	});
            	
            	// subscribe to char
            	newScene.addEventHandler(KeyEvent.KEY_TYPED, (KeyEvent e) -> {
                	String s = e.getCharacter();
                	byte bvalue = e.getCharacter().getBytes()[0];
                	computeChar((char)bvalue);
                });
            } 
        });
        
        drawStr(gc, text);
        
        this.getChildren().add(canvas);
        this.getChildren().add(cursor);
	}
	
	private void drawStr(GraphicsContext gc, StringBuilder text) {
		gc.setFill(Color.BLUE);
        gc.clearRect(0, 0, 800, 100);
        gc.fillRect(0, 0, 800, 300);
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.WHITE);
        gc.fillText(">" + text, 10, 10);
	}
	
    public void computeKeyCode(KeyCode code){
    	if(code == KeyCode.BACK_SPACE){
    		if(cursorPos.get() > 0){
    			text.deleteCharAt(cursorPos.decrementAndGet());
    			System.out.println("BS " + cursorPos.get() + " " + text);
    			cursor.setX(cursor.getX() - width);
    		}
    		drawStr(gc, text);
    	}
    	if(code == KeyCode.ENTER){
    		String cmd = text.toString();
    		String ret = SystemTools.execute(Paths.get(cmd),  Paths.get("."));
    		text.append("\n" + ret);
    		drawStr(gc, text);
    	}

    }
    
    public void computeChar(char c){
    	if((c>=30)&&(c<127)){
    		text.append(c);
    		cursorPos.incrementAndGet(); // just increment
			cursor.setX(cursor.getX() + width);
			
    		drawStr(gc, text);
    	}
    }
}
