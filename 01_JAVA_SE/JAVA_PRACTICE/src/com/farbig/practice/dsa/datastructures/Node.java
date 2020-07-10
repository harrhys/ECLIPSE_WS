package com.farbig.practice.dsa.datastructures;

public class Node {

	int data;
	Node left;
	Node right;

	public Node(int value) {
		left = right = null;
		this.data = value;
	}

	public String toString() {

		return data + " [" + left.data + "," + right.data + "]";
	}

	/*
	 * public String toString() { StringBuffer buffer = toString(this, new
	 * StringBuffer());
	 * 
	 * return buffer.toString(); }
	 * 
	 * private StringBuffer toString(Node root, StringBuffer buffer) {
	 * 
	 * if (root == null) return null;
	 * 
	 * toString(root.left, buffer); buffer.append(root.data + " ");
	 * toString(root.right, buffer);
	 * 
	 * return buffer; }
	 */

}
