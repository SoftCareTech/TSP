package gui;
import java.util.*;

import algorithm.SalesmanGenome;
import algorithm.Salesmensch;
import algorithm.SelectionType;
import algorithm.TSPNearestNeighbour;
import algorithm.TspDynamicProgrammingIterative;

public class Test {
    public static void printTravelPrices(double[][] travelPrices, int numberOfCities){
        for(int i = 0; i<numberOfCities; i++){
            for(int j=0; j<numberOfCities; j++){
                System.out.print(travelPrices[i][j]);
                if(travelPrices[i][j]/10 == 0)
                    System.out.print("  ");
                else
                    System.out.print(' ');
            }
            System.out.println("");
        }
    }
    public static void printTravelPrices(int[][] travelPrices, int numberOfCities){
        for(int i = 0; i<numberOfCities; i++){
            for(int j=0; j<numberOfCities; j++){
                System.out.print(travelPrices[i][j]);
                if(travelPrices[i][j]/10 == 0)
                    System.out.print("  ");
                else
                    System.out.print(' ');
            }
            System.out.println("");
        }
    }
    public static void main(String[] args) {
        int numberOfCities = 5;  // dynamic algorithm  is accepting only size 21 and below because of the comple
        int[][] travelPrices = new int[numberOfCities][numberOfCities];
        System.out.println (numberOfCities);
        travelPrices[0][1]=4;
        travelPrices[0][2]=1;
        travelPrices[0][3]=2;
        travelPrices[0][4]=6;
        
        travelPrices[1][0]=4;
        travelPrices[1][2]=5;
        travelPrices[1][3]=5;
        travelPrices[1][4]=6;
        
        travelPrices[2][0]=1;
        travelPrices[2][1]=5;
        travelPrices[2][3]=4;
        travelPrices[2][4]=3;
        
        travelPrices[3][0]=2;
        travelPrices[3][1]=5;
        travelPrices[3][2]=4;
        travelPrices[3][4]=7;
        
        travelPrices[4][0]=6;
        travelPrices[4][1]=6;
        travelPrices[4][2]=3;
        travelPrices[4][3]=7;
        
       
        for(int i = 0; i<numberOfCities; i++){
            for(int j=0; j<=i; j++){
                Random rand = new Random();
                if(i==j)
                    travelPrices[i][j] = 0;
                 
                else {
                   // travelPrices[i][j] = rand.nextInt(100);
                    //travelPrices[j][i] = travelPrices[i][j];
                }  
            }
        }
 
        printTravelPrices(travelPrices,numberOfCities);
long start=System.currentTimeMillis();
long end;
System.out.println(" Gen problem");
         Salesmensch geneticAlgorithm = new  Salesmensch(numberOfCities, SelectionType.ROULETTE, travelPrices, 0, 0);
        SalesmanGenome result = geneticAlgorithm.optimize();
         end =System.currentTimeMillis();
        System.out.println(result);
        
        System.out.println(result.getGenome());
        System.out.println(result.getFitness());
        
        System.out.println((end-start));
        System.out.println(" DYN problem");
        /////TspDynamicProgrammingIterative
        double[][] travelPrices2= getDouble( travelPrices);
        start=System.currentTimeMillis();
          TspDynamicProgrammingIterative dyn = new TspDynamicProgrammingIterative(0,     travelPrices2);
        System.out.println();
        dyn.solve(); 
        System.out.println(dyn.getTour().toString());
        System.out.println(dyn.getTourCost());
        end =System.currentTimeMillis();
        System.out.println((end-start));
          
        System.out.println(" kNN problem");
        
        
        
        
        TSPNearestNeighbour tsp = new TSPNearestNeighbour();
        
        tsp.tsp(travelPrices);
        
        
        
        
        
    }

	private static double[][] getDouble(int[][] travelPrices) {
		int size =  travelPrices.length;
		 
		  double[][] travelPrices2= new  double [size][size];
		  for(int i=0; i<size;i++) {
			  for(int j=0;j<size;j++) {
				  travelPrices2[i][j]=travelPrices[i][j];
			  }
		  }
		return  travelPrices2;
	}
}

