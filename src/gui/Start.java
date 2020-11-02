package gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.stream.events.Namespace;

import algorithm.SalesmanGenome;
import algorithm.Salesmensch;
import algorithm.SelectionType;
import algorithm.TSPNearestNeighbour;
import algorithm.TspDynamicProgrammingIterative;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import util.S;

public class Start extends BorderPane {
	public List<String> mCities;
	public List<Integer> mDirection;
	public double mMatrix[][] = new double[50][50];
	private double mCost;
	private int alg=1;
	private Stage stage;
	public Start(Stage stage) {
		super();
		// TODO Auto-generated constructor stub
		this.stage=stage;
		Button openProject = new Button("Open TSP");
		Button newCity = new Button("Add city "); 
		HBox toplayout= new HBox(openProject, newCity);

		Button clear = new Button("clear input");
		Button save = new Button("save");
		Button start = new Button("start");
		Button stop = new Button("stop");
		TextArea  startInput = new TextArea();
		TextArea  startResult = new TextArea();
		TextArea  startProgress = new TextArea();
		
		// Create the radio buttons.
				RadioButton rbGen = new RadioButton("Gentic algorithm");
				RadioButton rbDyn = new RadioButton("Dynamic algorithm");
				RadioButton rbKnn = new RadioButton("KNN algorithm");
				// Create a toggle group.
				ToggleGroup tg = new ToggleGroup();
				// Add each button to a toggle group.
				rbDyn.setToggleGroup(tg);
				rbKnn.setToggleGroup(tg);
				rbGen.setToggleGroup(tg);
				rbGen.setSelected(true);
				// Handle action events for the radio buttons.
				rbDyn.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent ae) {
				System.out.println("Dyn");
				alg=1;
				}
				});
				rbGen.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent ae) {
					alg=2;
					System.out.println("Gen");
				}
				});
				rbKnn.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent ae) {
					System.out.println("knn");
					alg=3;
				}
				});
		startInput.setEditable(false);;
		 VBox rightLay= new VBox(start,save); 
		 VBox algLayout= new VBox(rbGen,rbKnn,rbDyn); 
		 algLayout.setAlignment(Pos.BASELINE_LEFT);
		super.setTop(toplayout);
		super.setRight(rightLay);
		super.setBottom(clear);
		
		SplitPane sp1=new SplitPane(startProgress,startResult);
		SplitPane sp=new SplitPane( startInput , sp1);
		sp.setOrientation(Orientation.VERTICAL);
		super.setCenter( sp );
		super.setLeft(algLayout);
		
		
		 
		openProject.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				FileChooser fc = new FileChooser();
				
				File file = fc.showOpenDialog(stage);
				//.setId("start");
				if (file != null)
					if (file.exists())
						if (file.isFile())
							readTSP(file.getAbsolutePath());
				//start.fire();
				
			}
			
		});
		
	start.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) { 
				if(mCities!=null)
				if(mCities.size()>2) {
					  
					startProgress.setText(startProgress.getText()+"started 3 Algorthm at"+ new Date()+"\n");
				 long d=	startAgl(getData(), alg);
					//Test.printTravelPrices(getData(), 3);
				startResult.setText( new Date()+"\n"+geFulltResult(d));
				startProgress.setText(startProgress.getText()+"ended 3 Algorthm at "+ new Date()+"\n");
					 
				}else {
					// less than a problem
					//notice(Window owner,String title,String warning,String solution)
					S.notice(stage, "Notice", "Number of cities are/is less than a problem", "Add more cities or load a new project");
				}
			}
			
		});
	clear.setOnAction(new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent event) { 
			 clear();
			 startInput.setText("");
		}
		
	});
		newCity.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				if(mCities==null)mCities = new ArrayList();
				addCity(stage, mCities);  
					startInput.setText(getPreview());
					 
					
				
			}
			
		});
		
	}
	
 


protected String geFulltResult(long d) {
	String res="Movement\n";
	int prevouse =mDirection.get(0);
	if(mDirection!=null)
	for( int x=1 ;x<mDirection.size();x++) {
		int i =mDirection.get(x);
		res+=mCities.get(prevouse)+"\t"; 
		res+="\t"+mMatrix[prevouse][i]+"\tto\t"; 
		prevouse =i;
	}
	 res+=mCities.get(prevouse)+"\t"; 
	res+="\n Total distances \t"+mCost;
	res+="\n Total time \t"+d;
	res+=mDirection.toString();
	return res;
	}


