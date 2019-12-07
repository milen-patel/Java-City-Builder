package boardPieces;

import Interfaces.BoardPieceInterface;

public class GrassPiece implements BoardPieceInterface{
	private int xPos;
	private int yPos;
	
	public GrassPiece(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	@Override
	public int getXPosition() {
		return this.xPos;
	}

	@Override
	public int getYPosition() {
		return this.yPos;
	}
	
	@Override
	public double getDailyIncome() {
		/* Empty pieces shouldn't generate revenue */
		return 0.0;
	}
	
	@Override
	public int getNumResidents() {
		/* No one can live on empty pieces */
		return 0;
	}
	
}
