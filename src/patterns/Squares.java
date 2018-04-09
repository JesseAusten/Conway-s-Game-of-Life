package patterns;

import java.util.ArrayList;

public class Squares implements Pattern {
	
	private final static String name = "squares";
	private final static ArrayList<Coordinate> coords = new ArrayList<Coordinate>() {
		{
			add(new Coordinate(0, 2));
			add(new Coordinate(0, 3));
			add(new Coordinate(0, 4));
			add(new Coordinate(1, 1));
			add(new Coordinate(1, 5));
			add(new Coordinate(2, 0));
			add(new Coordinate(2, 2));
			add(new Coordinate(2, 3));
			add(new Coordinate(2, 4));
			add(new Coordinate(2, 6));
			add(new Coordinate(3, 1));
			add(new Coordinate(3, 5));
			add(new Coordinate(4, 2));
			add(new Coordinate(4, 3));
			add(new Coordinate(4, 4));
		}
	};
	private final static int height = 5;
	private final static int width = 7;
	
	public Squares() {};
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Coordinate> getCoords() {
		return coords;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
}
