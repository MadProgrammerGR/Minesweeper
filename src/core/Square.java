package core;

public class Square {
	private boolean bomb, visible, flagged;
	private int neighbors = 0;

	public boolean isBomb() {
		return bomb;
	}

	public void setBomb(boolean b) {
		bomb = b;
	}

	public int getNeighbors() {
		return neighbors;
	}

	public void incrementNeighbors() {
		this.neighbors++;
	}
	
	@Override
	public String toString(){
		if(flagged) return "F";
		if(!visible) return "+";
		if(bomb) return "X";
		if(neighbors == 0) return " ";
		return ""+neighbors;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isFlagged() {
		return flagged;
	}

	public void setFlag(boolean flag) {
		flagged = flag;
	}
	
	public void toggleFlag(){
		flagged = !flagged;
	}

}
