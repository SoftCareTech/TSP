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
import javafx.scene.layout.GridPane;
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

public class CityXY extends VBox{ 
	private boolean ok=false; 
	public boolean isOkay() {
		return this.ok;
	}
	TextField x_input ;
	TextField y_input ;
	TextField city ;
	public Label msg;
  public CityXY( ) {
	
		super();
		
		GridPane grid = new GridPane();
		 x_input=new TextField();
		  y_input=new TextField();
		 city=new TextField();
		  msg=new Label();
		String x="Lat ";
		String y="Log ";
		grid.addRow(0, new Label("City Name:"), city);
		grid.addRow(1, new Label(x), x_input);
		grid.addRow(2, new Label(y),y_input);
		grid.addRow(3, new Label("Status:"),msg);
		
		msg.setText("Empty fields");
		msg.setTextFill(Color.RED);  
	
	   
	super.getChildren().addAll(grid );
	
	x_input.setOnKeyReleased(new EventHandler<KeyEvent>() {

		@Override
		public void handle(KeyEvent k) {
			callTest(msg,"City",x,y);
		}});
	y_input.setOnKeyReleased(new EventHandler<KeyEvent>() {

		@Override
		public void handle(KeyEvent k) {
			callTest(msg, "City",x,y);
		
		}});
	city.setOnKeyReleased(new EventHandler<KeyEvent>() {

		@Override
		public void handle(KeyEvent k) {
			callTest(msg, "City",x,y);
		
		}});
	}
	void callTest(Label msg, String name, String x, String y ) {
		if(city.getText().isEmpty()) {
			ok=false;
			msg.setText(name+" name is empty");
			return;
		}else {
			ok= true;
			msg.setText("okay");
			 
			ok=	test(x_input,msg, x);
			if(ok)ok=	test(y_input,msg, y);
		}
	}
 private boolean test( TextField numS,Label aNum, String msgText){
	  DoubleStringConverter c= new DoubleStringConverter();	
		try { 
			if(numS.getText().isEmpty()) {
				aNum.setTextFill(Color.RED);
				aNum.setText("Empty "+msgText); 
			return false;
			}else {
			c.fromString(numS.getText());
			aNum.setTextFill(Color.GREEN);
			aNum.setText("okay");
			 return true;
			
			}
		}catch(Exception e) {
			aNum.setTextFill(Color.RED);
			aNum.setText("Not a number"); 
			return false;
		} 
  }
		
		
		
	public double getX() {
		if(ok) {
			DoubleStringConverter c= new DoubleStringConverter();	
			try {  return	c.fromString(x_input.getText());
				  }catch(Exception e) {
				 e.printStackTrace(); } 
		}
		
		return -1;
	}
public double getY() {
	if(ok) {
		DoubleStringConverter c= new DoubleStringConverter();	
		try {  return	c.fromString(y_input.getText());
			  }catch(Exception e) {
			 e.printStackTrace(); } 	
	}
	
	return -1;
	}
public String getName() {
	return city.getText();
}
public void clear() { 
city.setText("");
y_input.setText("");
x_input.setText("");
msg.setText("Empty fields");
msg.setTextFill(Color.RED);  
	
}

}
