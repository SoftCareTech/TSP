package algorithm;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
 
public class TSPNearestNeighbour
{
    private int numberOfNodes;
    private Stack<Integer> stack;
    private double cost=0;
    List<Integer> 	 direction =new ArrayList<Integer>();
    public TSPNearestNeighbour()
    {
        stack = new Stack<Integer>();
    }
 public List<Integer> getTour(){  
	return direction;
 }
 public double getCost() {
	 return this.cost;
 }
    public void tsp(int adjacencyMatrix[][])
    {
        numberOfNodes = adjacencyMatrix[1].length - 1;
        int[] visited = new int[numberOfNodes + 1];
        visited[1] = 1;
        stack.push(1);
        int element, dst = 0, i;
        int min = Integer.MAX_VALUE;
        boolean minFlag = false;
        System.out.print(1 + "\t");
        direction.add(1);
        while (!stack.isEmpty())
        {
            element = stack.peek();
            i = 1;
            min = Integer.MAX_VALUE;
            while (i <= numberOfNodes)
            {
                if (adjacencyMatrix[element][i] > 1 && visited[i] == 0)
                {
                    if (min > adjacencyMatrix[element][i])
                    {
                        min = adjacencyMatrix[element][i];
                        dst = i;
                        minFlag = true;
                    }
                }
                i++;
            }
            if (minFlag)
            {
                visited[dst] = 1;
                stack.push(dst);
                System.out.print(dst + "\t");
                direction.add(dst);
                //System.out.println(adjacencyMatrix[dst]);
                minFlag = false;
                continue;
            }
            stack.pop();
        }
        System.out.println(" Test direction "+visited.toString());
        direction.add(0);
         direction.add(0, 0);// add the starting as  0 back to zero
        for(Integer in:direction) { 
            System.out.println(" Test direction "+in.intValue());
        }
    }
 
   
}