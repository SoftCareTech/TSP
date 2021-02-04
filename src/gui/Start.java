package gui;

 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import algorithm.SalesmanGenome;
import algorithm.Salesmensch;
import algorithm.SelectionType;
import algorithm.TSPNearestNeighbour;
import algorithm.TspDynamicProgrammingIterative;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.converter.DoubleStringConverter;
import util.PointXY;
import util.ProgressPopUp;
import util.S;

public class Start extends BorderPane {
	public List<String> mCities;
	public List<Integer> mDirection;
	int defaultSize = 1024;
	private double mMatrix[][] = new double[defaultSize][defaultSize];
	private double mCost;
	private int alg = 2;// genetic first
	private Stage stage;
	private int waitTime=500;
	private String algName;
	private TextArea startInput;
	
	private TextArea startInputXY;
	
	public Start(Stage stage) {
		super();
		// TODO Auto-generated constructor stub
		this.stage = stage;
		  
		Button newCity = new Button("Add new city using  distance");
		Button newCityXY = new Button("Add new city using   location ");
		 
		
		// Create some menus
		Menu fileMenu = new Menu("File");
		 
		MenuItem exit= new MenuItem("Exit");
		exit.setOnAction(e -> Platform.exit());
		MenuItem openXY = new MenuItem("Open TSP  Location");
		MenuItem open = new MenuItem("Open TSP  Distance"); 
		MenuItem save = new MenuItem("save "); 
		fileMenu.getItems().addAll(openXY,open,save,exit);
		save.setOnAction(e -> Platform.exit());
		openXY.setOnAction(e -> Platform.exit());
		open.setOnAction(e -> Platform.exit());
		// Add menus to a menu bar
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu);
		BorderPane root = new BorderPane();
		HBox topLayout = new HBox(menuBar,newCity, newCityXY);
		
