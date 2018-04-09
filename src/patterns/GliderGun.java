package patterns;

import java.util.ArrayList;

public class GliderGun implements Pattern {
	
	private static String name = "glidergun";
	private static ArrayList<Coordinate> coords = new ArrayList<Coordinate>() {
		{
			add(new Coordinate(0, 24));
			add(new Coordinate(1, 22));
			add(new Coordinate(1, 24));
			add(new Coordinate(2, 12));
			add(new Coordinate(2, 13));
			add(new Coordinate(2, 20));
			add(new Coordinate(2, 21));
			add(new Coordinate(2, 34));
			add(new Coordinate(2, 35));
			add(new Coordinate(3, 11));
			add(new Coordinate(3, 15));
			add(new Coordinate(3, 20));
			add(new Coordinate(3, 21));
			add(new Coordinate(3, 34));
			add(new Coordinate(3, 35));
			add(new Coordinate(4, 0));
			add(new Coordinate(4, 1));
			add(new Coordinate(4, 10));
			add(new Coordinate(4, 16));
			add(new Coordinate(4, 20));
			add(new Coordinate(4, 21));
			add(new Coordinate(5, 0));
			add(new Coordinate(5, 1));
			add(new Coordinate(5, 10));
			add(new Coordinate(5, 14));
			add(new Coordinate(5, 16));
			add(new Coordinate(5, 17));
			add(new Coordinate(5, 22));
			add(new Coordinate(5, 24));
			add(new Coordinate(6, 10));
			add(new Coordinate(6, 16));
			add(new Coordinate(6, 24));
			add(new Coordinate(7, 11));
			add(new Coordinate(7, 15));
			add(new Coordinate(8, 12));
			add(new Coordinate(8, 13));
		}
	};
	private static int height = 9;
	private static int width = 36;
	
	public GliderGun() {};
	
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
