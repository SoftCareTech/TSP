package util;

import java.awt.AWTException;
import java.awt.Robot;
import java.text.DecimalFormat;
import java.util.Formatter;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class S {
	 
	public static final String PATH_CITY ="css/bootstrap2.css";
	public static final String PATH_MAIN ="css/bootstrap2.css";
	public static final String PATH_NOTICES ="css/bootstrap2.css";

	static  void print (ObservableList<String> sub) {
		  System.out.println();
		  System.out.println("start");
		for(String s :sub) {
			System.out.println(s);
		}
		 System.out.println("end");
		  System.out.println( );
	}
	  
	  public static double  formDouble(double d) {
 return new Double(new DecimalFormat("##.00").format(d)).doubleValue();
	  }
	  public static void enterKeyPress() {
			Robot re;
			try {
				re = new Robot();
				//
		 //	re.keyPress(KeyCode.ENTER.);
				;

			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	  
	 static public void  notice(Window owner,String title,String warning,String solution) {
			
			VBox vertical = new VBox();
			vertical.setAlignment(Pos.CENTER);
			FlowPane controls = new FlowPane();
			BorderPane main= new BorderPane(vertical);
		 
			Label text  ;
			Label classs;
			 
			
			 classs = new Label(warning );
			 text= new Label(solution );
			
text.setTextFill(Color.GREEN);;
			text.setFont(Font.font(15));
			classs.setFont(Font.font(15));
		 
		controls.setAlignment(Pos.CENTER_RIGHT);
		 
		Button ok = new Button("Ok");
		controls.getChildren().addAll( ok);
		vertical.getChildren().addAll(new   FlowPane(
				//new ImageView(new Image("questionMark1.png")),
				classs),text);
		main.setBottom(controls);
		Stage stage = new Stage();
		stage.setResizable(false);
	 
		ok.setPrefSize(100, 14);
		 
		 ok.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				stage.close();
				
			}
			 
		 }); 
			Scene st = new Scene(main, 300, 100); 
 
      st.getStylesheets().add(PATH_NOTICES); 
			stage.setScene(st);
			stage.centerOnScreen();
		stage.setTitle(title);
		stage.initStyle(StageStyle.UNIFIED);
		stage.initModality(Modality.APPLICATION_MODAL);
			

stage.initOwner(owner);
			 
			stage.showAndWait();;
			 
			 
			
		}

	public static String doubleToString(double d) {


        Formatter f = new Formatter();
        String s =String.valueOf(f.format("%.2f", d));
        f.close();
        return s;
	}
	 

			

}
