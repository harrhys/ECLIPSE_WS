package com.farbig.practice.dsa.datastructures;

import java.util.ArrayList;

//Bellman–Ford Algorithm
public class GraphList {

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

		System.out.println(edges.size() + " edges added successfully\n");

		System.out.println();

		shortestPath();
		
		System.out.println();
		
		longestPath();
	}

	static ArrayList<Vertex> vertices = new ArrayList<Vertex>();

	static ArrayList<Edge> edges = new ArrayList<Edge>();

	static int counter1, counter2;

	static int[][] shortestDistance;

	static String[][] shortestPath;

	static int[][] longestDistance;

	static String[][] longestPath;

	static void initialize() {

		int vertexCount = vertices.size();

		// Initialize the shortest distance and path variables.
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

		// Initialize the longest distance and path variables.
		longestDistance = new int[vertexCount + 1][vertexCount + 1];
		longestPath = new String[vertexCount + 1][vertexCount + 1];

		for (int i = 0; i <= vertexCount; i++) {
			for (int j = 0; j < vertexCount; j++) {
				if (i != j) {
					longestDistance[i][j] = Integer.MIN_VALUE;
					longestPath[i][j] = "";
				} else {
					longestDistance[i][j] = 0;
					longestPath[i][j] = "" + vertices.get(i);
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

		Edge e1 = new Edge(frm, to, distance);
		edges.add(e1);
		System.out.println(e1);
		// add the below line to calculate shortest distances starting from all vertices
		Edge e2 = new Edge(to, frm, distance);
		edges.add(e2);
		System.out.println(e2);
	}

	static void shortestPath() {
		
		counter1 = counter2= 0;

		StringBuilder sb = new StringBuilder();

		sb.append("Shortest Path Calculations from All Vertices:\n");
		// add the below line to calculate shortest distances starting from all vertices
		for (int startIndex = 0; startIndex < vertices.size(); startIndex++) {

			Vertex start = vertices.get(startIndex);

			for (int i = 0; i < vertices.size(); i++) {

				sb.append(start + " Round " + (i + 1) + "\n");

				boolean furtherRoundsRequired = false;

				for (int edgeIndex = 0; edgeIndex < edges.size(); edgeIndex++) {

					Edge edge = edges.get(edgeIndex);

					Vertex src = edge.srcVertex;
					Vertex tgt = edge.tgtVertex;
					int src2tgt = edge.distance;

					// System.out.println("PROCESS:S - " + start + "->" + tgt + " : " + edge);
					int start2src = shortestDistance[start.index][src.index];
					int start2tgt = shortestDistance[start.index][tgt.index];

					if (start2src != Integer.MAX_VALUE && start2tgt > start2src + src2tgt) {

						start2tgt = start2src + src2tgt;

						shortestDistance[start.index][tgt.index] = start2tgt;

						shortestPath[start.index][tgt.index] = shortestPath[start.index][src.index] + "->" + tgt;

						sb.append("UUU - " + start + "->" + tgt + " : " + edge + " : "
								+ shortestDistance[start.index][tgt.index] + " :: "
								+ shortestPath[start.index][tgt.index] + "\n");

						furtherRoundsRequired = true;

					} else if (start2src == Integer.MAX_VALUE && !start.equals(tgt)) {

						sb.append("XXX - " + start + "->" + tgt + " : " + edge + " : "
								+ shortestDistance[start.index][tgt.index] + " :: "
								+ shortestPath[start.index][tgt.index] + "\n");
					} else {

						sb.append("NNN - " + start + "->" + tgt + " : " + edge + " : "
								+ shortestDistance[start.index][tgt.index] + " :: "
								+ shortestPath[start.index][tgt.index] + "\n");

					}

				}
				sb.append("\n");
				if (!furtherRoundsRequired)
					break;
			}
		}

		System.out.println(sb.toString());

		// Print the result
		System.out.println("\nShortest Path Results:");
		printDistance(shortestDistance, shortestPath);
	}

	static void longestPath() {
		
		counter1 = counter2= 0;

		StringBuilder sb = new StringBuilder();

		sb.append("Longest Path Calculations from All Vertices:\n");

		// add the below line to calculate shortest distances starting from all vertices
		// add the below line to calculate shortest distances starting from all vertices
		for (int startIndex = 0; startIndex < vertices.size(); startIndex++) {

			Vertex start = vertices.get(startIndex);

			for (int i = 0; i <= vertices.size(); i++) {

				sb.append(start + " Round " + (i + 1) + "\n");

				boolean furtherRoundsRequired = false;

				for (int edgeIndex = 0; edgeIndex < edges.size(); edgeIndex++) {

					counter1++;

					Edge edge = edges.get(edgeIndex);

					Vertex src = edge.srcVertex;
					Vertex tgt = edge.tgtVertex;
					int src2tgt = edge.distance;
					int start2src = longestDistance[start.index][src.index];
					int start2tgt = longestDistance[start.index][tgt.index];

					if (start2src != Integer.MIN_VALUE && start2tgt < start2src + src2tgt
							&& !pathAlreadyHasVertex(longestPath[start.index][src.index], tgt)) {

						counter2++;

						start2tgt = start2src + src2tgt;

						longestDistance[start.index][tgt.index] = start2tgt;

						longestPath[start.index][tgt.index] = longestPath[start.index][src.index] + "->" + tgt;

						sb.append("UUU - " + start + "->" + tgt + " : " + edge + " : "
								+ longestDistance[start.index][tgt.index] + " :: " + longestPath[start.index][tgt.index]
								+ "\n");

						furtherRoundsRequired = true;

					} else if (start2src == Integer.MAX_VALUE && !start.equals(tgt)) {

						sb.append("XXX - " + start + "->" + tgt + " : " + edge + " : "
								+ longestDistance[start.index][tgt.index] + " :: " + longestPath[start.index][tgt.index]
								+ "\n");
					} else {

						sb.append("NNN - " + start + "->" + tgt + " : " + edge + " : "
								+ shortestDistance[start.index][tgt.index] + " :: "
								+ shortestPath[start.index][tgt.index] + "\n");
					}
					
					sb.append("\n");
					if (!furtherRoundsRequired)
						break;
				}
			}
		}

		// Print the result
		System.out.println("\nLongest Path Results:");
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

		public Vertex(int index, String name) {
			this.index = index;
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

	static class Edge {

		Vertex srcVertex, tgtVertex;
		int distance;

		public Edge(Vertex srcVertex, Vertex tgtVertex, int distance) {

			this.srcVertex = srcVertex;
			this.tgtVertex = tgtVertex;
			this.distance = distance;
		}

		public String toString() {
			return srcVertex + "->" + tgtVertex + " : " + distance;
		}
	}

}