		Button clear = new Button("clear input"); 
		Button start = new Button("start");
		Button stop = new Button("stop");
		startInput = new TextArea();
		startInputXY = new TextArea();
		TextArea startResult = new TextArea();
		TextArea startProgress = new TextArea();
		startResult.setWrapText(true);
		startProgress.setWrapText(true);
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
				alg = 1;
				algName = "Dynamic Algorithm";
			}
		});
		rbGen.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				alg = 2;
				System.out.println("Gen");
				algName = "Genetic Algorithm";
			}
		});
		rbKnn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				System.out.println("knn");
				alg = 3;
				algName = "KNN Algorithm";
			}
		});
		startInput.setEditable(false);
		;
		startInputXY.setEditable(false);
		;
		VBox rightLay = new VBox(start);
		VBox algLayout = new VBox(rbGen, rbKnn, rbDyn);
		algLayout.setAlignment(Pos.BASELINE_LEFT);
		
		super.setTop(topLayout);
		super.setRight(rightLay);
		super.setBottom(clear);
		startInputXY.setPrefHeight(700);
		startInput.setPrefHeight(700);
		startProgress.setPrefHeight(700);
		startResult.setPrefHeight(700);
		SplitPane sp1 = new SplitPane(new VBox(new Label("Progress"), startProgress),
				new VBox(new Label("Result"), startResult));
		SplitPane sp = new SplitPane(new VBox(new Label("Ajacent Matrix"), startInput), sp1);
		sp.setOrientation(Orientation.VERTICAL);
		super.setCenter(new SplitPane(sp, new VBox(new Label("Location"), startInputXY)));
		super.setLeft(algLayout);
		
		open.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				FileChooser fc = new FileChooser();
				
				File file =  fc.showOpenDialog(stage); 
				if (file == null) {
					S.notice(stage, "Error", "File not seleted", " select a file that exist");
				}else
					if (!file.exists()) {
						S.notice(stage, "Error", "File not seleted", " select a file that exist");
					}else
						if (file.isFile()) {
							
							if (mCities == null)
								mCities = new ArrayList();
							Runnable task = () -> {
								try {
									
									String tsp = readFile(file);
									if (ps != null)
										ps.pro(0.5, "Setting result ");
									if (readTSP(tsp)) {
										setLocation();
										String pv = getPreview();
										String pv2 = getPreviewXY();
										Platform.runLater(() ->
										startInput.setText(pv));
										startInputXY.setText(pv2);
									} else {
										System.out.print(mCities.size());
										Platform.runLater(
												() -> S.notice(stage, "Error", "File type or struct not match",
														"use the interface to add cities or read file format"));
										clear();
									}
									ps.prepareStop();
									;
									
									try {
										Thread.sleep(waitTime);
										
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									ps.stop();
									;
								} catch (IOException e) {
									e.printStackTrace();
									Platform.runLater(() -> S.notice(stage, "Error", "IOException", e.getMessage()));
									
								}
							};
							// Run the task in a background thread
							backgroundThread = new Thread(task);
							
							ps = new ProgressPopUp(backgroundThread);
							ps.notice(stage, "Loading TSP");
							// Terminate the running thread if the application exits
							backgroundThread.setDaemon(true);
							backgroundThread.start();
							
						}
					
				// start.fire();
				
			}
			
		});
		
	 
		 
		
		openXY.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				FileChooser fc = new FileChooser();
				
				File file = 
						//new File("C:\\Users\\GBENGE AONDOAKULA\\Downloads\\csv\\csv\\ulysses22.csv");
                fc.showOpenDialog(stage); 
				if (file == null) {
					S.notice(stage, "Error", "File not seleted", " select a file that exist");
				}else
					if (!file.exists()) {
						S.notice(stage, "Error", "File not seleted", " select a file that exist");
					}else
						if (file.isFile()) {
							
							if (mCities == null) {
								mCities = new ArrayList();
							}
							if (pointXY == null) {
								pointXY = new ArrayList();
							}
							Runnable task = () -> {
								try {
									String tsp = readFile(file);
									if (ps != null)
										ps.pro(0.5, "Setting result ");
									
									if (readTSP_XY(tsp)) {
										countDistancesAndUpdateMatrix();
										String pv = getPreview();
										String pvXY = getPreviewXY();
										Platform.runLater(() -> {
											
											startInputXY.setText(pvXY);
											System.out.println(" size: " + mCities.size());
											startInput.setText(pv);
										});
									} else {
										System.out.print(mCities.size());
										Platform.runLater(
												() -> S.notice(stage, "Error", "File type or struct not match",
														"use the interface to add cities or read file format"));
										clear();
									}
									ps.prepareStop();
									;
									
									try {
										Thread.sleep(waitTime);
										
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									ps.stop();
									;
								} catch (IOException e) {
									e.printStackTrace();
									Platform.runLater(() -> S.notice(stage, "Error", "IOException", e.getMessage()));
									
								}
							};
							// Run the task in a background thread
							backgroundThread = new Thread(task);
							
							ps = new ProgressPopUp(backgroundThread);
							ps.notice(stage, "Loading TSP Location");
							// Terminate the running thread if the application exits
							backgroundThread.setDaemon(true);
							backgroundThread.start();
							
						}
					
				// start.fire();
				
			}
			
		});
		
		save.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				if (mCities != null) {
					if ((mCities.size() < 1)) {
						S.notice(stage, "Notice", "Number of city  is less than one",
								"Add at leat one city to save project");
						return;
					}
					
				} else {
					S.notice(stage, "Notice", "Number of city  is less than one",
							"Add at leat one city to save project");
					return;
				}
				 Save save= new Save();
				 save.save(stage, "choose type", startInputXY.getText().toString(),
						 startInput.getText().toString() );
			
			}});
		
		start.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (mCities != null)
					if (mCities.size() > 2) {
						
						startProgress.setText(startProgress.getText() + "Started 3 Algorthm at " + new Date() + "\n");
				 	//start 
						
					 //
					 	Runnable task = () -> { 
								long d = startAgl(getData(), alg);  
								System.out.print(" Time used ");
								System.out.println(d);
								
								Platform.runLater(() -> { 
									startResult.setText(new Date() +
											"\n" + geFulltResult(d, alg));
									startProgress.setText(startProgress.getText() +
											algName + new Date() + "\n");
								});
								 
						if(ps!=null) {
							ps.prepareStop();
							ps.stop();
						}
				 		
						 };
						// Run the task in a background thread
						backgroundThread = new Thread(task);
						
						ps = new ProgressPopUp(backgroundThread);
						ps.notice(stage, "Algorithm running");
						ps.pro(0, "Algorithm running");
						// Terminate the running thread if the application exits
						backgroundThread.setDaemon(true);
						backgroundThread.start();
						
						
						
					} else {
						
						S.notice(stage, "Notice", "Number of cities are/is less than a problem",
								"Add more cities or load a new project");
					}
				else
					S.notice(stage, "Notice", "Number of cities are/is less than a problem",
							"Add more cities or load a new project");
			}
			
		});
		clear.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				clear();
				startInput.setText("");
				startInputXY.setText("");
			}
			
		});
		newCity.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				if (mCities == null) {
					pointXY = new ArrayList();
					mCities = new ArrayList();
				}
				addCity(stage, mCities);
				System.out.println("  fiesr");
				startInput.setText(getPreview()); 
				startInputXY.setText(getPreviewXY());
				
			}
			
		});
