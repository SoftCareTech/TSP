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
		private List<PointXY>pointXY; List<String> 
		cities;	List<Integer> path;
		 double [] distance ;
		 private double zoom= 1;
		double invertNegavative=0;
		public Plot(List<PointXY>pointXY, List<String> 
		cities,	List<Integer> path,
				 double [] distance ) { 
			this.pointXY=pointXY;
			this.cities=cities;
			this.path=path;
			this.distance=distance;
			
			for(PointXY p:pointXY) {
				 if(p.x<invertNegavative) {
					 invertNegavative=p.x;
				 }

				 if(p.y<invertNegavative) {
					 invertNegavative=p.y;
				 }
				
				 
			}
			if(invertNegavative<0)
			invertNegavative=invertNegavative*-1;
			//invertNegavative=0;
			canvas  = new Canvas(w, h); 
			zoom();
			 
		}
		
	 
		 
		



		 



int w=4000;
int h=5000;
 public void zoom() {
	 System.out.println("INVERT NEGATIVE  "+ invertNegavative);
	 GraphicsContext gc = canvas.getGraphicsContext2D(); 
	 gc.clearRect(0, 0, w,h); // First clear the canvas
		// Set drawing parameters  
		gc.setStroke(Color.RED);
		gc.setFill(Color.BLUE);
		gc.setLineWidth(1); 
		//gc.fillPolygon(xPoints, yPoints, nPoints);
		gc.beginPath();
		double x=(((pointXY.get(0).x+ invertNegavative+margin)*zoom)-margin*zoom)+margin;
		double y=(((pointXY.get(0).y+ invertNegavative+margin)*zoom)-margin*zoom)+margin;
		gc.moveTo(x, y); 
		for(Integer i:path) {
			x=(((pointXY.get(i).x+ invertNegavative+margin)*zoom)-margin*zoom)+margin;
			  y=((pointXY.get(i).y+ invertNegavative+margin)*zoom-margin*zoom)+margin;
			gc.lineTo(x, y); 
			gc.fillOval(x, y, circlRadius, circlRadius);
			gc.fillText(cities.get(i), x, y, distance[i]);
			//System.out.println("YY "+pointXY.get(i).x+"  YY " +pointXY.get(i).y);
		} 
		gc.stroke();
		 	 
		 }
 public void zoomOut() { 
	  
	 zoom=zoom+4;
	 zoom();
		 }
 public void zoomIn() { 
	 if(zoom>2)
	 zoom=zoom-2;
	 zoom();
		 }
 int margin =10;
 int circlRadius=10;
 Window owner; String title; String res;

		public void plot(Window owner, String title, String res) { 
			this.owner =owner;
			this.title =title;
			this.res =res;
			
			VBox vertical = new VBox();
			vertical.setAlignment(Pos.CENTER); 
			BorderPane main = new BorderPane(vertical); 
			;
			
			Button stop = new Button("Close"); 
			Button in = new Button("Zoom in"); 
			Button out = new Button("Zoom out"); 
			HBox sto = new HBox(out, in,stop);
			sto.setAlignment(Pos.BOTTOM_RIGHT);
			HBox top = new HBox(in,out);
			top.setAlignment(Pos.BOTTOM_RIGHT);
			main.setTop(top);
			main.setBottom(new VBox(new TextArea(res),sto));
			main.setCenter( new ScrollPane(canvas)); 
			
			stop.setPrefSize(100, 14);
out.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) { zoomOut(); } });	
in.setOnAction(new EventHandler<ActionEvent>() {
	@Override
	public void handle(ActionEvent arg0) { zoomIn(); } });	
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
