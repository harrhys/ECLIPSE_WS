package com.farbig.practice.dsa.datastructures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import com.farbig.practice.util.ThreadUtil;

//Dijkstra’s algorithm
public class GraphQueue {

	public static void main(String[] args) {

		Vertex A = addVertex("A");
		Vertex B = addVertex("B");
		Vertex C = addVertex("C");
		Vertex D = addVertex("D");
		Vertex E = addVertex("E");

		initialize();

		System.out.println(vertices.size() + " vertices added successfully\n");

		addEdge(A, B, 1);
		addEdge(A, C, 6);
		addEdge(A, D, 9);
		addEdge(A, E, 5);
		addEdge(B, C, 2);
		addEdge(B, D, 7);
		addEdge(B, E, 10);
		addEdge(C, D, 3);
		addEdge(C, E, 8);
		addEdge(D, E, 4);

		System.out.println("edges added successfully\n");

		shortestPath();

		System.out.println();

		//longestPath();
	}

	static ArrayList<Vertex> vertices = new ArrayList<Vertex>();

	static int[][] shortestDistance;

	static String[][] shortestPath;

	static Stack<Integer>[][] longestDistance;

	static Stack<String>[][] longestPath;

	static int counter1, counter2;

	static void initialize() {

		int vertexCount = vertices.size();

		// Initialize the distance and path variables.
		shortestDistance = new int[vertexCount][vertexCount];
		shortestPath = new String[vertexCount][vertexCount];

		for (int i = 0; i < vertexCount; i++) {
			for (int j = 0; j < vertexCount; j++) {
				if (i != j) {
					shortestDistance[i][j] = Integer.MAX_VALUE;
					shortestPath[i][j] = "";
				} else {
					shortestDistance[i][j] = 0;
					shortestPath[i][j] = "" + vertices.get(i);
				}
			}
		}

		// Initialize the distance and path variables.
		longestDistance = new Stack[vertexCount][vertexCount];
		longestPath = new Stack[vertexCount][vertexCount];

		for (int i = 0; i < vertexCount; i++) {
			for (int j = 0; j < vertexCount; j++) {
				if (i != j) {
					longestDistance[i][j] = new Stack<Integer>();
					longestDistance[i][j].add(Integer.MIN_VALUE);
					longestPath[i][j] = new Stack<String>();
					longestPath[i][j].add("" + vertices.get(i));
				} else {
					longestDistance[i][j] = new Stack<Integer>();
					longestDistance[i][j].add(0);
					longestPath[i][j] = new Stack<String>();
					longestPath[i][j].add("" + vertices.get(i));
				}
			}
		}

	}

	static Vertex addVertex(String name) {

		Vertex v = new Vertex(vertices.size(), name);

		vertices.add(v);

		System.out.println("V::" + v.name + "-" + v.index);

		return v;
	}

	static void addEdge(Vertex frm, Vertex to, int distance) {

		Edge e1 = new Edge(to, distance);
		frm.edges.add(e1);
		System.out.println(frm + "->" + e1);
		
		// add the below line to calculate shortest distances starting from all vertices
		Edge e2 = new Edge(frm, distance);
		to.edges.add(e2);
		System.out.println(to + "->" + e2);
	}

	// calculates the shortest from each vertex to all the remaining vertices and
	// prints the results
	static void shortestPath() {

		StringBuilder sb = new StringBuilder();

		for (int startIndex = 0; startIndex < vertices.size(); startIndex++) {

			Vertex start = vertices.get(startIndex);

			System.out.print("Shortest Path Calculations from Start Vertex::" + start + "\n");

			Queue<Vertex> queue = new LinkedList();
			queue.add(start);

			while (!queue.isEmpty()) {

				Vertex mid = queue.peek();

				queue.remove();

				System.out.print("Processing edges of Mid Vertex :: " + mid + " :: " + mid.edges.size() + "\n");

				for (int edgeIndex = 0; edgeIndex < mid.edges.size(); edgeIndex++) {

					Edge edge = mid.edges.get(edgeIndex);
					Vertex end = edge.tgtVertex;
					int mid2endDist = edge.distance;

					int start2midDist = shortestDistance[start.index][mid.index];
					int start2endDist = shortestDistance[start.index][end.index];
					String start2midPath = shortestPath[start.index][mid.index];
					String start2endPath = shortestPath[start.index][end.index];

					if (start2endDist > start2midDist + mid2endDist) {

						shortestDistance[start.index][end.index] = start2midDist + mid2endDist;
						shortestPath[start.index][end.index] = shortestPath[start.index][mid.index] + "->" + end;

						if (!queue.contains(end))
							queue.add(end);

						System.out.print("(" + start + "->" + end + " : " + start2endDist + ") >>> (" + start2midPath
								+ " : " + start2midDist + ")::(" + mid + "->" + edge + ") >>> " + start2endPath + " : "
								+ start2endDist + "\n");
					} else {
						System.err.print("(" + start + "->" + end + " : " + start2endDist + ") >>> (" + start2midPath
								+ " : " + start2midDist + ")::(" + mid + "->" + edge + ") >>> " + start2endPath + " : "
								+ start2endDist + "\n");
					}
					ThreadUtil.sleep(700);
				}
				System.out.print("\n");
			}
		}
		
		System.out.print("\nShortest Path Results:\n");
		// Print the result
		System.out.println(sb.toString());
		printDistance(shortestDistance, shortestPath);
	}

