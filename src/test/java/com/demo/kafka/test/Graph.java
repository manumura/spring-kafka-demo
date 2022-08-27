package com.demo.kafka.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

// https://fireship.io/courses/javascript/interview-graphs/
public class Graph {

    private Map<Vertex, List<Vertex>> adjacencies;

    public Graph() {
        this.adjacencies = new HashMap<>();
    }

    void addVertex(String label) {
        adjacencies.putIfAbsent(new Vertex(label), new ArrayList<>());
    }

    void addEdge(String label1, String label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        adjacencies.get(v1).add(v2);
        adjacencies.get(v2).add(v1);
    }

    Set<Vertex> breadthFirstTraversal(Vertex root) {
        Set<Vertex> visited = new LinkedHashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(root);
        visited.add(root);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            for (Vertex adjacency : adjacencies.get(current)) {
                if (!visited.contains(adjacency)) {
                    visited.add(adjacency);
                    queue.add(adjacency);
                }
            }
        }

        System.out.println("visited " + visited);
        return visited;
    }

    Set<Vertex> depthFirstTraversal(Vertex root, Set<Vertex> visited) {
        visited.add(root);

        for (Vertex adjacency : adjacencies.get(root)) {
            if (!visited.contains(adjacency)) {
                depthFirstTraversal(adjacency, visited);
            }
        }

        System.out.println("visited " + visited);
        return visited;
    }

//    Set<Vertex> depthFirstTraversal2(Vertex root) {
//        Set<Vertex> visited = new LinkedHashSet<>();
//        Stack<Vertex> stack = new Stack<>();
//        stack.push(root);
//
//        while (!stack.isEmpty()) {
//            Vertex current = stack.pop();
//            if (!visited.contains(current)) {
//                visited.add(current);
//                for (Vertex adjacency : adjacencies.get(current)) {
//                    stack.push(adjacency);
//                    System.out.println("stack " + stack);
//                }
//            }
//        }
//
//        System.out.println("visited " + visited);
//        return visited;
//    }

    public static void main(String[] args) {

        List<String> airports = List.of("PHX", "BKK", "OKC", "JFK", "LAX", "MEX", "EZE", "HEL", "LOS", "LAP", "LIM");
        List<String[]> routes = List.of(
                new String[]{"PHX", "LAX"},
                new String[]{"PHX", "JFK"},
                new String[]{"JFK", "OKC"},
                new String[]{"JFK", "HEL"},
                new String[]{"JFK", "LOS"},
                new String[]{"MEX", "LAX"},
                new String[]{"MEX", "BKK"},
                new String[]{"MEX", "LIM"},
                new String[]{"MEX", "EZE"},
                new String[]{"LIM", "BKK"}
        );

        // Create the Graph
        Graph g = new Graph();
        for (String airport : airports) {
            g.addVertex(airport);
        }

        for (String[] route: routes) {
            g.addEdge(route[0], route[1]);
        }

//        g.breadthFirstTraversal(new Vertex("PHX"));
        g.depthFirstTraversal(new Vertex("PHX"), new LinkedHashSet<>());
//        g.depthFirstTraversal2(new Vertex("PHX"));
    }

    public static class Vertex {

        private final String value;

        public Vertex(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "value='" + value + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Vertex)) return false;
            Vertex vertex = (Vertex) o;
            return Objects.equals(getValue(), vertex.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getValue());
        }
    }
}
