package org.capcaval.lafabriquegui.ccdepend;

import java.io.File;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.capcaval.ermine.jfx.layout.JfxRefPointLayout;
import org.capcaval.ermine.layout.RefPointEnum;
import org.capcaval.lafabrique.ccdepend.CCDependTools;
import org.capcaval.lafabrique.ccdepend.meta.Item;
import org.capcaval.lafabrique.ccdepend.meta.Package;

public class IHMMain  extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
      stage.setWidth(800);
      stage.setHeight(600);
      stage.setTitle("Depencency");

      Pane pane = new Pane();
      Scene scene = new Scene(pane);
      scene.setFill(Color.SKYBLUE);
      
      JfxRefPointLayout layout = new JfxRefPointLayout(pane);    

      TreeItem<Item> rootItem = new TreeItem<Item> ();

      rootItem.setExpanded(true);

      ObservableList<Item> dataList = FXCollections.observableArrayList();
      
      TreeView<Item> tree = new TreeView<>(rootItem);
      ListView<Item> list = new ListView<Item>(dataList);
      
      list.setCellFactory(myl -> new ListCellImpl());

      tree.setShowRoot(false);
      
	tree.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> {
        	Item[] itemArray = oldValue.getValue().getDependecies();
  		  // remove all
  		  dataList.removeAll(itemArray);
  		  // fill all
  		  for(Item depItem : newValue.getValue().getDependecies()){
  			dataList.add(depItem);
  		  }
  		  //dataList.replaceAll(newValue.getValue().getDependecies());
	});    		  
    		  
      
      tree.setCellFactory(new Callback<TreeView<Item>,TreeCell<Item>>(){
          @Override
          public TreeCell<Item> call(TreeView<Item> p) {
              return new TextFieldTreeCellImpl();
          }
      });
      
      
      
      Button openDirBtn = new Button();
      openDirBtn.setText("Open");
      openDirBtn.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
              DirectoryChooser directoryChooser = new DirectoryChooser();
              File dir = directoryChooser.showDialog(stage);
               
              if(dir != null){
            	  Item[] itemArray = CCDependTools.analyse(Paths.get(dir.getAbsolutePath()), "*.java");
            	  for(Item item : itemArray){
            		  addItem(item, rootItem);
            		  
            	  }
              }
          }

		private void addItem(Item item, TreeItem<Item> rootItem) {
			TreeItem<Item> treeItem = new TreeItem<>(item);
			rootItem.getChildren().add(treeItem);
			
			if(Package.class.isAssignableFrom(item.getClass())){
				Package pkg = (Package)item;
				
				for(Item childItem : pkg.getChildren()){
					addItem(childItem, treeItem);
				}
			}
			
		}
      });
      
      
      layout.addNode(openDirBtn);
      layout.addResizableNode(tree, RefPointEnum.topLeftPoint, 10, 30, RefPointEnum.bottomCenterPoint, -5, -10);
      layout.addResizableNode(list, RefPointEnum.topCenterPoint, 5, 30, RefPointEnum.bottomRightPoint, -10, -10);
      
      stage.setScene(scene);
      stage.show();
	}

}
