import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import patterns.*;

// LifeGUI is the GUI for the game. It contains the instance of LifeGame so it can update the games grid when needed.
public class LifeGUI extends JFrame implements ActionListener, ChangeListener, MouseListener {

	private static final long serialVersionUID = -165936261958597219L;
	private JButton[][] buttons;
	private JButton start;
	private JButton reset;
	private JButton step;
	
	private LifeGame game;
	private Pattern currentPattern = null;
	private int length;
	private boolean isMouseDown = false;
	
	// Set up the size of the grid, and create the GUI.
	public LifeGUI(int length, LifeGame game) {
		this.length = length;
		this.game = game;
		
		JPanel mainPanel = new JPanel();						// This is the main panel added to the JFrame.
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(getButtonPanel());						// Add the button panel containing all the cells.
		mainPanel.add(getBottomMenuPanel(), BorderLayout.SOUTH);// Add the menu at the bottom.
		mainPanel.add(getRightMenuPanel(), BorderLayout.EAST);	// Add the pattern menu to the right.
		add(mainPanel);											// Attach the main panel to the JFrame.
		
		setTitle("Conway's Game of Life");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		pack();
		setVisible(true);
	}
	
	// Sets the color of the given cell. 1 is white, 2 is black.
	public void setColor(int row, int col, int color) {
		if (color == 1)
			buttons[row][col].setBackground(Color.WHITE);
		else if (color == 2)
			buttons[row][col].setBackground(Color.BLACK);
	}
	
	// Sets the color of the given cell.
	public void setColor(int row, int col, Color color) {
		buttons[row][col].setBackground(color);
	}
	
	// Sets all of the buttons to white.
	public void resetBoard() {
		for (int row = 0; row < length; row++)
			for (int col = 0; col < length; col++)
				buttons[row][col].setBackground(Color.WHITE);
	}
	
	// Sets the cell to swap from dead<->alive.
	private void setCell(String cell) {
		String[] commands = cell.split(" ");			// Get the command for the button "row col"
		int row = Integer.parseInt(commands[0]);
		int col = Integer.parseInt(commands[1]);
		

		// If the current selected pattern is not none (null), place it on the board.
		if (currentPattern != null) {
			for (int r = row; r < (row + currentPattern.getHeight()); r++)
				for (int c = col; c < (col + currentPattern.getWidth()); c++)
					if (game.isValid(r, c)) {			// Clear the area that the pattern will fill.
						setColor(r, c, Color.WHITE);		
						game.setCell(r, c, false);
					}
			
			for (int i = 0; i < currentPattern.getCoords().size(); i++) {
				int r = row + currentPattern.getCoords().get(i).row();
				int c = col + currentPattern.getCoords().get(i).col();
				if (game.isValid(r, c)) {				// Draw the pattern onto the selected area.
					setColor(r, c, Color.BLACK);		
					game.setCell(r, c, true);
				}
			}
		}
		// Otherwise no pattern is selected, so switch the current cell.
		else {
			if (!game.getCell(row, col)) {				// If the cell is dead, switch it to alive.
				setColor(row, col, Color.BLACK);		// Set the color on the board,
				game.setCell(row, col, true);			// and set the cell in the game's grid.
			}
			else {										// Otherwise, switch the cell from alive to dead.
				setColor(row, col, Color.WHITE);		
				game.setCell(row, col, false);
			}
		}
	}
	
	// Sets the cell to be alive or dead based on the passed in boolean.
	private void setCell(String cell, boolean alive) {
		String[] commands = cell.split(" ");		// Get the command for the button "row col"
		int row = Integer.parseInt(commands[0]);	
		int col = Integer.parseInt(commands[1]);
		setColor(row, col, Color.BLACK);			// Set the color on the board,
		game.setCell(row, col, alive);				// and set the cell in the game's grid.
	}
	
