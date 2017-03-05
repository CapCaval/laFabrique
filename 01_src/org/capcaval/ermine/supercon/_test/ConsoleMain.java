package org.capcaval.ermine.supercon._test;

import org.capcaval.ermine.supercon.SuperCon;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ConsoleMain extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setX(150);
		stage.setY(600);
		stage.setWidth(350);
        stage.setHeight(200);

        stage.setTitle("test");

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);
        scene.setFill(Color.WHITESMOKE);
        
        SuperCon superCon = new SuperCon();
        root.setCenter(superCon);
        BorderPane.setMargin(superCon, new Insets(12,12,12,12));
        BorderPane.setAlignment(superCon, Pos.CENTER);
        
        stage.setScene(scene);
        stage.show();
	}

}
