package patterns;

import java.util.ArrayList;

public interface Pattern {
	public String getName();
	public ArrayList<Coordinate> getCoords();
	public int getHeight();
	public int getWidth();
}
