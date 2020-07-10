package com.farbig.practice.dsa.datastructures;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class BST {

	Node root;

	Node successor;

	BST() {
		root = null;
	}

	public void add(int value) {

		System.out.print(value + " ");
		root = add(root, value);

	}

	private Node add(Node node, int value) {

		if (node == null) {
			node = new Node(value);
		} else if (node.data > value) {
			node.left = add(node.left, value);
		} else if (node.data < value) {
			node.right = add(node.right, value);
		}

		return node;
	}

	public int height(Node root) {
		if (root == null)
			return 0;
		else {
			/* compute height of each subtree */
			int lheight = height(root.left);
			int rheight = height(root.right);

			/* use the larger one */
			if (lheight > rheight)
				return (lheight + 1);
			else
				return (rheight + 1);
		}
	}

	public void getSuccessorNode(int givenValue) {

		getSuccessor(root, givenValue);

		System.out.println(successor.data);

	}

	private void getSuccessor(Node currentNode, int givenValue) {

		if (currentNode != null) {

			if (currentNode.data > givenValue) {
				successor = currentNode;
				getSuccessor(currentNode.left, givenValue);
			} else {
				getSuccessor(currentNode.right, givenValue);
			}
		}

	}

	public void traversePreOrder(Node node) {
		if (node == null)
			return;

		System.out.print(node.data + " ");

		traversePreOrder(node.left);

		traversePreOrder(node.right);

	}

	public void traverseInOrder(Node node) {

		if (node == null)
			return;

		traverseInOrder(node.left);

		System.out.print(node.data + " ");

		traverseInOrder(node.right);

	}

	public void traversePostOrder(Node node) {

		if (node == null)
			return;

		traversePostOrder(node.left);

		traversePostOrder(node.right);

		System.out.print(node.data + " ");

	}

	public void traverseLevelOrder(Node node) {

		if (node == null)
			return;

		for (int i = 1; i <= height(node); i++) {
			traverseLevelOrder(node, i);
		}

		System.out.println();
	}

	private void traverseLevelOrder(Node node, int level) {

		if (node == null)
			return;

		if (level == 1) {

			System.out.print(node.data + " ");

		} else {

			traverseLevelOrder(node.left, level - 1);
			traverseLevelOrder(node.right, level - 1);

		}
	}

	public void traverseInOrderNoRecursion(Node node) {
		Stack<Node> stack = new Stack<Node>();

		boolean done = false;

		while (!done) {
			if (node != null) {
				stack.push(node);
				node = node.left;
			} else {
				if (!stack.isEmpty()) {
					node = stack.pop();
					System.out.print(node.data + " ");
					node = node.right;
				} else {
					done = true;
				}

			}
		}
	}

	public void traverseLevelOrderNoRecursion(Node node) {

		Queue<Node> queue = new ArrayDeque<Node>();

		boolean done = false;

		while (!done) {

			System.out.print(node.data + " ");

			// System.out.print("[");
			if (node.left != null) {
				queue.add(node.left);
				// System.out.print(node.left.data);
			}
			/*
			 * else { System.out.print("*"); }
			 */
			// System.out.print(",");
			if (node.right != null) {
				queue.add(node.right);
				// System.out.print(node.right.data);
			}
			/*
			 * else { System.out.print("*"); } System.out.println("]");
			 */
			if (!queue.isEmpty()) {
				node = queue.poll();
			} else {
				done = true;
			}

		}

		System.out.println();
	}

	public static void main(String ar[]) {

		BST bst = getLoadedBST();

		System.out.println("added");

		bst.getSuccessorNode(50);

		bst.traversePreOrder(bst.root);

		bst.traverseInOrder(bst.root);

		bst.traversePostOrder(bst.root);

		System.out.println();

		bst.traverseLevelOrderNoRecursion(bst.root);
		bst.traverseLevelOrder(bst.root);

	}

	public static BST getLoadedBST() {
		BST bst = new BST();

		bst.add(50);
		bst.add(20);
		bst.add(15);
		bst.add(80);
		bst.add(60);
		bst.add(75);
		bst.add(70);
		bst.add(100);
		bst.add(5);
		bst.add(125);
		bst.add(18);
		bst.add(45);
		bst.add(12);
		bst.add(72);
		bst.add(68);
		bst.add(70);

		return bst;

	}

}
