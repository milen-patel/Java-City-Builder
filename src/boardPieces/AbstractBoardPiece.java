package boardPieces;

import java.awt.image.BufferedImage;

public abstract class AbstractBoardPiece implements boardPieces.BoardPieceInterface {
	private int xPos;
	private int yPos;
	private String pieceName;
	
	 /* Constructor */
	public AbstractBoardPiece(int xPos, int yPos, String pieceName) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.pieceName = pieceName;
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
	public String getPieceName() {
		return this.pieceName;
	}
	
	/* Abstract Methods */
	public abstract double getDailyIncome();
	public abstract int getNumResidents();
	public abstract double getCostToBuild();
	public abstract BufferedImage getPieceImage();
}
