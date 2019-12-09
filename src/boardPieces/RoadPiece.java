package boardPieces;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Interfaces.BoardPieceInterface;

public class RoadPiece implements BoardPieceInterface {
	public enum roadType { HORIZONTAL, VERTICAL, INTERSECTION, ENDPIECE, TLCORNER, TRCORNER, BRCORNER, BLCORNER}
	private int xPos;
	private int yPos;
	private BufferedImage icon;
	
	public RoadPiece(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		
		if (icon == null) {
			try {
				icon = ImageIO.read(getClass().getResource("/boardPieces/StraightRoadImage.png"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
	@Override
	public double getCostToBuild() {
		// TODO Auto-generated method stub
		return 1000.0;
	}
	@Override
	public String getPieceName() {
		return "Road Piece";
	}
	@Override
	public BufferedImage getPieceImage() {
		return icon;
	}
	
}
