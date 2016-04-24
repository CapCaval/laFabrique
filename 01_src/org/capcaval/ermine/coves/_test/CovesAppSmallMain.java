package org.capcaval.ermine.coves._test;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import org.capcaval.ermine.coves.AbstractCovesApplication;
import org.capcaval.ermine.jfx.JfxApplicationTools;
import org.capcaval.ermine.jfx.JfxCommand;
import org.capcaval.lafabrique.commandline.Command;

public class CovesAppSmallMain extends AbstractCovesApplication {

	@JfxCommand
    @Command
    public void start()
    {
        Scene scene2 = new Scene(new Group(new Button("helllllloooo")), 80, 150);
        
        Stage stage2 = new Stage();
        
        stage2.setTitle("Coves Example 2 : ) ");
        stage2.setScene(scene2);
        stage2.show();
    }
    
    public static void main(String[] args){   
    	JfxApplicationTools.runApplication(CovesAppSmallMain.class, args);
    }
}