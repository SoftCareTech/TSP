package gui; 
//StageModalityApp.java

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
import static javafx.stage.Modality.NONE;
import static javafx.stage.Modality.WINDOW_MODAL;

import java.util.List;

import static javafx.stage.Modality.APPLICATION_MODAL;
import javafx.stage.Window;
public class Main extends Application {
public static void main(String[] args) {
Application.launch(args);
}
 
@Override
public void start(Stage stage) {   
Scene scene = new Scene(new Start(stage), 700, 600);
stage.setScene(scene);
stage.setTitle("Transportation Sales Problem ");
stage.show();  
} 
 
}


