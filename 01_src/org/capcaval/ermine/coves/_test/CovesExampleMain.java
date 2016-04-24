package org.capcaval.ermine.coves._test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.capcaval.ermine.coves.CovesTools;
import org.capcaval.ermine.coves.analysis.CovesPanel;

public class CovesExampleMain extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        PasswordPaneCtrl passwordCtrl = new PasswordPaneCtrl();
        
        CovesPanel analyse = CovesTools.analyseHmiTree(PasswordPaneCtrl.class);
        System.out.println(analyse);
        
        
        Scene scene = new Scene(passwordCtrl.getView(), 300, 150);
        
        primaryStage.setTitle("Coves Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args){   
    	Application.launch(args);   }
}