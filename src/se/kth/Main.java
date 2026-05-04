/**
 * The app will find the most suited possible paths from a source point to a point destination
 * that is though a specific in-between point, the path map represented with numbers for this case and refer to a state.
 */

package se.kth;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        runProgram();
    }

    /**
     * Interface for the app.
     */
    static void runProgram() {
        // interface to get the information from the user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the source vertex: ");
        int source = scanner.nextInt();
        System.out.println("Enter the destination vertex: ");
        int destination = scanner.nextInt();
        System.out.println("Enter the middle vertex between them: ");
        int middle = scanner.nextInt();
        System.out.println("");
        System.out.println("Results: ");
        System.out.println(findShortestPathThroughANode(source, destination, middle));
    }

    /**
     * Find the shortest path possible from the start point to the end point through middle point.
     * @param source the node we want to start the path from.
     * @param destination where the path ends from the source
     * @param middleNode the point that the path should go through
     * @return the shortest path in a string
     */
    static String findShortestPathThroughANode(int source, int destination, int middleNode) {
        // Preparation
        String file = "NYC.txt";
        String data = Utilities.readDatabase(file);
        String[] dataInput = Utilities.getInput(data);
        EdgeWeightedGraph graph = new EdgeWeightedGraph(dataInput);
        StringBuilder shortestPath = new StringBuilder();

        DijkstraUndirectedSP firstPartOfPath = new DijkstraUndirectedSP(graph, source);
        DijkstraUndirectedSP secondPartOfPath = new DijkstraUndirectedSP(graph, middleNode);

        if(!(firstPartOfPath.hasPathTo(middleNode) && secondPartOfPath.hasPathTo(destination))) {
            return "No such path exist between " + source + " and " + destination + " through "+ middleNode + "!";
        }

        // First part of the path
        for(Edge e : firstPartOfPath.pathTo(middleNode)) {
            shortestPath.append("-->").append(e.toString());
        }

        shortestPath.append("\n");

        // Second part of the path
        for(Edge e : secondPartOfPath.pathTo(destination)) {
            shortestPath.append("-->").append(e.toString());
        }

        shortestPath.delete(0,3);

        return shortestPath.toString();
    }

}