	//Longest Path doesnt work perfectly always..need to fix this if possible.
	static void longestPath() {

		StringBuilder sb = new StringBuilder();

		for (int startIndex = 0; startIndex < vertices.size(); startIndex++) {

			Vertex start = vertices.get(startIndex);

			System.out.print("Longest Path Calculations from Start Vertex::" + start + "\n");

			Queue<Vertex> queue = new LinkedList();
			queue.add(start);

			while (!queue.isEmpty()) {

				Vertex mid = queue.peek();

				queue.remove();

				System.out.print("Processing edges of Mid Vertex :: " + mid + " :: " + mid.edges.size() + "\n");

				for (int edgeIndex = 0; edgeIndex < mid.edges.size(); edgeIndex++) {

					Edge edge = mid.edges.get(edgeIndex);
					Vertex end = edge.tgtVertex;
					int mid2endDist = edge.distance;

					int ldstart2midsize = longestDistance[start.index][mid.index].size();

					for (int i = ldstart2midsize - 1; i >= 0; i--) {

						int start2midDist = longestDistance[start.index][mid.index].get(i);
						int ldstart2endsize = longestDistance[start.index][end.index].size();
						int start2endDist = longestDistance[start.index][end.index].get(ldstart2endsize - 1);
						String start2midPath = longestPath[start.index][mid.index].get(i);
						String start2endPath = longestPath[start.index][end.index].get(ldstart2endsize - 1);

						if (start2midDist != Integer.MIN_VALUE && start2endDist < start2midDist + mid2endDist
								&& !pathAlreadyHasVertex(start2midPath, end)) {

							longestDistance[start.index][end.index].add(start2midDist + mid2endDist);
							start2endPath = start2midPath + "->" + end;
							longestPath[start.index][end.index].add(start2endPath);
							if (start2endDist == Integer.MIN_VALUE) {
								longestDistance[start.index][end.index].remove(0);
								longestPath[start.index][end.index].remove(0);
							}

							//if (!queue.contains(end))
								queue.add(end);

							System.out.print("(" + start + "->" + end + " : " + start2endDist + ") >>> ("
									+ start2midPath + " : " + start2midDist + ")::(" + mid + "->" + edge + ") >>> "
									+ start2endPath + " : " + start2endDist + "\n");
							break;

						} else {
							System.err.print("(" + start + "->" + end + " : " + start2endDist + ") >>> ("
									+ start2midPath + ":" + start2midDist + ")::(" + mid + "->" + edge + ") >>> ("
									+ start2endPath + " : " + start2endDist + ")\n");
						}
					}
				}
				System.out.print("\n");
			}
		}
		System.out.print("\nLongest Path Results:");
		// Print the result
		System.out.println(sb.toString());
		printDistance(longestDistance, longestPath);
	}

	static boolean pathAlreadyHasVertex(String path, Vertex vertex) {
		
		boolean has = false;
		String[] s = path.split("->");
		for (int i = 0; i < s.length; i++) {
			if (s[i].equals(vertex + ""))
				has = true;
		}
		return has;
	}

	// Function to print shortest distance from All source
	static void printDistance(int[][] distArray, String[][] pathArray) {

		System.out.print("from \t to \t Distance \t" + " path\n");

		for (int from = 0; from < vertices.size(); from++) {

			for (int to = 0; to < vertices.size(); to++) {

				System.out.printf("%s \t %s\t %d \t\t %s\n", vertices.get(from), vertices.get(to), distArray[from][to],
						pathArray[from][to]);
			}
		}
	}

	// Function to print shortest distance from All source
	static void printDistance(Stack<Integer>[][] distArray, Stack<String>[][] pathArray) {

		System.out.print("from \t to \t Distance \t" + " path\n");

		for (int from = 0; from < vertices.size(); from++) {

			for (int to = 0; to < vertices.size(); to++) {

				for (int i = pathArray[from][to].size(); i <= pathArray[from][to].size(); i++) {

					System.out.printf("%s \t %s\t %d \t\t %s\n", vertices.get(from), vertices.get(to),
							distArray[from][to].peek(), pathArray[from][to].peek());
				}
			}
		}
	}

	// Function to print shortest distance from source
	static void printDistance(Vertex startVertex, int[][] distArray, String[][] pathArray) {

		System.out.print("from \t to \t Distance \t" + " path\n");

		for (int to = 0; to < vertices.size(); to++) {

			System.out.printf("%s \t %s\t %d \t\t %s\n", startVertex, vertices.get(to),
					distArray[startVertex.index][to], pathArray[startVertex.index][to]);
		}
	}

	static class Vertex {

		int index;
		String name;
		List<Edge> edges;

		public Vertex(int index, String name) {
			this.index = index;
			this.name = name;
			edges = new ArrayList();
		}

		public String toString() {
			return name;
		}
	}

	static class Edge {

		Vertex tgtVertex;
		int distance;

		public Edge(Vertex tgtVertex, int distance) {

			this.tgtVertex = tgtVertex;
			this.distance = distance;
		}

		public String toString() {
			return tgtVertex + " : " + distance;
		}
	}

}
