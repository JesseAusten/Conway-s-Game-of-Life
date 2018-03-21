import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LifeGUI extends JFrame implements ActionListener {

	private JButton[][] buttons;
	private JButton start;
	
	private static Grid grid;
	private boolean go = false;
	private boolean pause = false;
	
	private int LENGTH = 40;
	private static int TIME = 10;
	
	public LifeGUI() {
		grid = new Grid(LENGTH);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(LENGTH, LENGTH));
		setSize(LENGTH * 30, LENGTH * 30);
		
		buttons = new JButton[LENGTH][LENGTH];
		for (int row = 0; row < LENGTH; row++)
			for (int col = 0; col < LENGTH; col++) {
				buttons[row][col] = new JButton();
				buttons[row][col].setBackground(Color.WHITE);
				buttons[row][col].setActionCommand(row + " " + col);
				buttons[row][col].addActionListener(this);
				panel.add(buttons[row][col]);
		}
		add(panel);
		
		start = new JButton("Start Life");
		start.addActionListener(this);
		start.setActionCommand("start");
		add(start, BorderLayout.SOUTH);
		
		setTitle("Conway's Game of Life");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		for (int row = 0; row < LENGTH; row++)
			for (int col = 0; col < LENGTH; col++)
				setColored(row, col, grid.get(row, col));
	}
	
	public static void main(String[] args) {
		LifeGUI life = new LifeGUI();
		
		while (!life.isGridAlive())
			try { Thread.sleep(TIME); } catch (InterruptedException e) { System.out.println("Error sleeping."); }
		
		while (true) {
			try { Thread.sleep(TIME); } catch (InterruptedException e) { System.out.println("Error sleeping."); }
			if (!life.isPaused())
				life.grow();
			
			/*Iterator<Coordinate> iter = grid.iterator();
			Coordinate c;
			while (iter.hasNext()) {
				c = iter.next();
				System.out.println("Cell is alive: (" + c.row() + ", " + c.col() + ").");
				life.setEnabled(c.row(), c.col(), true);
			}*/
		}
	}
}
