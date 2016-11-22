package core;

import java.util.Random;

public class Game {
	private Square[][] board;

	public Game(int width, int height, int bombs) {
		
		board = new Square[height][width];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = new Square(); 
			}
		}

		spawnBombs(bombs);
		calculateNeighbors();

	}

	private void spawnBombs(int totalBombs) {
		int rows = board[0].length;
		int columns = board.length;
		Boolean[] bombs = new Boolean[rows * columns];
		for (int i = 0; i < bombs.length; i++) {
			bombs[i] = i < totalBombs;
		}
		Random random = new Random();
		for (int i = bombs.length - 1; i > 0; i--) {
			int rand = random.nextInt(i + 1);
			boolean temp = bombs[i];
			bombs[i] = bombs[rand];
			bombs[rand] = temp;
		}
		
		for (int i = 0; i < bombs.length; i++) {
			if (bombs[i])
				board[row(i)][col(i)].setBomb(true);
		}

	}

	private void calculateNeighbors() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (!board[i][j].isBomb())
					continue;
				for (int x = -1; x <= 1; x++)
					for (int y = -1; y <= 1; y++)
						if (!isOutOfBounds(i + x, j + y) && !board[i + x][j + y].isBomb())
							board[i + x][j + y].incrementNeighbors();
			}
		}
	}

	public void makeVisible() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j].setFlag(false);
				board[i][j].setVisible(true);
			}
		}
	}

	public void show(int x, int y) {
		if (isOutOfBounds(y, x) || board[y][x].isVisible())
			return;
		board[y][x].setVisible(true);
		if (board[y][x].getNeighbors() == 0) {
			show(x - 1, y - 1);
			show(x - 1, y);
			show(x - 1, y + 1);
			show(x, y - 1);
			show(x, y + 1);
			show(x + 1, y - 1);
			show(x + 1, y);
			show(x + 1, y + 1);
		}
	}

	public boolean win() {
		for(int i=0;i<board.length;i++){
			for(int j = 0;j<board[i].length;j++){
				Square current = board[i][j];
				if(!current.isVisible() && !current.isBomb()){
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isOutOfBounds(int r, int c) {
		return r < 0 || r >= board.length || c < 0 || c >= board[0].length;
	}

	private int row(int n) {
		return n / board[0].length;
	}

	private int col(int n) {
		return n % board[0].length;
	}

	public Square[][] getBoard() {
		return board;
	}


}