/*
 * Tue Oct 27 12:05:33 WAT 2020
Movement
A		0.0	to	C		7.0	to	B		3.0	to	A		3.0	to	A	
 Total distances 	13.0
 Total time 	2[0, 2, 1, 0]

	A	B	C
	0.0	3.0	7.0
	3.0	0.0	3.0
	7.0	3.0	0.0
 */

protected long startAgl(double[][] data,int alg) {
	long start=System.currentTimeMillis();

	mDirection = new ArrayList<Integer>();
	 mCost=0;
	long duration =0l;
	 long end;
		switch(alg) {
		case 1 :{ 
		  TspDynamicProgrammingIterative dyn = new TspDynamicProgrammingIterative(0,     data);
			   dyn.solve(); 
			    System.out.println(dyn.getTour().toString());
			          mDirection=dyn.getTour();
			         System.out.println(dyn.getTourCost());
			         mCost=dyn.getTourCost();
			         end =System.currentTimeMillis();
			         duration=end-start;
			         System.out.println(duration); 
			         System.out.println(); 
			break;
		}
		
		 
		case 2:{    
			
			int dataInt[][] = new int[mCities.size()][mCities.size()];
			dataInt =getDataInt(data);
			if(mCities.size()>20) {
			start=System.currentTimeMillis();
			Salesmensch geneticAlgorithm = new  Salesmensch(mCities.size(), SelectionType.ROULETTE,dataInt , 0, 0);
			         SalesmanGenome result = geneticAlgorithm.optimize();
			          end =System.currentTimeMillis();
			         System.out.println(result); 
			         mDirection.add( result.getStartingCity());
			         mDirection.addAll(result.getGenome());
			         mDirection.add( result.getStartingCity());
			        mCost = result.getFitness();
			         duration=end-start;
			         System.out.println(duration);  
		}else {
			S.notice(stage, "Notice", "Cities are too much for Dynamic algorithm", "Use another algorithm");
		}
			         
			break;
		}
		
		
		case 3:{ 
  
			 int dataInt[][] = new int[mCities.size()][mCities.size()];
			dataInt =getDataInt(data);
			start=System.currentTimeMillis();
			         TSPNearestNeighbour tsp = new TSPNearestNeighbour(); 
			         tsp.tsp(dataInt);
			         end =System.currentTimeMillis();
			         duration=end-start;
			         System.out.println(duration); 
			         
			break;
		}
		}
	return duration;	
	}


private int[][] getDataInt(double[][] data) {
	int [][] res= new int[mCities.size()][mCities.size()];
	 
	for(int i =0; i<mCities.size();i++) {
		for(int j =0; j<mCities.size();j++) {
			res[i][j]=  (int) data[i][j];
		} 
	}
	
	return res; 
}


protected void readTSP(String id) {
	// TODO Auto-generated method stub
	
}
	TextArea input ;
	protected void addCities(Window owner, final List<String> cities) {
		// Create a Stage with specified owner and modality
		Stage stage = new Stage();
		stage.initOwner(owner);
		stage.initModality(Modality.APPLICATION_MODAL); 
		
		VBox root = new VBox();
		BorderPane main = new BorderPane();
		Button add = new Button("add City");
		Button clear = new Button("clear input");
		Button save = new Button("save");

		Button finish = new Button("finish");
		 input = new TextArea();
		input.setEditable(true);
		input.setText(getPreview());
		main.setTop(save);
		main.setRight(new VBox(add,finish));
		main.setBottom(clear);
		main.setCenter(input);
		root.getChildren().add(main);
		Scene scene = new Scene(root, 700, 400);
		stage.setScene(scene);
		stage.setTitle("Add cities ");

		
		add.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// stage.hide();
				
				addCity(stage, cities);
				for (String c : cities) {
					System.out.println(c);
				}
			}
		});
