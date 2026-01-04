package com.farbig.dsa;

public class TreeDisplay {

	public static void main(String ar[]) {
		
		int rows = 5;
		String[] indents = new String[rows];
		String[] spaces = new String[rows];

		for (int i = 0; i < rows; i++) {

			indents[i] = getString((int) Math.pow(2, rows - 1 - i) - 1);
			spaces[i] = getString((int) Math.pow(2, rows - i) - 1);
		}
		
		System.out.println();

	}

	public static String getString(int len) {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < len; i++) {
			s.append(" ");
		}
		return s.toString();
	}

}
