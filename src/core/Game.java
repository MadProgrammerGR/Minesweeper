package core;

import java.util.Random;

public class Game {
	private Square[][] board;
	public static final int GAMEOVER = 0;
	public static final int WIN = 1;
	public static final int NOTHING = 2;

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
		for (int i = 0; i < totalBombs; i++) {
			board[row(i)][col(i)].setBomb(true);
		}
		shuffle();
	}
	
	private void shuffle(){
		int rows = board.length;
		int columns = board[0].length;
		Random random = new Random();
		for (int i = rows*columns - 1; i > 0; i--) {
			int rand = random.nextInt(i + 1);
			//swap
			Square temp = board[row(i)][col(i)];
			board[row(i)][col(i)] = board[row(rand)][col(rand)];
			board[row(rand)][col(rand)] = temp;
		}
	}

	private void calculateNeighbors() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j].setNeighbors(0);
			}
		}
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

	public void setVisible(boolean v) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j].setFlag(false);
				board[i][j].setVisible(v);
			}
		}
	}

	public int select(int x, int y, boolean flag){
		if(flag){
			board[y][x].toggleFlag();
			return NOTHING;
		}
		
		if(board[y][x].isFlagged())
			return NOTHING;
		
		if(board[y][x].isBomb()){
			setVisible(true);
			return GAMEOVER;
		}
		
		show(x, y);
		
		if(win()){
			setVisible(true);
			return WIN;
		}
		
		return NOTHING;
	}
	
	private void show(int x, int y) {
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

	private boolean win() {
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
	
	public void reset(){
		shuffle();
		calculateNeighbors();
		setVisible(false);
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