finish.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});
		
		clear.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				input.setText("");
				clear();
			}
		});
		stage.showAndWait();;
		
	}
	
	protected void clear() {
		mCities.clear(); 
	}


	public boolean updateMatrix(int x, double data[]) {
		// mMatrix.length
				if (x < mMatrix.length) {
			for (int i = 0; i <= x; i++) {
				if (i == x) {
					mMatrix[i][x] = 0;
				} else {
					 				mMatrix[i][x] = data[i];
					mMatrix[x][i] = data[i];
				}
				
			}
		//	updatePreview();
			return true;
		}
		
		return false;
		
	}
	
	private void updatePreview() {
		String res="";
		for(String c: mCities) {
			res=res+"\t"+c  ;
		}
		res+="\n";
		for(int i =0; i<mCities.size();i++) {
			for(int j =0; j<mCities.size();j++) {
				res=res+"\t" + mMatrix[i][j];
			}
			res+="\n";
		}
		
		System.out.println(res);
		//input.setText(res);
		
	}
	
	private String getPreview() {
		String res="";
		for(String c: mCities) {
			res=res+"\t"+c  ;
		}
		res+="\n";
		for(int i =0; i<mCities.size();i++) 
		{
			res=res+ mCities.get(i);
			for(int j =0; j<mCities.size();j++) {
				res=res+"\t" + mMatrix[i][j];
			}
			res+="\n";
		}
		
		return res;
		
	}
	
	private double [][] getData() {
		double [][] res= new double[mCities.size()][mCities.size()];
		 
		for(int i =0; i<mCities.size();i++) {
			for(int j =0; j<mCities.size();j++) {
				res[i][j]=  mMatrix[i][j];
			} 
		}
		
		return res;
		
	}

	public void addCity(Window owner, List<String> cities) {
		Stage stage = new Stage();
		stage.initOwner(owner);
		stage.initModality(Modality.APPLICATION_MODAL);
		// get(0,"okay");
	final	ObservableList<CityDistance> citiesDisatance =  
		  FXCollections.observableArrayList();
		int i = 0;
		for (String cityName : cities) {
			citiesDisatance.add(new CityDistance(cityName, i));
			i++;
		}
		VBox root = new VBox();
		if (citiesDisatance != null)
			root.getChildren().addAll(citiesDisatance);
		
		BorderPane main = new BorderPane();
		main.setCenter(root);
		HBox topLayout = new HBox();
		
		Button go = new Button("go");
		 Label aNum = new Label("Empty name");
		 aNum.setTextFill(Color.RED);
		HBox bottomLayout = new HBox(go);
		TextField newCityName = new TextField();
		main.setBottom(bottomLayout);
		bottomLayout.setAlignment(Pos.BASELINE_RIGHT);
		Label l= new Label("New City  ");
		l.setMinWidth(150);
		topLayout.getChildren().addAll(l, newCityName,aNum);
		main.setTop(topLayout);
		
		Scene scene = new Scene(main, 400, 500);
		stage.setScene(scene);
		stage.setTitle("Add a city");
		
		
		
		newCityName.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent k) {
			 
				if(newCityName.getText().isEmpty()) {
					aNum.setTextFill(Color.RED);
					aNum.setText("Empty city name");
				}else {
				aNum.setTextFill(Color.GREEN);
				aNum.setText(" Okay");
				 
				}
			 
			
			}});
		
		go.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				if (!newCityName.getText().isEmpty()) { 
					boolean isOkay = true;
					/// updateMatrix();
					int i = 0; 
					if (citiesDisatance.size() >0) {
						double data[] = new double[citiesDisatance.size()];
						for (CityDistance c : citiesDisatance) {
							if (c.isOkay() != true) {
								isOkay = false;
								
							}
							data[i] = c.getDistance();
							i++;
						}
						if (isOkay) {
							mCities.add(newCityName.getText());
							updateMatrix(mCities.size() - 1, data);
							stage.close();
							 
						}else {
						System.out.println(" data not Okay"); 
						S.notice(stage, "Data input error ", " A Field empty or not a number", "Make sure  you all fields are numbers");
						}
					} else { 
						mCities.add(newCityName.getText());
						updateMatrix(mCities.size() - 1, null);
						stage.close();
					}
				}else {
					S.notice(stage, "Data input error ", "City name is empty ", "Make sure  city name not empty ");
				 
						System.out.println("empty city name");
					 
				}
				
			}
		});
		
		stage.showAndWait();	 
		
	}
	
	 
	
}