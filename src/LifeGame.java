import java.util.Iterator;

// This is the main file for the Game of Life.
public class LifeGame {
	
	private LifeGUI lifeGUI;
	private Grid grid;
	private boolean go = false;
	private boolean pause = true;
	
	private int length;
	private int interval;
	
	// Create the GUI and the grid.
	public LifeGame(int length, int interval) {
		lifeGUI = new LifeGUI(length, this);
		grid = new Grid(length);
		this.length = length;
		this.interval = interval;
	}
	
	// Resets the game by creating a new grid and setting every cell to dead.
	public void resetGame() {
		grid = new Grid(length);
		lifeGUI.resetBoard();
		go = false;
		pause = true;
	}
	
	// Returns true if there is a live cell in the grid.
	public boolean isGridAlive() {
		return grid.isGridAlive();
	}
	
	// Pause or unpause the game.
	public void pause() {
		pause = !pause;
		go = !go;
	}
	
	// Returns true if the game is paused.
	public boolean isPaused() {
		return pause;
	}
	
	// Set the cell to be dead or alive.
	public void setCell(int row, int col, boolean b) {
		grid.set(row, col, b);
	}
	
	// Get the state of a cell.
	public boolean getCell(int row, int col) {
		return grid.get(row, col);
	}
	
	// Set the time between growth cycles.
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	// Get the time between each growth cycle.
	public int getInterval() {
		return interval;
	}
	
	// Advances the cycle by one turn.
	public void grow() {
		if (!go)		// If the game is paused, do nothing.
			return;
		
		grid.grow();	// This runs the algorithm inside Grid. The data is stored for what cells need to be changed.
		
		Iterator<Coordinate> iterAlive = grid.iterator(true);	// Iterate through the cells that are dead->alive.
		Coordinate c;
		while (iterAlive.hasNext()) {
			c = iterAlive.next();
			lifeGUI.setColor(c.row(), c.col(), 2);
		}
		
		Iterator<Coordinate> iterDead = grid.iterator(false);	// Iterate through the cells that are alive->dead.
		while (iterDead.hasNext()) {
			c = iterDead.next();
			lifeGUI.setColor(c.row(), c.col(), 1);
		}
	}
	
	// Create the game with the given size and speed, and start the game in a separate thread.
	public static void main(String[] args) {
		LifeGame game = new LifeGame(50, 216);	// Creates the game object and the GUI.
		
		Thread gameThread = new Thread(new Runnable() {
			@Override public void run() {
				while (true) {
					try { Thread.sleep(game.getInterval()); } catch (InterruptedException e) { System.out.println("Error sleeping: " + e.getMessage()); }
					if (!game.isPaused()) game.grow(); } } 
		});

		gameThread.start();	// Starts the game.
	}
}
