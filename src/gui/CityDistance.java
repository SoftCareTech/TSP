package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.layout.HBox;

public class CityDistance extends HBox{
	private int index;
	private boolean ok=false;
	private double input; 
	public boolean isOkay() {
		return this.ok;
	}
  public CityDistance(String name, int index) {
	
		super();
		 Label aNum = new Label("empty number");
		 aNum.setTextFill(Color.RED); 
		TextField numS = new TextField();
	
		this.index=index; 
	Label	cityLable=new Label("Distance to "+name+" is ");
	cityLable.setMaxWidth(150);
	cityLable.setMinWidth(150);
	super.getChildren().addAll(cityLable, numS,aNum);
	
	numS.setOnKeyReleased(new EventHandler<KeyEvent>() {

		@Override
		public void handle(KeyEvent k) {
		DoubleStringConverter c= new DoubleStringConverter();	
		try { 
			if(numS.getText().isEmpty()) {
				aNum.setTextFill(Color.RED);
				aNum.setText("empty number");
				 
				ok=false;
			}else {
				input=	c.fromString(numS.getText());
			aNum.setTextFill(Color.GREEN);
			aNum.setText("a number");
			 ok=true;
			/*
			 * if(k.getCode().equals(KeyCode.ENTER)) {
				go.fire();
			}
			 */
			
			}
		}catch(Exception e) {
			aNum.setTextFill(Color.RED);
			aNum.setText("not a number");
			input=-1;
			ok= false;
		} 
		
		}});
	}
		
		
		
		
	public double getDistance() {
		return input;  
		
	}
public int getIndex() {
		return this.index;
	}


}
