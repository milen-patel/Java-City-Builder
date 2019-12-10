package boardPieces;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GrassPiece extends AbstractBoardPiece implements BoardPieceInterface{
	private static BufferedImage icon;
	
	public GrassPiece(int xPos, int yPos) {
		super(xPos, yPos, "Grass Piece");
		
		if (icon == null) {
			try {
				icon = ImageIO.read(getClass().getResource("/boardPieces/GrassImage.jpg"));
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
		return 0;
	}

	@Override
	public BufferedImage getPieceImage() {
		return icon;
	}
	
	
}
