package patterns;

import java.util.ArrayList;

public class Glider implements Pattern {
	
	private final static String name = "glider";
	private final static ArrayList<Coordinate> coords = new ArrayList<Coordinate>() {
		{
			add(new Coordinate(0, 1));
			add(new Coordinate(1, 2));
			add(new Coordinate(2, 0));
			add(new Coordinate(2, 1));
			add(new Coordinate(2, 2));
		}
	};
	private final static int height = 3;
	private final static int width = 3;
	
	public Glider() {};
	
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
