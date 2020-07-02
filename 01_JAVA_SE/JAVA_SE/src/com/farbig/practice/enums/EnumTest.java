package com.farbig.practice.enums;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class EnumTest {

	public static void main(String arg[]) {
		
		
		Direction east = Direction.EAST;
		System.out.println(east);
		System.out.println(Direction.EAST);
		System.out.println(Direction.EAST.name());
		System.out.println(Direction.EAST.toString());
		System.out.println(Direction.EAST.getClass());
		System.out.println(east.getClass());
		System.out.println(Direction.EAST.ordinal()); // 0

		System.out.println(Direction.NORTH.ordinal()); // 2
		Direction west = Direction.valueOf("WEST");
		System.out.println(west);
		System.out.println(Direction.EAST.name().equals("EAST"));

		Direction[] directions = Direction.values();

		for (Direction d : directions) {
			System.out.println(d);
		}

		System.out.println(Direction.NORTH.getAngle());

		Direction eastNew = Direction.valueOf("EAST");

		System.out.println(east == eastNew); // true
		System.out.println(east.equals(eastNew)); // true

		Direction.NORTH.printDirection(); // You are moving in NORTH direction

		Set<Direction> enumSet = EnumSet.of(Direction.EAST, Direction.WEST,
				Direction.NORTH, Direction.SOUTH);

		for (Direction dir : enumSet) {
			System.out.println(dir);
		}

		Map enumMap = new EnumMap(Direction.class);

		// Populate the Map
		enumMap.put(Direction.EAST, Direction.EAST.getAngle());
		enumMap.put(Direction.WEST, Direction.WEST.getAngle());
		enumMap.put(Direction.NORTH, Direction.NORTH.getAngle());
		enumMap.put(Direction.SOUTH, Direction.SOUTH.getAngle());
		System.out.println(enumMap);
	}

}

enum Direction {

	EAST(0), WEST(180), NORTH(90), SOUTH(270);

	// constructor
	private Direction(final int angle) {
		this.angle = angle;
	}

	// internal state
	private int angle;

	public int getAngle() {
		return angle;
	}

	protected String printDirection() {
		String message = "You are moving in " + this + " direction";
		System.out.println(message);
		return message;
	}

}
