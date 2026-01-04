package com.farbig.dsa;

public class BST {

	Node root;

	Node successor;

	BST() {
		root = null;
	}

	public void add(int value) {

		root = add(root, value);

	}

	private Node add(Node root, int value) {

		if (root == null) {
			root = new Node(value);
		} else if (root.data > value) {
			root.leftNode = add(root.leftNode, value);
		} else if (root.data < value) {
			root.rightNode = add(root.rightNode, value);
		}

		return root;
	}

	public void getSuccessorNode(int givenValue) {

		getSuccessor(root, givenValue);

		System.out.println(successor.data);
		
		System.out.println(successor);

	}

	private void getSuccessor(Node currentNode, int givenValue) {

		if (currentNode != null) {

			if (currentNode.data > givenValue) {
				successor = currentNode;
				getSuccessor(currentNode.leftNode, givenValue);
			} else {
				getSuccessor(currentNode.rightNode, givenValue);
			}
		}

	}
	
	public void getPreOrder(Node root)
	{
		if(root==null) return;
		
		System.out.print(root.data+" ");
		
		getPreOrder(root.leftNode);
		
		getPreOrder(root.rightNode);
		
	}

	public static void main(String ar[]) {

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

		System.out.println("added");

		bst.getSuccessorNode(49);
		
		bst.getPreOrder(bst.root);

	}

}
