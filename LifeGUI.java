import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LifeGUI extends JFrame implements ActionListener {

	private JButton[][] buttons;
	private JButton start;
	private JButton reset;
	
	private static Grid grid;
	private boolean go = false;
	private boolean pause = true;
	
	private int LENGTH = 100;
	private static int TIME = 50;
	
	public LifeGUI() {
		grid = new Grid(LENGTH);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(LENGTH, LENGTH));
		panel.setPreferredSize(new Dimension(700, 700));
		
		buttons = new JButton[LENGTH][LENGTH];
		for (int row = 0; row < LENGTH; row++)
			for (int col = 0; col < LENGTH; col++) {
				buttons[row][col] = new JButton();
				buttons[row][col].setBackground(Color.WHITE);
				buttons[row][col].setActionCommand(row + " " + col);
				buttons[row][col].addActionListener(this);
				panel.add(buttons[row][col]);
		}
		mainPanel.add(panel);
		
		panel = new JPanel(new FlowLayout());
		start = new JButton("Start Life");
		start.addActionListener(this);
		start.setActionCommand("start");
		panel.add(start);
		
		reset = new JButton("Reset Life");
		reset.addActionListener(this);
		reset.setActionCommand("reset");
		panel.add(reset);
		mainPanel.add(panel, BorderLayout.SOUTH);
		
		add(mainPanel);
		
		setTitle("Conway's Game of Life");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		pack();
		setVisible(true);
	}

	public void setColored(int row, int col, boolean b) {
		if (b)
			buttons[row][col].setBackground(Color.BLACK);
		else
			buttons[row][col].setBackground(Color.WHITE);
	}
	
	public void setColor(int row, int col, Color color) {
		buttons[row][col].setBackground(color);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "start") {
			if (!go) {
				start.setText("Pause Life");
				go = true;
				pause = false;
			}
			else {
				start.setText("Start Life");
				go = false;
				pause = true;
			}
			return;
		}
		else if (e.getActionCommand() == "reset") {
			start.setText("Start Life");
			go = false;
			pause = true;
			resetGame();
		}
		else {
			String[] commands = e.getActionCommand().split(" ");
			int row = Integer.parseInt(commands[0]);
			int col = Integer.parseInt(commands[1]);
			if (!grid.get(row, col)) {
				setColor(row, col, Color.BLACK);
				grid.set(row, col, true);
			}
			else {
				setColor(row, col, Color.WHITE);
				grid.set(row, col, false);
			}
		}
	}
	
	private void resetGame() {
		grid = new Grid(LENGTH);
		for (int row = 0; row < LENGTH; row++)
			for (int col = 0; col < LENGTH; col++)
				buttons[row][col].setBackground(Color.WHITE);
	}
	public boolean isGridAlive() {
		return grid.isGridAlive();
	}
	
	public boolean isPaused() {
		return pause;
	}
	
	public void grow() {
		if (!go)
			return;
		
		grid.grow();
		Iterator<Coordinate> iterAlive = grid.iterator(true);
		Coordinate c;
		while (iterAlive.hasNext()) {
			c = iterAlive.next();
			//System.out.println("Cell is now alive: (" + c.row() + ", " + c.col() + ").");
			setColored(c.row(), c.col(), true);
		}
		
		Iterator<Coordinate> iterDead = grid.iterator(false);
		while (iterDead.hasNext()) {
			c = iterDead.next();
			//System.out.println("Cell is now dead: (" + c.row() + ", " + c.col() + ").");
			setColored(c.row(), c.col(), false);
		}
	}
	
	public static void main(String[] args) {
		LifeGUI life = new LifeGUI();
		
		Thread gameThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try { Thread.sleep(TIME); } catch (InterruptedException e) { System.out.println("Error sleeping: " + e.getMessage()); }
					if (!life.isPaused())
						life.grow();
					
				}
			}
		});

		gameThread.start();
	}
}
