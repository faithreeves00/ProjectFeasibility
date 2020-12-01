// File Name: CanItBeDone.java
// Author: Faith Reeves
// Program Purpose: Allows the user to create a project (a directed, weighted 
//                  graph) that will be analyzed to determine if the project is 
//                  feasible, how long the project will take, and what activities 
//                  are “critical” (activitis that will delay entire project if 
//                  not completed int time).
                
package canitbedone;

import java.util.*;

public class CanItBeDone {

    public static void main(String[] args) {

        // declare variable to store the number of stages/nodes in the project
        int numStages;

        // create Scanner object
        Scanner input = new Scanner(System.in);

        // prompt user to enter number of stages
        System.out.print("Enter the number of stages for your project: ");

        // read in number of stages
        numStages = input.nextInt();
        
        // create arrays that will store times
        int[] EST = new int[numStages];
        int[] LST = new int[numStages];

        // create a graph object with number of stages
        FaithsGraph project = new FaithsGraph(numStages);

        // set every cell's value to -1
        project.setAllNull();

        // create an array to store the stages
        int[] stages = new int[numStages];

        // iterate through for each stage
        for (int i = 0; i < numStages; i++) {

            // prompt user for the current stage
            System.out.print("Enter the current stage (" + (i + 1) + "), number of "
                    + "adjacent stages, & then pairs of each adjacent stage and "
                    + "the activity cost to get to that stage (seperate answers"
                    + " with spaces): ");

            // read in and store current stage
            stages[i] = (input.nextInt() - 1);
            
            // if the user enters the incorrect current stage, execute if statement
            if (stages[i] != i) {
                
                // display error message
                System.out.println("You did not enter the current stage. Exiting"
                        + " program.");
                
                // exit programn 
                System.exit(1);
            }

            // declare and read in number of nodes reachable from current node
            int numReachableFromCurrent = input.nextInt();

            // iterate through for each node reachable from current
            for (int j = 0; j < numReachableFromCurrent; j++) {

                // store users input for the adjacent stage
                int adjacentStage = (input.nextInt() - 1);

                // store users input for activity cost
                int activityCost = input.nextInt();

                // call addEdge method, pass it current stage, adjacent stage
                // and the activity cost between them
                project.addEdge(stages[i], adjacentStage, activityCost);
            }
        }

        // find topological order
        int[] topOrder = project.topologicalSort();

        // check if project is feasible, execute if statement
        if (numStages == topOrder.length) {

            // tell user project is feasible
            System.out.println("Project is feasible");
        } // if project is NOT feasible, execute else statement
        
        else {

            // tell user project is not feasible
            System.out.println("Project is not feasible");
        }

        // tell user you are going to display the topological ordering
        System.out.print("\nOrdering: ");

        // iterate through for each stage
        for (int i = 0; i < numStages; i++) {

            // display each stage in the top order
            System.out.print((topOrder[i] + 1) + " ");

        }
        
        // display the headers for the early and late stage times
        System.out.printf("\n\n%-10s%-10s%-10s\n", "Stages", "Early", "Late");
        
        // set 1st node in top order to have an EST of 0
        EST[0] = 0;
        
        // iterate through each stage in the top order except the first one
        for (int i = 1; i < numStages; i++) {
            
            // call EST method on each stage, in topological order
            EST = project.EST(topOrder[i], EST);
        }
        
        // set last node in top order to the total project time
        LST[numStages - 1] = EST[numStages - 1];
        
        // iterate through each stage in reverse top order
        for (int i = (numStages - 2); i > 0; i--) {
            
            // call LST method on each stage, in reverse top order
            LST = project.LST(topOrder[i], LST);    
        }
        
        // print out early and late stage times
        for (int i = 0; i < numStages; i++) {
            
            // print stage, EST, and then LST
            System.out.printf("%-10d%-10d%-10d\n", (i + 1), EST[i], LST[i]);     
        }
        
        // print the total project time
        System.out.println("\nTotal Project Time: " + EST[numStages - 1]);
        
        // call method to count the number of activities in the project
        int numActivities = project.countActivities();
        
        // create arrays to store activity times and critical activities
        int[] EAT = new int[numActivities];
        int[] LAT = new int[numActivities];
        
        // call EAT method and store data in EAT array
        EAT = project.EAT(EAT, EST);
        
        // call LAT method and store data in LAT array
        LAT = project.LAT(LAT, LST);
        
        // display headers for the early and late activity times
        System.out.printf("\n%-10s%-10s%-10s\n", "Activity", "Early", "Late");
        
        // print out early and late activity times
        for (int i = 0; i < numActivities; i++) {
            
            // print activity, EAT, and then LAT
            System.out.printf("%-10d%-10d%-10d\n", (i + 1), EAT[i], LAT[i]);
        }
        
        // create an arraylist to store the project's critical activities
        ArrayList criticalActivities = new ArrayList();
        
        // call critical activities method and store data in critical activities arraylist
        criticalActivities = project.findCriticalActivities(LAT, EAT, numActivities);

        // let user know you are going to print the critical activities
        System.out.print("Critical Activities: ");
        
        // print the critical activities seperated by commas
        System.out.println( 
        Arrays.toString(criticalActivities.toArray()) .replaceAll("[\\[\\]]", ""));
    }
}
