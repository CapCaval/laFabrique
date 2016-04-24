package org.capcaval.ermine.jfx.layout._test;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.capcaval.ermine.jfx.layout.JfxRefPointLayout;
import org.capcaval.ermine.layout.RefPointEnum;

public class JfxRefPointLayoutMain extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("RefPoint Layout");
        stage.setMinHeight(200);
        stage.setMinWidth(200);
        
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 400, 400, Color.WHITE);
        
		// use a ref point layout
		JfxRefPointLayout layout = new JfxRefPointLayout(pane); 
        
        final ObservableList<String> lDataList = FXCollections.observableArrayList("A","B","C");
        final ListView<String> leftListView = new ListView<String>(lDataList);

        layout.addResizableNode( leftListView, 
        		RefPointEnum.topLeftPoint, 10, 10, 
        		RefPointEnum.bottomCenterPoint, -25, -10);
        
        final ObservableList<String> rDataList = FXCollections.observableArrayList("D", "E", "F");
        final ListView<String> rightListView = new ListView<String>(rDataList);

        layout.addResizableNode( rightListView, 
        		RefPointEnum.topCenterPoint, 25, 10, 
        		RefPointEnum.bottomRightPoint, -10, -10);

        Button sendRightButton = new Button(">");
        Button sendLeftButton = new Button("<");
        VBox menu = new VBox(5);
        menu.getChildren().addAll(sendRightButton,sendLeftButton);
        
        layout.addNode(menu, RefPointEnum.middleCenterPoint, -15, -40);
       
        stage.setScene(scene);
        stage.show();
    }
}