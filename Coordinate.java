
public class Coordinate {
	private int row;
	private int col;
	
	public Coordinate(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
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
	
	public String toString() {
		return "(" + row + ", " + col + ")"; 
	}
}
