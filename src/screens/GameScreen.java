package screens;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import core.Game;

public class GameScreen extends JFrame{
	private static final long serialVersionUID = -3704828714404334924L;

	Game game;
	JButton[][] grid;
	JButton reset, menu;
	JPanel panel;
	
	public GameScreen(int rows, int columns, int bombs){
		game = new Game(rows, columns, bombs);
		
		createMenuButton();
		createResetButton();
		createPanel(rows, columns);
		createGrid(rows, columns);
		
		setTitle("MineSweeper");
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 700);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void createMenuButton() {
		menu = new JButton();
		menu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new MenuScreen();
				close();
			}
		});
		menu.setText("MENU");
		menu.setSize(new Dimension(50, 50));
		menu.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(menu);
	}

	private void createResetButton(){
		reset = new JButton();
		reset.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.reset();
				updateGrid();
			}
		});
		reset.setText("RESET");
		reset.setSize(new Dimension(50, 50));
		reset.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(reset);
	}
	
	private void createPanel(int rows, int columns) {
		panel = new JPanel();
		panel.setLayout(new GridLayout(rows, columns));
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.setBackground(Color.RED);
		add(panel);
	}

	private void createGrid(int rows, int columns) {
		grid = new JButton[rows][columns];
		for(int col = 0; col < columns;col++){
			for(int row = 0; row < rows;row++){
				createButton(row, col);
			}
		}
	}
	
	private void createButton(int row, int col) {
		grid[row][col] = new JButton();
		JButton button = grid[row][col];
		button.addMouseListener(new MouseListener(){
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					gridButtonClick(row, col);
				}else if(e.getButton() == MouseEvent.BUTTON3){
					game.select(col, row, true);
					updateGrid();
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
		panel.add(button);
	}

	private void gridButtonClick(int r, int c){
		
		int status = game.select(c, r, false);
		
		updateGrid();
		
		if(status == Game.GAMEOVER){
			JOptionPane.showMessageDialog(null, "GAME OVER!");
			game.reset();
			updateGrid();
		}
		else if(status == Game.WIN){
			JOptionPane.showMessageDialog(null, "YOU WON!");
			game.reset();
			updateGrid();
		}
		
	}

	private void updateGrid() {
		for(int i = 0; i < game.getBoard().length;i++){
			for(int j = 0; j < game.getBoard()[i].length ;j++){
				if(game.getBoard()[i][j].isVisible()){
					grid[i][j].setText(game.getBoard()[i][j].toString());
					grid[i][j].setEnabled(false);					
				}else{
					grid[i][j].setEnabled(true);
					grid[i][j].setText("");
					if(game.getBoard()[i][j].isFlagged()) 
						grid[i][j].setText("F");
				}
			}
		}
	}
	
	public void close(){
		setVisible(false);
		dispose();
	}
	
}








