package com.farbig.practice.dsa.datastructures;

import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

public class Graph {

	// Driver code
	public static void main(String[] args) {

		int vertexCount = 5;
		int startVertex = 3;

		for (int i = 0; i < graph.length; i++) {
			graph[i] = new ArrayList<pair>();
		}


		// Connect vertex a to b with distance d
		// addEdge(a, b, d)
		addEdge(1, 2, 1);
		addEdge(1, 3, 6);
		addEdge(1, 4, 6);
		addEdge(1, 5, 5);
		addEdge(2, 3, 7);
		addEdge(2, 4, 8);
		addEdge(2, 5, 1);
		addEdge(3, 4, 3);
		addEdge(3, 5, 9);
		addEdge(4, 5, 4);

		// Calling shortestPathFaster function
		shortestPath(startVertex, vertexCount);
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

	// Function to compute the SPF algorithm
	static void shortestPathFaster(int startVertex, int vertexCount) {

		// Create array d to store shortest distance
		int[] distArray = new int[vertexCount + 1];

		// Boolean array to check if vertex
		// is present in queue or not
		boolean[] inQueue = new boolean[vertexCount + 1];

		// Initialize the distance from source to
		// other vertex as Integer.MAX_VALUE(infinite)
		for (int i = 0; i <= vertexCount; i++) {
			distArray[i] = Integer.MAX_VALUE;
		}
		distArray[startVertex] = 0;

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
				int distance = graph[srcVertex].get(tgtVertexIndex).distance;

				if (distArray[tgtVertex] > distArray[srcVertex] + distance) {
					distArray[tgtVertex] = distArray[srcVertex] + distance;

					// Check if vertex tgtVertex is in Queue or not
					// if not then push it into the Queue
					if (!inQueue[tgtVertex]) {
						queue.add(tgtVertex);
						inQueue[tgtVertex] = true;
					}
				}
			}
		}

		// Print the result
		print_distance(startVertex, distArray, vertexCount);
	}

	static ArrayList<Integer>[] shortestPath;

	static void shortestPath(int startVertex, int vertexCount) {

		shortestPath = new ArrayList[vertexCount + 1];

		// Create array d to store shortest distance
		int[] distArray = new int[vertexCount + 1];

		// Boolean array to check if vertex
		// is present in queue or not
		boolean[] inQueue = new boolean[vertexCount + 1];

		// Initialize the distance from source to
		// other vertex as Integer.MAX_VALUE(infinite)
		for (int i = 0; i <= vertexCount; i++) {
			distArray[i] = Integer.MAX_VALUE;
			shortestPath[i] = new ArrayList();
		}
		distArray[startVertex] = 0;
		shortestPath[startVertex].add(startVertex);

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
				int distance = graph[srcVertex].get(tgtVertexIndex).distance;

				if (distArray[tgtVertex] > distArray[srcVertex] + distance) {

					distArray[tgtVertex] = distArray[srcVertex] + distance;

					shortestPath[tgtVertex] = new ArrayList();

					shortestPath[tgtVertex].addAll(shortestPath[srcVertex]);

					shortestPath[tgtVertex].add(tgtVertex);

					String path = "";

					for (int j = 0; j < shortestPath[tgtVertex].size(); j++) {

						path += (shortestPath[tgtVertex].get(j) + " ");
					}
					System.out.println(tgtVertex + " - " + distArray[tgtVertex] + " - " + path);

					// Check if vertex tgtVertex is in Queue or not
					// if not then push it into the Queue
					if (!inQueue[tgtVertex]) {
						queue.add(tgtVertex);
						inQueue[tgtVertex] = true;
					}
				}
			}
		}

		// Print the result
		print_distance(startVertex, distArray, vertexCount);

	}

	static class pair {
		int tgtVertex, distance;

		public pair(int vertex, int distance) {
			this.tgtVertex = vertex;
			this.distance = distance;
		}
	}

	// Function to print shortest distance from source
	static void print_distance(int startVertex, int distArray[], int vertexCount) {

		System.out.print("from \t to \t Distance \t" + " path\n");

		for (int i = 1; i <= vertexCount; i++) {

			String path = "";

			for (int j = 0; j < shortestPath[i].size(); j++) {

				path += (shortestPath[i].get(j) + " ");
			}

			System.out.printf("%d \t %d\t %d \t\t %s\n", startVertex, i, distArray[i], path);
		}
	}

}
