import java.util.Iterator;
import java.util.Stack;

public class Grid{

	private int length;			// Length of one side of the grid.
	private int size;			// Total number of cells.
	private boolean[][] grid;	// Holds whether each cell is alive (true) or dead (false).
	
	public Grid(int length) {
		this.length = length;
		size = length * length;
		grid = new boolean[length][length];
		
		// Initializes the grid to be dead.
		for (int row = 0; row < length; row++)
			for (int col = 0; col < length; col++)
				grid[row][col] = false;
	}
	
	// Set a cell to be dead or alive.
	public void set(int row, int col, boolean alive) {
		grid[row][col] = alive;
	}
	
	// Returns the status of a cell.
	public boolean get(int row, int col) {
		return grid[row][col];
	}
	
	// Returns true if there is at least one live cell in the grid.
	public boolean isGridAlive() {
		for (int row = 0; row < length; row++)
			for (int col = 0; col < length; col++)
				if (grid[row][col])
					return true;
		return false;
	}
	
	// Advances the grid to a new cycle of dead/alive cells.
	public void grow() {
		boolean[][] checked = new boolean[length][length]; 	// Indicates if a cell has already been pushed to the stack.
		boolean[][] alive = new boolean[length][length]; 	// Indicates whether or not a cell will be alive after the current cycle.
		Stack<Coordinate> stack = new Stack<Coordinate>(); 	// This stack holds all the squares that need to be checked.
		
		Stack<Coordinate> st = new Stack<Coordinate>(); 	// This stack temporarily holds the coordinates of the live cell.
		for (int row = 0; row < length; row++)				// By default, ALL new cells will be dead until changed to alive.
			for (int col = 0; col < length; col++) {
				checked[row][col] = false;
				alive[row][col] = false;
				if (grid[row][col] == true)
					st.push(new Coordinate(row, col));
			}
			
		while (!st.empty()) {
			Coordinate c = st.pop();	 // Pop a live cell off the stack.
			
			// Check if the neighbors are valid squares. 
			// If they are, and haven't been checked, push them onto the stack to be analyzed later.
			int row, col;
			for (int i = -1; i <= 1; i++)
				for (int j = -1; j <= 1; j++) {
					row = c.row() + i;
					col = c.col() + j;
					if (isValid(row, col) && !checked[row][col]) {
						stack.push(new Coordinate(row, col));
						checked[row][col] = true;
					}
				}
		}
		
		// Analyze each cell on the stack and determine whether it lives or dies for the next cycle.
		// Each cell on this stack is unique, and is either alive or a neighbor to an alive cell.
		int count;
		Coordinate c;
		while (!stack.empty()) {
			c = stack.pop();
			count = countAliveNeighbors(c.row(), c.col());
			
			if (grid[c.row()][c.col()]) {				// If the current cell is alive,
				if (count == 2 || count == 3)			// and it has exactly 2 or 3 neighbors,
					alive[c.row()][c.col()] = true;		// then the cell becomes alive.
			}
			else										// If the current cell is dead,
				if (count == 3)							// and has exactly 3 live neighbors,
					alive[c.row()][c.col()] = true;		// then the cell becomes alive.
		}
		
		// Finally, update the current grid to their new dead/alive states.
		for (int row = 0; row < length; row++)
			for (int col = 0; col < length; col++)
				grid[row][col] = alive[row][col];
	}
	
	// Returns true if the cell exists on the grid, bounded by the length.
	private boolean isValid(int row, int col) {
		return (row >= 0 && row < length && col >= 0 && col < length);
	}
	
	// Counts the number of neighbors that are alive.
	private int countAliveNeighbors(int row, int col) {
		int count = 0;
		int r, c;
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++) {
				r = row + i;
				c = col + j;
				if (isValid(r, c))
					if (grid[r][c] == true)
							count++;
			}
		if (grid[row][col] == true)	// Don't count the current cell itself.
			count--;
		
		return count;
	}

	
	
	
	
	
	
	
	/*
	public Iterator<Coordinate> iterator() {
		return new GridIterator();
	}
	
	// Iterates over the grid, returning the next live cell's coordinate.
	// If there is not a live cell after the current one, hasNext will return false;
	private class GridIterator implements Iterator<Coordinate> {

		private int nextRow;	// Row of the next live cell.
		private int nextCol;	// Column of the next live cell.
		
		// Initializes nextRow and nextCol to the first live cell.
		// If there is none, sets both to -1.
		public GridIterator() {
			nextRow = -1;
			nextCol = -1;
			boolean found = false;
			for (int row = 0; row < length && !found; row++)
				for (int col = 0; col < length; col++)
					if (grid[row][col]) {
						nextRow = row;
						nextCol = col;
						found = true;
						break;
					}
		}
		
		// Returns true if there is another live cell after the current one.
		public boolean hasNext() {
			return (nextRow != -1);
		}

		// Returns the coordinates of the current live cell.
		// If there isn't another one after the current, nextRow is set to -1.
		// Otherwise, nextRow and nextCol are set to the next live cell.
		public Coordinate next() {
			Coordinate c = new Coordinate(nextRow, nextCol);
			nextRow = -1;
			for (int row = nextRow-1; row < length; row++)
				for (int col = nextCol-1; col < length; col++)
					if (grid[row][col]) {
						nextRow = row;
						nextCol = col;
					}
			return c;
		}
	}*/
}
