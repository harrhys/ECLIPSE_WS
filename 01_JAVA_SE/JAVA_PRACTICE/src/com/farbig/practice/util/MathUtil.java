package com.farbig.practice.util;

public class MathUtil {

	// Gives you binary representation of a number in array
	public static int[] binaryArray(int number, int arrayLength) {

		int[] binArray = new int[arrayLength];

		for (int i = arrayLength - 1; number > 0; i--) {
			binArray[i] = number % 2;
			number = number / 2;
		}

		return binArray;
	}

	// Gives you binary representation of a number in array
	public static int[] binarySubSet(int number, int[] input) {

		int[] subArray = new int[Integer.bitCount(number)];

		for (int i = 0, c = 0; number > 0; i++) {

			if (number % 2 == 1) {
				subArray[c++] = input[i];
			}
			number = number / 2;
		}

		return subArray;
	}

}
