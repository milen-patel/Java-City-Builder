package boardPieces;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RoadPiece extends AbstractBoardPiece implements BoardPieceInterface {
	public enum roadType { HORIZONTAL, VERTICAL, INTERSECTION, ENDPIECE, TLCORNER, TRCORNER, BRCORNER, BLCORNER}
	private BufferedImage icon;
	public static double costToConstruct = 500.0;
	
	public RoadPiece(int xPos, int yPos) {
		super(xPos, yPos, "Road Piece");
		
		if (icon == null) {
			try {
				icon = ImageIO.read(getClass().getResource("/boardPieces/RoadImage.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
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
		return costToConstruct;
	}

	@Override
	public BufferedImage getPieceImage() {
		return icon;
	}

	@Override
	public BufferedImage getSecondaryImage() {
		return getPieceImage();
	}


	@Override
	public BufferedImage getThirdImage() {
		return getPieceImage();
	}
	
	@Override
	public void updateCost() {
		/* Make it more expensive to construct the next one */
		costToConstruct *= (1 + Math.random()/15);		
	}
	
	@Override
	public int getNumEmployeePositions() {
		return 0;
	}
}
