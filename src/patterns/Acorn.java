package patterns;

import java.util.ArrayList;

public class Acorn implements Pattern {
	
	private static String name = "acorn";
	private static ArrayList<Coordinate> coords = new ArrayList<Coordinate>() {
		{
			add(new Coordinate(0, 1));
			add(new Coordinate(1, 3));
			add(new Coordinate(2, 0));
			add(new Coordinate(2, 1));
			add(new Coordinate(2, 4));
			add(new Coordinate(2, 5));
			add(new Coordinate(2, 6));
		}
	};
	private static int height = 3;
	private static int width = 7;
	
	public Acorn() {};
	
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
