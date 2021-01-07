package gui; 

	import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javafx.application.Platform;
	import javafx.event.ActionEvent;
	import javafx.event.EventHandler;
	import javafx.geometry.Pos;
	import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
	import javafx.scene.control.Label;
	import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
	import javafx.scene.image.ImageView;
	import javafx.scene.layout.BorderPane;
	import javafx.scene.layout.FlowPane;
	import javafx.scene.layout.HBox;
	import javafx.scene.layout.VBox;
	import javafx.scene.paint.Color;
	import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
	import javafx.stage.Stage;
	import javafx.stage.StageStyle;
	import javafx.stage.Window;
import util.PointXY;
import util.S;

	public class Save {
		private Stage stage = new Stage(); 
		public Save( ) {  
		} 


		public void save(Window owner, String title,String tspV,String  adjV) {
			 
			VBox vertical = new VBox();
			vertical.setAlignment(Pos.CENTER); 
			BorderPane main = new BorderPane(vertical); 
			;
			
			Button stop = new Button("Close"); 
			Button tsp = new Button(" Location"); 
			Button adj = new Button("Adjacent distances "); 
			HBox sto = new HBox(stop);
			sto.setAlignment(Pos.BOTTOM_RIGHT);
			main.setBottom(new VBox(new TextArea("Select the type you create the project with."
					+ "\nAccuracy is not granttee due floating point number if you select otherwise  "),sto));
			main.setCenter( new HBox(tsp, adj)); 
			stop.setPrefSize(100, 14);
			
			stop.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {

					stage.close();
					
				}
				
			});
adj.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {

					saveTSP(adjV,".csv"); } });
tsp.setOnAction(new EventHandler<ActionEvent>() {
	
	@Override
	public void handle(ActionEvent arg0) {

		saveTSP(tspV,".tsp"); } });
			Scene st = new Scene(main, 300, 300);
			// st.getStylesheets().add(S.PATH_MAIN);
			stage.setScene(st);
			stage.centerOnScreen();
			stage.setTitle(title);
			stage.initStyle(StageStyle.DECORATED);
			stage.initModality(Modality.WINDOW_MODAL);
			
			stage.initOwner(owner);
			stage.show();
			;
			
		}
	 
		private void saveTSP(String values, String type) { 
			FileChooser fc = new FileChooser();
			
			File file = fc.showSaveDialog(stage);
			// .setId("start");
			if (file != null) {
				if (!file.exists()) { 
			File f = new File(file.getAbsolutePath() + type);
			try (FileOutputStream dout = new FileOutputStream(f)) {
				
				byte[] buffer = values.getBytes();
				
				dout.write(buffer);
				stage.close();
			} catch (FileNotFoundException e) {
				S.notice(stage, "Notice", "Cannot Open Output File", e.getLocalizedMessage());
				System.out.println("Cannot Open Output File");
				return;
			} catch (IOException e) {
				System.out.println("I/O Error: " + e);
				S.notice(stage, "Notice", "I/O Error:", e.getLocalizedMessage());
			}
		}else
					S.notice(stage, "Notice", "I/O Error: File Already exist", "Choose another file name");
			}else
				S.notice(stage, "Notice", "Operion canceled", "File not chosen");
		}
			
			// startInput
			
		 
}
