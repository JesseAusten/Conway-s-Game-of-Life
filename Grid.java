import java.util.Iterator;
import java.util.Stack;

public class Grid{

	private int length;			// Length of one side of the grid.
	private int size;			// Total number of cells.
	private boolean[][] grid;	// Holds whether each cell is alive (true) or dead (false).
	
	private Stack<Coordinate> nextAlive;	// Holds the cells that have changed from dead to alive.
	private Stack<Coordinate> nextDead;		// Holds the cells that have changed from alive to dead.
	
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
	
	public int countAlive() {
		int count = 0;
		for (int row = 0; row < length; row++)
			for (int col = 0; col < length; col++)
				if (grid[row][col])
					count++;
		return count;
	}
	
	// Advances the grid to a new cycle of dead/alive cells.
	public void grow() {
		nextAlive = new Stack<Coordinate>();				// Clear the stacks for the next generation.
		nextDead = new Stack<Coordinate>();
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
				if (count == 2 || count == 3) 			// and it has exactly 2 or 3 neighbors,
					alive[c.row()][c.col()] = true;		// then the cell is still alive.
				else
					nextDead.push(new Coordinate(c));	// Otherwise, the cell dies.
			}
			else										// If the current cell is dead,
				if (count == 3)	{						// and has exactly 3 live neighbors,
					nextAlive.push(new Coordinate(c));	// then the cell becomes alive.
					alive[c.row()][c.col()] = true;
				}
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

	
	
	
	
	
	
	
	
	public Iterator<Coordinate> iterator(boolean isAlive) {
		return new GridIterator(isAlive);
	}
	
	// Iterates over one of the two stacks holding the cells that have changed from
	// either dead->alive or alive->dead. Any cells that remained the same from the
	// last generation are not in the stacks.
	private class GridIterator implements Iterator<Coordinate> {

		private boolean isAlive;	// If this is true, the iterator will use the nextAlive stack.
									// Otherwise, it will use the nextDead stack.
		
		public GridIterator(boolean isAlive) {
			this.isAlive = isAlive;
		}
		
		// Returns true if there is an element in the given stack.
		public boolean hasNext() {
			if (isAlive)
				return (!nextAlive.isEmpty());
			return (!nextDead.isEmpty());
		}

		// Returns the next coordinate of the given stack.
		public Coordinate next() {
			if (isAlive)
				return nextAlive.pop();
			else
				return nextDead.pop();
		}
	}
}
