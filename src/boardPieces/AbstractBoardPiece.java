package boardPieces;

import java.awt.image.BufferedImage;

/* A general implementation of the boardPieceInterface 
 * Responsible for encapsulation the piece name and its X/Y coordinates in the model
 * Rest of models are left as abstract
 */
public abstract class AbstractBoardPiece implements boardPieces.BoardPieceInterface {
	/* Instance variables for holding the XPosition, YPosition, and Name */
	private int xPos;
	private int yPos;
	private String pieceName;

	/* Constructor */
	public AbstractBoardPiece(int xPos, int yPos, String pieceName) {
		/* Assume valid input is passed to constructor */
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
	
	public abstract void updateCost();
	
	public abstract BufferedImage getSecondaryImage();
	
	public abstract BufferedImage getThirdImage();
	
	public abstract int getNumEmployeePositions();

}
