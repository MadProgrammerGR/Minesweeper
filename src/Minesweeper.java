import java.util.Arrays;
import java.util.Scanner;

import core.Game;

public class Minesweeper {
	static Game game;
	static Scanner input;
	
	public static void main(String[] args) {
		input = new Scanner(System.in);
		System.out.println("=== Minesweeper ===");
		System.out.print("Width: ");
		int width = readInt(500);
		
		System.out.print("Height: ");
		int height = readInt(500);
		
		System.out.print("Total bombs: ");
		int bombs = readInt(height*width);
		
		game = new Game(width, height, bombs);
		
		
		while(true){
			printBoard();			
			
			System.out.print("X: ");
			int column = input.nextInt();
			System.out.print("Y: ");
			int row = input.nextInt();
			System.out.println("flag?(yes/no) ");
			input.nextLine();
			String s = input.nextLine();
			
			//if u put a flag
			if(s.equals("yes")){
				game.getBoard()[row][column].toggleFlag();
				continue;
			}
			
			//cant dig on a flagged square
			if(game.getBoard()[row][column].isFlagged()) 
				continue;
			
			//if u touched bomb
			if(game.getBoard()[row][column].isBomb()){
				game.setVisible(true);
				printBoard();
				System.out.println("You lost!");
				break;
			}
			
			//show cells from selected square
			game.show(column, row);
			
			//if u found all bombs ( all non-bombs squares are visible )
			if(game.win()){
				printBoard();
				System.out.println("You won!");
				break;
			}
			 
		}
		
		input.close();
	}

	private static void printBoard() {
		for(int i=0;i<game.getBoard().length;i++){
			System.out.println(Arrays.toString(game.getBoard()[i]));
		}
	}
	
	private static int readInt(int max){
		int number = 0;
		while (true) {
			while (!input.hasNextInt()) {
				input.next();
				System.out.println("Thats not an integer.");
			}
			number = input.nextInt();

			if (number <= 0) {
				System.out.println("Input a positive value.");
			} else if (number > max) {
				System.out.println("Input a value in range [0-"+max+"].");
			} else {
				break;
			}
		}
		return number;
	}

}
