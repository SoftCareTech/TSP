package gui; 

	import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane; 
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import util.PointXY;

	public class Plot {
		private Stage stage = new Stage(); 
		 private Canvas canvas;
		 
		public Plot(List<PointXY>pointXY, List<String> cities,	List<Integer> path,
				 double [] distance ) { 
			 canvas  = new Canvas(400, 500);
			GraphicsContext gc = canvas.getGraphicsContext2D(); 
			// Set drawing parameters  
			gc.setStroke(Color.RED);
			gc.setFill(Color.BLUE);
			gc.setLineWidth(1); 
			//gc.fillPolygon(xPoints, yPoints, nPoints);
			gc.beginPath();
			gc.moveTo(pointXY.get(0).x+100, pointXY.get(0).y+100); 
			for(Integer i:path) {
				gc.lineTo(pointXY.get(i).x+100, pointXY.get(i).y+100); 
				gc.fillOval(pointXY.get(i).x+100, pointXY.get(i).y+100, 5, 5);
				gc.fillText(cities.get(i), pointXY.get(i).x+100, pointXY.get(i).y+100, distance[i]);
				//System.out.println("YY "+pointXY.get(i).x+"  YY " +pointXY.get(i).y);
			} 
			gc.stroke();
			 
		}
		
	 
		 
		



		 







		public void plot(Window owner, String title, String res) { 
			VBox vertical = new VBox();
			vertical.setAlignment(Pos.CENTER); 
			BorderPane main = new BorderPane(vertical); 
			;
			
			Button stop = new Button("Close"); 
			HBox sto = new HBox(stop);
			sto.setAlignment(Pos.BOTTOM_RIGHT);
			main.setBottom(new VBox(new TextArea(res),sto));
			main.setCenter( new ScrollPane(canvas)); 
			stop.setPrefSize(100, 14);
			
			stop.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {

					stage.close();
					
				}
				
			});
			Scene st = new Scene(main, 700, 700);
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
	 
	
}