newCityXY.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				if (mCities == null) {
					pointXY = new ArrayList();
					mCities = new ArrayList();
				}
				addCityXY(stage, mCities);
				startInputXY.setText(getPreviewXY());
				startInput.setText(getPreview());
				
			}
			
		});
		
	}
	String header="";
	protected boolean readTSP_XY(String tsp) {
		mCities.clear();
		header="";
		if (tsp != null) {
			String lines[] = tsp.split("\n");
			
			List<PointXY> p = new ArrayList<>();
			  int pp=1;
			for (String li:lines) {
				header+=li+"\n";
				if(li.trim().contains("NODE_COORD_SECTION")) {
					 break;
				}
				
				pp++;
			} 
			 
				for (int i = pp; i < lines.length - 1; i++) {
					 System.out.println(" Result "+lines[i]);
					String[] name_X_Y = lines[i].trim().split("\\s+");
					  System.out.println(" Debug size "+name_X_Y.length);
					if (name_X_Y.length == 3) {
						mCities.add(name_X_Y[0]);
						try {
							double x = Double.parseDouble(name_X_Y[1]);
							double y = Double.parseDouble(name_X_Y[2]);
							p.add(new PointXY(x, y));
						} catch (Exception e) {
							return false;
						}
					} else {
						for (String s : name_X_Y) {
							System.out.println(" ERR Debug data " + s);
						}
						
						return false;
					}
					if (ps != null) {
						ps.pro(0.5 + ((i / lines.length) / 2), " setting " + i + " of " + lines.length);
						if (!ps.back) {
							return false;
						}
					}
				}
				System.out.println(" read ts  " + (lines.length - 7));
				pointXY.addAll(p);
				return true;
			 
		}
		return false;
	}
	
	protected String getPreviewXY() {
		String res = " ";
		if (pointXY != null) {
			
			res=header;
			for (int i = 0; i < mCities.size(); i++) {
				res = res + mCities.get(i).replace(" ", "_") +
						"    " + pointXY.get(i).getX() + " "
						+ pointXY.get(i).getY();
				res += "\n";
			}
			
		}
		
		return res + "EOF";
	}
	
	private Thread backgroundThread;
	ProgressPopUp ps;
	
	protected String geFulltResult(long d, int alg) {
		double cost=0;
		if (mDirection.size() < 2) {
			return "Action have no defined output yet...";
		} 
		String res = "Movement\n";
		res += "\tPath: ";
		double  dist[] = new double[mDirection.size()];
		int prevouse = mDirection.get(0);
		if (mDirection != null)
			for (int x = 1; x < mDirection.size(); x++) {
				int i = mDirection.get(x);
				res += mCities.get(prevouse) + "\t";
				res += "\t" + mMatrix[prevouse][i] + "\tto\t";
				
				dist[prevouse]=mMatrix[prevouse][i];
				 cost =cost+mMatrix[prevouse][i];
				prevouse = i;
			}
		
		res += mCities.get(prevouse) + "\t";
		if(mCost==0) {
			mCost=cost;
		}
		res += "\n Total distances \t" + mCost;
		if(d>0L) {
		double time = (double)d/(double)1000;
		if(time!=1)
		res += "\n Total time in seconds\t" + time+" seconds";
		else
			res += "\n Total time  in seconds\t" + time+" second";
		
		}else {
			res += "\n Total time in seconds\t" + " is zero seconds";
		}
		//res += "\n Total time \t" + d;
		res += "\n\n  Path: ";
		prevouse = mDirection.get(0);
		if (mDirection != null)
			for (int x = 0; x < mDirection.size(); x++) {
				int i = mDirection.get(x);
				res += mCities.get(i) + "\t";
			}
		//res += "\n Total time in millsec\t" + d+"  ";
		//res += "\n  Path: " + mDirection.toString();
		//Plot(List<PointXY>pointXY, List<String> cities,	List<Integer> path, 
		//<Double[]distance )
		Plot p= new Plot(pointXY,mCities,mDirection,dist);
		
		
		switch (alg) {
		case 1: {
			
			p.plot(stage, "Result  Dnynamic  Algorithm ", res);
			
			break;
		}
		
		case 2: { 
			p.plot(stage, "Result Genetic Algorithm  ", res);
			 
			break;
		}
		
		case 3: { 
			p.plot(stage, "Result TSP Nearest Neighbour ", res);  
			break;
		}}
		
		System.out.println("COST + "+cost);
		return res;
		
	}
	
	/*
	 * Tue Oct 27 12:05:33 WAT 2020 Movement A 0.0 to C 7.0 to B 3.0 to A 3.0 to A
	 * Total distances 13.0 Total time 2[0, 2, 1, 0]
	 * 
	 * A B C 0.0 3.0 7.0 3.0 0.0 3.0 7.0 3.0 0.0
	 */
	
	protected long startAgl(double[][] data, int alg) {
		long start = System.currentTimeMillis();
		
		mDirection = new ArrayList<Integer>();
		mCost = 0;
		long duration = 0l;
		long end;
		switch (alg) {
		case 1: {
			
			if (mCities.size() <= 20) {
				TspDynamicProgrammingIterative dyn = new TspDynamicProgrammingIterative(0, data);
				dyn.solve();
				System.out.println(dyn.getTour().toString());
				mDirection = dyn.getTour();
				System.out.println(dyn.getTourCost());
				mCost = dyn.getTourCost();
				end = System.currentTimeMillis();
				duration = end - start;
				System.out.println(duration);
				System.out.println();
			} else {
				System.out.println("error size is " + mCities.size());
				S.notice(stage, "Notice", "Cities are too much for Dynamic algorithm", "Use another algorithm");
			}
			
			break;
		}
		
		case 2: {
			
			int dataInt[][] = new int[mCities.size()][mCities.size()];
			dataInt = getDataInt(data);
			start = System.currentTimeMillis();
			Salesmensch geneticAlgorithm = new Salesmensch(mCities.size(), SelectionType.ROULETTE, dataInt, 0, 0);
			SalesmanGenome result = geneticAlgorithm.optimize();
			end = System.currentTimeMillis();
			System.out.println(result);
			mDirection.add(result.getStartingCity());
			mDirection.addAll(result.getGenome());
			mDirection.add(result.getStartingCity());
			mCost = result.getFitness();
			duration = end - start;
			System.out.println(duration);
			
			break;
		}
		
		case 3: {
			
			int dataInt[][] = new int[mCities.size()][mCities.size()];
			dataInt = getDataInt(data);
			start = System.currentTimeMillis();
			TSPNearestNeighbour tsp = new TSPNearestNeighbour();
			tsp.tsp(dataInt);
			/*
			 System.out.println(dyn.getTour().toString());
				mDirection = dyn.getTour();
				System.out.println(dyn.getTourCost());
				mCost = dyn.getTourCost();
			 */
			System.out.println();
			System.out.println("  cosst +>> "+ String.valueOf(tsp.getCost())); 
			System.out.println("  direction +>> "+ String.valueOf(tsp.getTour())); end = System.currentTimeMillis();
			System.out.println();
			mDirection = tsp.getTour();
			mCost =tsp.getCost();
			duration = end - start;
			System.out.println(duration);
			
			break;
		}
		}
		return duration;
	}
	
	private int[][] getDataInt(double[][] data) {
		int[][] res = new int[mCities.size()][mCities.size()];
		
		for (int i = 0; i < mCities.size(); i++) {
			for (int j = 0; j < mCities.size(); j++) {
				res[i][j] = (int) data[i][j];
			}
		}
		
		return res;
	}
	
	public String readFile(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		String res = "";
		BufferedReader buff = new BufferedReader(new FileReader(file));
		String readLine = "";
		if (ps != null)
			ps.pro(0, "Reading file");
		int l = 0;
		while ((readLine = buff.readLine()) != null) {
			res += readLine + "\n";
			l++;
			if (ps != null)
				ps.pro(l, "Reading file...  " + l + " lines");
		}
		
		return res;
	}
	
	public boolean readTSP(String data) {
		 System.out.println(data);
		
		 String resH ="";
		
		mCities.clear();
		String seperator = "\\s+";
		String lines[] = data.split("\n");
		boolean res = true;
		clear(); 
		 String name = "TSP" + (lines.length-1);
			String comment = "Faith TSP solution ";
			String type = "TSP";
			String edgeType = "EUC_2D";
			resH = name + "\n";
			resH += "TYPE: " + type + "\n";
			resH += "COMMENT: " + comment + "\n";
			resH += "DIMENSION: " +  (lines.length-1) + "\n";
			resH += "EDGE_WEIGHT_TYPE: " + edgeType + "\n";
			resH += "NODE_COORD_SECTION\n"; 
			header=resH;
			
		
		if (lines != null) { 
			int x = -1, y=-1;
			int k = 0; 
			for (String line : lines) {
				String columns[] = line.trim().split(seperator);
				if (ps != null) { ps.pro((((double)x / (double)lines.length) /(double) 2), " setting " + k + " of " + lines.length); }
				System.out.println(y+" PROCESSING LINE "+line  );
				if(y==-1) {y++;continue;}
				x=-1;
				if (columns != null&columns.length==lines.length ) { 
						for (String column : columns) {
							if (x < y) { if (x != -1) {
								DoubleStringConverter c = new DoubleStringConverter();
								mMatrix[x][y] = c.fromString(column.trim().replace(" ", ""));
								mMatrix[y][x] =mMatrix[x][y];
								 System.out.print("  |" +column+ "|  ==> "+mMatrix[x][y]
                                 				+" position "+x+" , "+y );
								x++; 
							} else { mCities.add(column); x=0; }
						}else {  
							break; } 
					} 
				} else {
					System.out.println(" col null or miss match lenght");
					System.out.println(" col    lenght "+columns.length+
							"  lines " +lines.length);
					
					for (String column : columns) {
						System.out.println(" col    data "+column );
					}
					
					res = false;
					break;
				}
				System.out.println(  );
				System.out.print(y+" LinE "  );
				System.out.println(  );
				y++;
			}

			 
			
		}
		 System.out.println(getPreview());
		
		return res;
		
	}
	
	TextArea input;
	
	protected void clear() {
		if (mCities != null)
			mCities.clear();
		if (pointXY != null)
			pointXY.clear();
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
			// updatePreview();
			return true;
		}
		
		return false;
		
	}
	
	private void updatePreview() {
		String res = "";
		for (String c : mCities) {
			res = res + "\t" + c;
		}
		res += "\n";
		for (int i = 0; i < mCities.size(); i++) {
			for (int j = 0; j < mCities.size(); j++) {
				res = res + "\t" + mMatrix[i][j];
			}
			res += "\n";
		}
		
		System.out.println(res);
		// input.setText(res);
		
	}
	
	private String getPreview() {
		String res = "";
		for (String c : mCities) {
			res = res + "\t" + c;
		}
		res += "\n";
		for (int i = 0; i < mCities.size(); i++) {
			res = res + mCities.get(i);
			for (int j = 0; j < mCities.size(); j++) {
				res = res + "\t" + S.doubleToString(mMatrix[i][j]);
			}
			if (ps != null)
				ps.pro((double) ((double) i / (double) mCities.size()),
						"Reading ajacent matrix " + i + " rows of " + mCities.size() + " rows");
			res += "\n";
			
		}
		
		return res;
		
	}
	
	private double[][] getData() {
		double[][] res = new double[mCities.size()][mCities.size()];
		
		for (int i = 0; i < mCities.size(); i++) {
			for (int j = 0; j < mCities.size(); j++) {
				res[i][j] = mMatrix[i][j];
			}
		}
		
		return res;
		
	}
	
	private String getSave() {
		String res = "";
		
		for (int i = 0; i < mCities.size(); i++) {
			res += mCities.get(i) + ",";
			res += "\n";
		}
		for (int i = 0; i < mCities.size(); i++) {
			res += mCities.get(i) + ",";
			for (int j = 0; j < mCities.size(); j++) {
				res += mMatrix[i][j] + ",";
			}
			res += "\n";
		}
		
		return res;
		
	}
	
	private String getSave_temp() {
		String res = "";
		
		for (int i = 0; i < mCities.size(); i++) {
			res += mCities.get(i) + ",";
			for (int j = 0; j < i; j++) {
				res += mMatrix[i][j] + ",";
			}
			res += "\n";
		}
		
		return res;
		
	}
	
	public void addCity(Window owner, List<String> cities) {
		Stage stage = new Stage();
		stage.initOwner(owner);
		stage.initModality(Modality.APPLICATION_MODAL);
		// get(0,"okay");
		final ObservableList<CityDistance> citiesDisatance = FXCollections.observableArrayList();
		int i = 0;
		for (String cityName : cities) {
			citiesDisatance.add(new CityDistance(cityName, i));
			i++;
		}
		VBox root = new VBox();
		if (citiesDisatance != null)
			root.getChildren().addAll(citiesDisatance);
		
		BorderPane main = new BorderPane();
		main.setCenter( new ScrollPane(root));
		HBox topLayout = new HBox();
		
		Button go = new Button("Add city");
		Label aNum = new Label("Empty name");
		aNum.setTextFill(Color.RED);
		HBox bottomLayout = new HBox(go);
		TextField newCityName = new TextField();
		main.setBottom(bottomLayout);
		bottomLayout.setAlignment(Pos.BASELINE_RIGHT);
		Label l = new Label("New City  ");
		l.setMinWidth(150);
		topLayout.getChildren().addAll(l, newCityName, aNum);
		main.setTop(topLayout);
		
		Scene scene = new Scene(main, 400, 400);
		
		scene.getStylesheets().add(S.PATH_CITY);
		stage.setScene(scene);
		stage.setTitle("Add a city");
		
		newCityName.setOnKeyReleased(new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent k) {
				
				if (newCityName.getText().isEmpty()) {
					aNum.setTextFill(Color.RED);
					aNum.setText("Empty city name");
				} else {
					aNum.setTextFill(Color.GREEN);
					aNum.setText(" Okay");
					
				}
				
			}
		});
		
		go.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				if (!newCityName.getText().isEmpty()) {
					boolean isOkay = true;
					/// updateMatrix();
					int i = 0;
					if (citiesDisatance.size() > 0) {
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
							 setLocation(mCities.size() - 1);
							stage.close();
							
						} else {
							System.out.println(" data not Okay");
							S.notice(stage, "Data input error ", " A Field empty or not a number",
									"Make sure  you all fields are numbers");
						}
					} else {
						mCities.add(newCityName.getText());
						updateMatrix(mCities.size() - 1, null); 
						 setLocation(mCities.size() - 1);
						stage.close();
					}
				} else {
					S.notice(stage, "Data input error ", "City name is empty ", "Make sure  city name not empty ");
					
					System.out.println("empty city name");
					
				}
				
			}
		});
		
		stage.showAndWait();
		
	}
	
	
	
	List<PointXY> pointXY;
	
	protected void addCityXY(Window owner, final List<String> cities) {
		// Create a Stage with specified owner and modality
		Stage stage = new Stage();
		stage.initOwner(owner);
		stage.initModality(Modality.APPLICATION_MODAL);
		
		VBox root = new VBox();
		BorderPane main = new BorderPane();
		Button add = new Button("add City");
		Button clear = new Button("clear input");
		Button cancel = new Button("Cancel");
		
		CityXY control = new CityXY();
		control.setStyle("\"-fx-padding: 10;\" +\r\n" + "\"-fx-border-style: solid inside;\" +\r\n"
				+ "\"-fx-border-width: 2;\" +\r\n" + "\"-fx-border-insets: 5;\" +\r\n"
				+ "\"-fx-border-radius: 5;\" +\r\n" + "\"-fx-border-color: blue;\"");
		main.setRight(new VBox(cancel));
		main.setBottom(new HBox(clear, add));
		main.setCenter(control);
		root.getChildren().add(main);
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setTitle("Add city");
		
		add.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				if (control.isOkay()) {
					pointXY.add(new PointXY(control.getX(), control.getY()));
					cities.add(control.getName());
					countDistancesAndUpdateMatrix();
					stage.close();
				} else {
					S.notice(stage, "Error", "Wrong inputs", control.msg.getText());
					
				}
			}
		});
		clear.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				control.clear();
			}
		});
		
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});
		stage.showAndWait();
		;
		
	}
	
	void countDistancesAndUpdateMatrix() {
		int size = pointXY.size();
		if (size > defaultSize) {
			clear();
			mMatrix = new double[size][size];
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				mMatrix[i][j] = distance(pointXY.get(i), pointXY.get(j));
			}
		}
	}
	
	void setLocation() {
		
		if (mCities != null) {
			int size = mCities.size();
			{
				pointXY = new ArrayList<>();
				pointXY.add(new PointXY(0, 0));
				pointXY.add(new PointXY(0, mMatrix[0][1]));
				for (int i = 2; i < size; i++) {
					pointXY.add(Test.getPoint(pointXY.get(0), pointXY.get(1), mMatrix[0][i], mMatrix[1][i]));
				}
			}
		}
		/*
		 * 
		if (mCities != null) {
			int size = mCities.size();
			
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					mMatrix[i][j] = distance(pointXY.get(i), pointXY.get(j));
				}
			}
		}
		 */
	}
	
