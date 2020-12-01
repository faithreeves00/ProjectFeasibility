// File Name: FaithsGraph.java
// Author: Faith Reeves
// Program Purpose: Weighted directed graph class that will allow the user to 
//                  create a project, calculate stage and activity times,
//                  sort stages topologically, and find critical activities

package canitbedone;

import java.util.ArrayList;

public class FaithsGraph {

    // declare datafields for number of nodes and the adjacency matrix
    int nodes;
    public int[][] adjMatrix;

    // no arg constructor for FaithsGraph that takes in an int for size
    FaithsGraph(int size) {

        // set numNodes to given size
        nodes = size;

        // make adjacency matrix the given size
        adjMatrix = new int[nodes][nodes];
    }

    // method to set each cell of the adjacency matrix to -1
    public void setAllNull() {

        // iterate through each cell of the matrix
        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {

                // set cell to -1
                adjMatrix[i][j] = -1;
            }
        }
    }

    // method to add a new edge to the graph
    public void addEdge(int source, int destination, int cost) {

        // set matrix cells to the given cost
        adjMatrix[source][destination] = cost;
    }

    // method to display the adjacency matrix
    public void printGraph() {

        // iterate through each cell of the matrix
        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {

                // print the element
                System.out.print(adjMatrix[i][j] + " ");
            }

            // print a blank line to start the next row
            System.out.println();
        }
    }

    // method that performs topological sort and returns the indexes of the 
    // nodes in topological order
    public int[] topologicalSort() {

        // create var for size of matrix
        int n = adjMatrix.length;

        // make a boolean 2D array to track which nodes have been visited
        boolean[] visited = new boolean[n];

        // create an order array
        int[] order = new int[n];

        // create var for index
        int index = n - 1;

        // visit each node
        for (int u = 0; u < n; u++) {
            if (!visited[u]) {
                index = visit(adjMatrix, visited, order, index, u);
            }
        }

        // return topological sort
        return order;
    }
    
    // method to visit a node
    private static int visit(int[][] adj, boolean[] visited, int[] order, int index, int u) {

        // if it has been visited, return the index
        if (visited[u]) {
            return index;
        }

        // set the node to visited
        visited[u] = true;

        // Visit all neighbors  
        for (int v = 0; v < adj.length; v++) {
            if (adj[u][v] != -1) {
                if (!visited[v]) {
                    index = visit(adj, visited, order, index, v);
                }
            }
        }

        // Place this node at the head of the list
        order[index--] = u;

        // return the index
        return index;
    }
    
    // method to find early stage time
    public int[] EST(int stage, int[] EST) {
        
        // set a max variable to 0
        int max = 0;
        
        // iterate through for each node
        for (int i = 0; i < nodes; i++) {
            
            // if there is a stage with a connection to the current stage,
            // execute if statement
            if (adjMatrix[i][stage] != -1) {
                
                // if the EST of pre-req stages plus the cost of the connecting 
                // activity is greater than max, execute if statement
                if ((adjMatrix[i][stage] + EST[i]) > max) {
                    
                    // set max variable to the newest EST
                    max = (adjMatrix[i][stage] + EST[i]);
                }
            }
        }
        
        // add the stage's EST (max value) to the EST array
        EST[stage] = max;
        
        // return the EST array
        return EST;     
    }
    
    // method to find late stage time
    public int[] LST(int stage, int[] LST) {
        
        // set min variable to positive infinity
        float min = Float.POSITIVE_INFINITY;
        
        // boolean to signal whether min needs to be set to zero (occurs if no
        // other value is found)
        boolean setToZero = true;
        
        // iterate through for each node
        for (int i = 0; i < nodes; i++) {
            
            // if there is a stage with a connection to the current stage,
            // execute if statement
            if (adjMatrix[stage][i] != -1) {
                
                // if the EST of pre-req stages plus the cost of the connecting 
                // activity is greater than min, execute if statement
                if ((LST[i] - adjMatrix[stage][i]) < min) {
                    
                    // set max variable to the newest EST
                    min = (LST[i] - adjMatrix[stage][i]);
                    
                    // signal that min does not need to be changed to zero
                    setToZero = false;
                }
            }
        }
        
        // if the boolean was not changed to false, execute if statement
        if (setToZero) {
            
            // set min to 0
            min = 0;
        }
        
        // add the stage's EST (max value) to the EST array
        LST[stage] = (int)min;
        
        // return the EST array
        return LST;     
    }
    
    // method to find early activity times
    public int[] EAT(int[] EAT, int[] EST) {
        
        // set counter for indexes to 0
        int counter = 0;
        
        // iterate through each adjacency matrix cell
        for (int i = 0; i < nodes; i++) {  
            for (int j = 0; j < nodes; j++) {
                
                // if the cell has a value, execute if statement
                if (adjMatrix[i][j] != -1) {
                    
                    // set the EAT to the EST of the activity's starting stage
                    EAT[counter] = EST[i];
                    
                    // increment the index counter
                    counter++;
                }   
            } 
        }
        
        // return the array of EATs
        return EAT;
    }
    
    // method to count how many activities exist in the project
    public int countActivities() {
        
        // start the activity counter at 0
        int activities = 0;
        
        // iterate through each cell of the adjacency matrix
        for (int i = 0; i < nodes; i++) {    
            for (int j = 0; j < nodes; j++) {
                
                // if the cell has a value, execute the if statement
                if (adjMatrix[i][j] != -1) {
                    
                    // increment the activity counter
                    activities++;
                }   
            } 
        }
        
        // return the number of activities found
        return activities;
    }
    
    // method to calculate the lare activity times
    public int[] LAT(int[] LAT, int[] LST) {
        
        // set index counter to 0
        int counter = 0;
        
        // iterate through each cell of the adjacency matrix
        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                
                // if the cell has a value, execute if statement
                if (adjMatrix[i][j] != -1) {
                    
                    // set the LAT to the LST of the activity's ending stage - 
                    // activity duration
                    LAT[counter] = (LST[j] - adjMatrix[i][j]);
                    
                    // increment the index counter
                    counter++;
                }   
            } 
        }
        
        // return the array of LATs
        return LAT;
    }
    
    // method to find out which activities are critical activities
    public ArrayList findCriticalActivities(int[] LAT, int[] EAT, int numActivities) {
        
        // create an arraylist to store the critical activities
        ArrayList criticalActivities = new ArrayList();
        
        // iterate through for each activity
        for (int i = 0; i < numActivities; i++) {
            
            // calculate the slack time (difference between activity's LAT & EAT)
            int slackTime = (LAT[i] - EAT[i]);
            
            // if the slack time is 0, execute if statement
            if (slackTime == 0) {
                
                // add activity to the critical activities arraylist
                criticalActivities.add(i + 1);
            }
        }
        
        // return arraylist of critical activities
        return criticalActivities;
    }
}
