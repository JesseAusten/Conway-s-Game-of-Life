package patterns;

import java.util.ArrayList;

public class Diehard implements Pattern {
	
	private static String name = "diehard";
	private static ArrayList<Coordinate> coords = new ArrayList<Coordinate>() {
		{
			add(new Coordinate(0, 6));
			add(new Coordinate(1, 0));
			add(new Coordinate(1, 1));
			add(new Coordinate(2, 1));
			add(new Coordinate(2, 5));
			add(new Coordinate(2, 6));
			add(new Coordinate(2, 7));
		}
	};
	private static int height = 3;
	private static int width = 8;
	
	public Diehard() {};
	
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
