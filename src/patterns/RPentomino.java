package patterns;

import java.util.ArrayList;

public class RPentomino implements Pattern {
	
	private static String name = "rpentomino";
	private static ArrayList<Coordinate> coords = new ArrayList<Coordinate>() {
		{
			add(new Coordinate(0, 1));
			add(new Coordinate(0, 2));
			add(new Coordinate(1, 0));
			add(new Coordinate(1, 1));
			add(new Coordinate(2, 1));
		}
	};
	private static int height = 3;
	private static int width = 3;
	
	public RPentomino() {};
	
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