	// Create and return the main button panel, containing all of the buttons in the grid.
	// Each button has an actionListener and a mouseListener attached.
	private JPanel getButtonPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(length, length));
		panel.setPreferredSize(new Dimension(700, 700));
		
		// Loop through each button in the board.
		buttons = new JButton[length][length];
		for (int row = 0; row < length; row++)
			for (int col = 0; col < length; col++) {
				buttons[row][col] = new JButton();
				buttons[row][col].setBackground(Color.WHITE);		// Initialize them to white (dead).
				buttons[row][col].setActionCommand(row + " " + col);// Set's the action command to "row col".
				buttons[row][col].addMouseListener(this);			// and as the mouseListener.
				panel.add(buttons[row][col]);
		}
		return panel;
	}
	
	// This returns the bottom panel for the game, including the start/pause and reset buttons and the speed slider.
	private JPanel getBottomMenuPanel() {
		JPanel panel;
		
		// Start/Pause button. Action command: "start"
		panel = new JPanel(new FlowLayout());
		start = new JButton("Start Life");
		start.addActionListener(this);
		start.setActionCommand("start");
		
		// Reset button. Action command: "reset"
		reset = new JButton("Reset Life");
		reset.addActionListener(this);
		reset.setActionCommand("reset");
		
		// Step button. Action command: "step"
		step = new JButton("Step");
		step.addActionListener(this);
		step.setActionCommand("step");
				
		// Speed slider. Name: "speed"
		JSlider slider = new JSlider(0, 100, 50);
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(10);
		slider.addChangeListener(this);
		slider.setPaintTicks(true);
		slider.setSnapToTicks(true);
		slider.setName("speed");

		// "Speed" label for the slider.
		JLabel label = new JLabel("Speed");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Add the components to the slider panel.
		JPanel slidePanel = new JPanel();
		slidePanel.setLayout(new BoxLayout(slidePanel, BoxLayout.Y_AXIS));
		slidePanel.add(label);
		slidePanel.add(slider);
		
		// Add components to the panel and return it.
		panel.add(start);
		panel.add(reset);
		panel.add(step);
		panel.add(slidePanel);
		return panel;
	}
	
	private JPanel getRightMenuPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		
		// Grid goes inside of the panel (rows, columns).
		JPanel grid = new JPanel(new GridLayout(18, 1));
		
		// Label goes inside of the panel, before grid.
		JLabel label = new JLabel("  Select a Pattern");
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		
		// No Pattern Selected button.
		JButton none = new JButton("None");
		none.setActionCommand("patternNone");
		none.addActionListener(this);
		
		// RPentomino pattern.
		JButton rpentomino = new JButton("R-Pentomino");
		rpentomino.setActionCommand("patternRPentomino");
		rpentomino.addActionListener(this);
		
		// Glider pattern.
		JButton glider = new JButton("Glider");
		glider.setActionCommand("patternGlider");
		glider.addActionListener(this);
		
		// Glider Gun pattern.
		JButton gliderGun = new JButton("Glider Gun");
		gliderGun.setActionCommand("patternGliderGun");
		gliderGun.addActionListener(this);
				
		// Acorn pattern.
		JButton acorn = new JButton("Acorn");
		acorn.setActionCommand("patternAcorn");
		acorn.addActionListener(this);
		
		// Glider Gun pattern.
		JButton diehard = new JButton("Diehard");
		diehard.setActionCommand("patternDiehard");
		diehard.addActionListener(this);

		// Diamond pattern.
		JButton diamond = new JButton("Diamond");
		diamond.setActionCommand("patternDiamond");
		diamond.addActionListener(this);
		
		// Diamond pattern.
		JButton squares = new JButton("Squares");
		squares.setActionCommand("patternSquares");
		squares.addActionListener(this);
				
		// Add the label and buttons to the panel.
		grid.add(none);
		grid.add(rpentomino);
		grid.add(glider);
		grid.add(gliderGun);
		grid.add(acorn);
		grid.add(diehard);		
		grid.add(diamond);	
		grid.add(squares);
		panel.add(label, BorderLayout.NORTH);
		panel.add(grid, BorderLayout.CENTER);
		return panel;
	}
	
	// This function runs when a button in the menu is pressed.
	// It will Pause/Resume, Restart, and switch a cell.
	// It also controls what pattern is selected.
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "start") {			// Start / Pause button pressed.
			if (game.isPaused())						// The game is paused, so when it starts, should be "Pause."
				start.setText("Pause Life");
			else 										// The game is not paused, so when it pauses, should be "Start."			
				start.setText("Start Life");
			game.pause();								// Start or pause the game.
			return;
		}
		else if (e.getActionCommand() == "reset") {		// Reset button pressed.
			start.setText("Start Life");
			game.resetGame();
			resetBoard();
		}
		else if (e.getActionCommand() == "step") {
			if (!game.isPaused()) {
				game.pause();	
				start.setText("Start Life");
			}
			game.pause();
			game.grow();
			game.pause();
		}
		else if (e.getActionCommand() == "patternNone")			// Set Pattern to null (none).
			currentPattern = null;
		else if (e.getActionCommand() == "patternRPentomino")	// Set Pattern to RPentomino.
			currentPattern = new RPentomino();
		else if (e.getActionCommand() == "patternGlider")		// Set Pattern to Glider.
			currentPattern = new Glider();
		else if (e.getActionCommand() == "patternGliderGun")	// Set Pattern to GliderGun.
			currentPattern = new GliderGun();
		else if (e.getActionCommand() == "patternAcorn")		// Set Pattern to Acorn.
			currentPattern = new Acorn();
		else if (e.getActionCommand() == "patternDiehard")		// Set Pattern to Diehard.
			currentPattern = new Diehard();
		else if (e.getActionCommand() == "patternDiamond")		// Set Pattern to Diamond.
			currentPattern = new Diamond();
		else if (e.getActionCommand() == "patternSquares")		// Set Pattern to Squares.
			currentPattern = new Squares();
	}

	// Sets the interval between cycles based on the JSlider.
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if (source.getValueIsAdjusting())			// Do nothing if the slider is in motion.
			return;
		int val = (int) 100 - source.getValue();
		game.setInterval(val * val / 15 + 50);	// Set the interval min:50 to max:716.
	}

	@Override
	public void mousePressed(MouseEvent e) {
		isMouseDown = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		isMouseDown = false;
	}
	
	// If the mouse is clicked on a button in the grid, it will change the button from dead<->alive.
	@Override
	public void mouseClicked(MouseEvent e) {
		JButton button = (JButton) e.getComponent();	// Get the button in the grid that was clicked.
		setCell(button.getActionCommand());				// Switch the cell from dead<->alive.
	}

	// If the mouse is pressed and it enters a cell, it will set that cell to alive.
	@Override
	public void mouseEntered(MouseEvent e) {
		if (isMouseDown) {
			JButton button = (JButton) e.getComponent();
			setCell(button.getActionCommand(), true);
		}
	}

	// If the mouse is down and it exits a cell, the the user is dragging the mouse,
	// and it sets the first cell to alive.
	@Override
	public void mouseExited(MouseEvent e) {
		if (isMouseDown) {
			JButton button = (JButton) e.getComponent();
			setCell(button.getActionCommand(), true);
		}
	}
}