package com.farbig.dsa;

public class Node {

	int data;
	Node leftNode;
	Node rightNode;

	public Node(int value) {
		leftNode = rightNode = null;
		this.data = value;
	}

	public String toString() {
		StringBuffer buffer = toString(this, new StringBuffer());

		return buffer.toString();
	}

	private StringBuffer toString(Node root, StringBuffer buffer) {
		
		if (root == null)
			return null;

		toString(root.leftNode, buffer);
		buffer.append(root.data+" "); 
		toString(root.rightNode, buffer);

		return buffer;
	}

}
