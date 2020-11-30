package util;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class ProgressPopUp {
	Stage stage = new Stage();
	ProgressBar progress;
	public boolean back=false;
	public	void  pro(double i ,String l) {
		Platform.runLater(() -> {	if(progress!=null) {
			progress.setProgress(i);
		}
		if(l!=null&&text!=null) {
			text.setText(l);
		}
		
		});
	}
	private Thread t;
	public ProgressPopUp(Thread t) {
		this.t=t;
	}
	public	void prepareStop() {
		Platform.runLater(() -> {	if(progress!=null) {
			progress.setProgress(1);
		}
		if(text!=null) {
			text.setText("finish");
		}
		
		});
	}
	public	void stop() {
		Platform.runLater(() -> {	
if(stage!=null) stage.close();
		
		});
		 back=false;
		
	}
private	Label  text;
public
void  notice(Window owner,String title) {
	back=true;
		progress= new ProgressBar(0);
			VBox vertical = new VBox();
			vertical.setAlignment(Pos.CENTER);
			FlowPane controls = new FlowPane();
			BorderPane main= new BorderPane(vertical);
		 
			  text= new Label("" );
			
                text.setTextFill(Color.GREEN);;
			text.setFont(Font.font(20)); ;
		 
		Button stop = new Button("Stop"); 
		vertical.getChildren().addAll(new   FlowPane( text,
				progress));
		main.setBottom(stop);
		main.setCenter(vertical);
		progress.setPrefWidth(300); 
		stop.setPrefSize(100, 14);
		 
		 stop.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				stage.close();
				back=false;
				
			}
			 
		 }); 
			Scene st = new Scene(main, 300, 100);
			 //st.getStylesheets().add(S.PATH_MAIN);
			stage.setScene(st);
			stage.centerOnScreen();
		stage.setTitle(title);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.initModality(Modality.WINDOW_MODAL);
			
		 

stage.initOwner(owner);
			stage.show();;
			 
			 
			
		}
			

 

}