void setLocation( int in) { 
	if(pointXY==null)
	pointXY = new ArrayList<>();
		if (mCities != null) {
			int size = mCities.size(); 
			switch(in){
			case 0:{
				pointXY.add(in,new PointXY(0, 0)); 
				return;
				
			}
			case 1:{
				pointXY.add(in,new PointXY(0, mMatrix[0][1]));
				return;
				
			}
			default:{
				pointXY.add(in,Test.getPoint(pointXY.get(0), pointXY.get(1), mMatrix[0][in], mMatrix[1][in]));
			}
			}
			{
				
				
				
				 
			}
		}
		/*
		 if (mCities != null) {
			int size = mCities.size();
			
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					mMatrix[i][j] = distance(pointXY.get(i), pointXY.get(j));
				}
			}
		}
		 */
	}
	private double distance(PointXY p1, PointXY p2) {
		return euclidean(p1.x - p2.x, p1.y - p2.y);
	}
	
	private double euclidean(double dx, double dy) {
		return Math.sqrt(dx * dx + dy * dy);
	}
	
}

/*
 * residues protected void addCities(Window owner, final List<String> cities) {
 * // Create a Stage with specified owner and modality Stage stage = new
 * Stage(); stage.initOwner(owner);
 * stage.initModality(Modality.APPLICATION_MODAL);
 * 
 * VBox root = new VBox(); BorderPane main = new BorderPane(); Button add = new
 * Button("add City"); Button clear = new Button("clear input"); Button save =
 * new Button("save");
 * 
 * Button finish = new Button("finish"); input = new TextArea();
 * input.setEditable(true); input.setText(getPreview()); main.setTop(save);
 * main.setRight(new VBox(add,finish)); main.setBottom(clear);
 * main.setCenter(input); root.getChildren().add(main); Scene scene = new
 * Scene(root, 700, 400); stage.setScene(scene); stage.setTitle("Add cities ");
 * 
 * 
 * add.setOnAction(new EventHandler<ActionEvent>() {
 * 
 * @Override public void handle(ActionEvent event) { // stage.hide();
 * 
 * addCity(stage, cities); for (String c : cities) { System.out.println(c); } }
 * }); finish.setOnAction(new EventHandler<ActionEvent>() {
 * 
 * @Override public void handle(ActionEvent event) { stage.close(); } });
 * 
 * clear.setOnAction(new EventHandler<ActionEvent>() {
 * 
 * @Override public void handle(ActionEvent event) { input.setText(""); clear();
 * } }); stage.showAndWait();;
 * 
 * }
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * public boolean readTSP(String data) {
		// System.out.println(data);
		
		mCities.clear();
		String seperator = "\\s+";
		String lines[] = data.split("\n");
		boolean res = true;
		clear();
		
		;
		if (lines != null) {
			int size = lines.length; 
			int previousSize = 0;
			int x = 0, y;
			int k = 0;
			;
			for (String line : lines) {
				String columns[] = line.trim().split(seperator);
				if (ps != null) {
					ps.pro((((double)k / (double)lines.length) /(double) 2), " setting " + k + " of " + lines.length);
					 
				}
				y = -1;
				if (columns != null) {
					if (previousSize + 1 == columns.length) {
						for (String column : columns) {
							if (y != -1) {
								DoubleStringConverter c = new DoubleStringConverter();
								mMatrix[x][y] = c.fromString(column.trim().replace(" ", ""));
								mMatrix[y][x] = mMatrix[x][y];
								y++;
							} else {
								mCities.add(column);
								y = 0;
							}
						}
						previousSize = columns.length;
					} else {
						System.out.println("hi 1");
						res = false;
						break;
					}
				} else {
					System.out.println("hi 2");
					res = false;
					break;
				}
				x++;
			}
			
		}
		// System.out.println(M);
		
		return res;
		
	}
	
 * 
 * 
 */