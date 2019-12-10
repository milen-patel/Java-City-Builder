package boardPieces;

import java.awt.image.BufferedImage;

public interface BoardPieceInterface {
	public int getXPosition();
	public int getYPosition();
	public double getDailyIncome();
	public int getNumResidents();
	public double getCostToBuild();
	public String getPieceName();
	public BufferedImage getPieceImage();
}
