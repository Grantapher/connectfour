package connect4;

import java.awt.Color;
import javax.swing.JPanel;

public class myC4Panel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4591481615909369264L;
	private int x, y, piece;
	public static final int EMPTY = 0;
	public static final int COLOR1 = 1;
	public static final int COLOR2 = 2;
	public static final Color COLOR1COLOR = Color.RED;
	public static final String COLOR1STRING = "Red";
	public static final Color COLOR2COLOR = Color.BLACK;
	public static final String COLOR2STRING = "Black";
	
	myC4Panel(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getIndexX() {
		return x;
	}
	
	public int getIndexY() {
		return y;
	}
	
	public int getPiece() {
		return piece;
	}
	
	public Color getColor(boolean fadeStatus) {
		if(fadeStatus == BoardGUI.NOFADE) {
			if(piece == COLOR1)
				return COLOR1COLOR;
			if(piece == COLOR2)
				return COLOR2COLOR;
			else
				return Color.WHITE;//background color
		} else {
			if(BoardGUI.TURN == COLOR1) {
				return new Color(COLOR1COLOR.getRed(),
						COLOR1COLOR.getGreen(), COLOR1COLOR.getBlue(), 128);
			} else {
				return new Color(COLOR2COLOR.getRed(),
						COLOR2COLOR.getGreen(), COLOR2COLOR.getBlue(), 128);
			}
		}
	}
	
	public void setPiece(int p) {
		piece = p;
	}
	
	public boolean isEmpty() {
		if(piece == EMPTY)
			return true;
		return false;
	}
	
	public static int oppositePiece(int PIECE) {
		return PIECE == COLOR1 ? COLOR2 : COLOR1;
	}
}
