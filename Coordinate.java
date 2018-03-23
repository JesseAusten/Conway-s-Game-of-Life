// Coordinate contains a row and column
public class Coordinate {
	private int row;
	private int col;
	
	public Coordinate(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	// Copies another coordinate to this one.
	public Coordinate(Coordinate c) {
		row = c.row();
		col = c.col();
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public int row() {
		return row;
	}
	
	public int col() {
		return col;
	}
	
	// Format: (row, col)
	public String toString() {
		return "(" + row + ", " + col + ")"; 
	}
}
