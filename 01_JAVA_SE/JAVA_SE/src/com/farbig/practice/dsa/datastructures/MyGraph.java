package com.farbig.practice.dsa.datastructures;

import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

public class MyGraph {

	// Driver code
	public static void main(String[] args) {

		int vertexCount = 5;
		// int startVertex = 3;

		for (int i = 0; i < graph.length; i++) {
			graph[i] = new ArrayList<pair>();
		}

		// Connect vertex a to b with distance d
		// addEdge(a, b, d)
		addEdge(1, 2, 1);
		addEdge(1, 3, 6);
		addEdge(1, 4, 9);
		addEdge(1, 5, 5);
		addEdge(2, 3, 2);
		addEdge(2, 4, 7);
		addEdge(2, 5, 10);
		addEdge(3, 4, 3);
		addEdge(3, 5, 8);
		addEdge(4, 5, 4);

		// 1 7 9 4

		// shortestPath(vertexCount);

		longestPath(vertexCount);

	}

	// Function to add edges in the graph
	// connecting a pair of vertex(frm) and distance
	// to another vertex(to) in graph
	static void addEdge(int frm, int to, int distance) {

		graph[frm].add(new pair(to, distance));
		graph[to].add(new pair(frm, distance));
	}

	// Graph is stored as vector of vector of pairs
	// first element of pair store vertex
	// second element of pair store weight
	static ArrayList<pair>[] graph = new ArrayList[100000];

	// Create array distArray to store shortest distance between two vertices
	static int[][] shortestDistance;

	// Create array shortestPath to store shortest path between two vertices
	static ArrayList<Integer>[][] shortestPath;

	// Create array distArray to store shortest distance between two vertices
	static int[][] longestDistance;

	// Create array shortestPath to store shortest path between two vertices
	static ArrayList<Integer>[][] longestPath;

	static void shortestPath(int vertexCount) {

		shortestPath = new ArrayList[vertexCount + 1][vertexCount + 1];
		shortestDistance = new int[vertexCount + 1][vertexCount + 1];

		for (int startVertex = 1; startVertex <= vertexCount; startVertex++) {

			// Boolean array to check if vertex
			// is present in queue or not
			boolean[] inQueue = new boolean[vertexCount + 1];

			// Initialize the distance from source to
			// other vertex as Integer.MAX_VALUE(infinite)
			for (int i = 1; i <= vertexCount; i++) {
				shortestDistance[startVertex][i] = Integer.MAX_VALUE;
				shortestPath[startVertex][i] = new ArrayList();
			}
			shortestDistance[startVertex][startVertex] = 0;
			shortestPath[startVertex][startVertex].add(startVertex);

			Queue<Integer> queue = new LinkedList<>();
			queue.add(startVertex);
			inQueue[startVertex] = true;

			while (!queue.isEmpty()) {

				// Take the front vertex from Queue
				int srcVertex = queue.peek();
				queue.remove();
				inQueue[srcVertex] = false;

				// Relaxing all the adjacent edges of
				// vertex taken from the Queue
				for (int tgtVertexIndex = 0; tgtVertexIndex < graph[srcVertex].size(); tgtVertexIndex++) {

					int tgtVertex = graph[srcVertex].get(tgtVertexIndex).tgtVertex;
					int srctgtdistance = graph[srcVertex].get(tgtVertexIndex).distance;

					if (shortestDistance[startVertex][tgtVertex] > shortestDistance[startVertex][srcVertex]	+ srctgtdistance) {

						shortestDistance[startVertex][tgtVertex] = shortestDistance[startVertex][srcVertex]	+ srctgtdistance;

						shortestPath[startVertex][tgtVertex] = new ArrayList();

						shortestPath[startVertex][tgtVertex].addAll(shortestPath[startVertex][srcVertex]);

						shortestPath[startVertex][tgtVertex].add(tgtVertex);

						String path = "";

						for (int j = 0; j < shortestPath[startVertex][tgtVertex].size(); j++) {

							path += (shortestPath[startVertex][tgtVertex].get(j) + " ");
						}
						System.out.println(startVertex + "->" + tgtVertex + " - "
								+ shortestDistance[startVertex][tgtVertex] + " :: " + path);

						// Check if vertex tgtVertex is in Queue or not
						// if not then push it into the Queue
						if (!inQueue[tgtVertex]) {
							queue.add(tgtVertex);
							inQueue[tgtVertex] = true;
						}
					}
				}
			}

		}

		// Print the result
		print_distance(shortestDistance, shortestPath, vertexCount);
	}

