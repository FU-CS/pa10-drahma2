package pa10;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class EdgeList implements Graph{
    private static class Edge {
        int source;
        int destination;

        public Edge(int source, int destination) {
            this.source = source;
            this.destination = destination;
        }
    }

    // stores edges
    List<Edge> edges;

    // initialize the graph
    public EdgeList() {
        edges = new ArrayList<>();
    }

    // adds directed edges
    public void addEdge(int v, int w) {
        edges.add(new Edge(v, w));
    }

    public String topologicalSort() {
        // Sorted: stack to store the topological ordering of the vertices.
        Stack<Integer> sorted = new Stack<>();
        // Visited: set to keep track of visited vertices.
        Set<Integer> visited = new HashSet<>();
        // This is created so we have a set of all the vertices from edges (needed because it is implemented as an EdgeList)
        Set<Integer> vertices = new HashSet<>();
        // adds all edges to vertices
        for (Edge edge : edges) {
            vertices.add(edge.source);
            vertices.add(edge.destination);
        }
        // dfs on each vertex that has not been visited
        for (int vertex : vertices) {
            if (!visited.contains(vertex)) {
                DFS(vertex, sorted, visited);
            }
        }
        String result = "";
        while (!sorted.isEmpty()) {
            result = result + sorted.pop() + " "; 
        }
        
        return result.trim(); 
    }

    private void DFS(int vertex, Stack<Integer> sorted, Set<Integer> visited) {
        // add vertex to visited once it is visited
        visited.add(vertex);
        // Visit each neighbor
        for (Edge edge : edges) {
            if (edge.source == vertex && !visited.contains(edge.destination)) {
                DFS(edge.destination, sorted, visited);
            }
        }
        // Push vertex onto sorted
        sorted.push(vertex);
    }


    public String kahn() {
        // Count incoming edges for all vertices
        Set<Integer> vertices = new HashSet<>();
        // add all edges to the set
        for (Edge edge : edges) {
            vertices.add(edge.source);
            vertices.add(edge.destination);
        }    

        // used to count incoming edges for all vertices
        int[] count = new int[Integer.MAX_VALUE];
        for (Edge edge : edges) {
            count[edge.destination]+= 1;
        }
        
        // add all with count value 0 to the queue
        Queue<Integer> queue = new LinkedList<>();
        for (Integer vertex : vertices) {
            if (count[vertex] == 0) {
                queue.add(vertex);
            }
        }
        
        // while queue is empty
            //pick curr
            //add curr to result
            //visit all children
            //update count
            //repeat until set is empty
        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()){
            int current = queue.poll();
            result.add(current);
            for (Edge edge : edges) {
                if (edge.source == current) {
                    int neighbor = edge.destination;
                    count[neighbor]-= 1;
                    if (count[neighbor] == 0) {
                        queue.add(neighbor);
                    }
                }
            }
        }
        // Build result string
        if (result.size() == vertices.size()) {
            String resultStr = "";
            for (int i = 0; i < result.size(); i++) {
                resultStr += result.get(i);
                if (i != result.size() - 1) {
                    resultStr += " ";
                }
            }
            return resultStr;
        } 
        else {
            return "Graph has a cycle";
        }
    }
    

}