	static void longestPath(int vertexCount) {

		longestPath = new ArrayList[vertexCount + 1][vertexCount + 1];
		longestDistance = new int[vertexCount + 1][vertexCount + 1];

		for (int startVertex = 1; startVertex <= vertexCount; startVertex++) {

			// Boolean array to check if vertex
			// is present in queue or not
			boolean[] inQueue = new boolean[vertexCount + 1];

			// Initialize the distance from source to
			// other vertex as Integer.MAX_VALUE(infinite)
			for (int i = 1; i <= vertexCount; i++) {
				longestDistance[startVertex][i] = Integer.MIN_VALUE;
				longestPath[startVertex][i] = new ArrayList();
			}
			longestDistance[startVertex][startVertex] = 0;
			longestPath[startVertex][startVertex].add(startVertex);

			Queue<Integer> queue = new LinkedList<>();
			queue.add(startVertex);
			inQueue[startVertex] = true;

			while (!queue.isEmpty()) {

				// Take the front vertex from Queue
				int srcVertex = queue.peek();
				queue.remove();
				inQueue[srcVertex] = false;

				// Relaxing all the adjacent edges of vertex taken from the Queue
				for (int tgtVertexIndex = 0; tgtVertexIndex < graph[srcVertex].size(); tgtVertexIndex++) {

					int tgtVertex = graph[srcVertex].get(tgtVertexIndex).tgtVertex;
					int srctgtdistance = graph[srcVertex].get(tgtVertexIndex).distance;

					if (longestDistance[startVertex][tgtVertex] < longestDistance[startVertex][srcVertex]
							+ srctgtdistance) {

						// To ensure the vertex which is already in path is not visited again
						if (!longestPath[startVertex][srcVertex].contains(tgtVertex)) {

							longestDistance[startVertex][tgtVertex] = longestDistance[startVertex][srcVertex]
									+ srctgtdistance;

							longestPath[startVertex][tgtVertex] = new ArrayList();

							longestPath[startVertex][tgtVertex].addAll(longestPath[startVertex][srcVertex]);

							longestPath[startVertex][tgtVertex].add(tgtVertex);

							String path = "";

							for (int j = 0; j < longestPath[startVertex][tgtVertex].size(); j++) {

								path += (longestPath[startVertex][tgtVertex].get(j) + " ");
							}
							System.out.println(startVertex + "->" + tgtVertex + " - "
									+ longestDistance[startVertex][tgtVertex] + " :: " + path);

							// Check if vertex tgtVertex is in Queue or not
							// if not then push it into the Queue
							queue.add(tgtVertex);
							inQueue[tgtVertex] = true;
						}
					}
				}
			}

		}

		// Print the result
		print_distance(longestDistance, longestPath, vertexCount);
	}

	static class pair {
		int tgtVertex, distance;

		public pair(int vertex, int distance) {
			this.tgtVertex = vertex;
			this.distance = distance;
		}
	}

	// Function to print shortest distance from source
	static void print_distance(int distArray[][], ArrayList<Integer> pathArray[][], int vertexCount) {

		System.out.print("from \t to \t Distance \t" + " path\n");

		for (int from = 1; from <= vertexCount; from++) {

			for (int to = 1; to <= vertexCount; to++) {

				String path = "";

				for (int pathIndex = 0; pathIndex < pathArray[from][to].size(); pathIndex++) {

					path += (pathArray[from][to].get(pathIndex) + " ");
				}

				System.out.printf("%d \t %d\t %d \t\t %s\n", from, to, distArray[from][to], path);

			}

		}
	}

}
