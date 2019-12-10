package boardPieces;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class WaterPiece extends AbstractBoardPiece implements boardPieces.BoardPieceInterface {
	private BufferedImage icon;

	public WaterPiece(int xPos, int yPos) {
		super(xPos, yPos, "Water Piece");
		
		
		if (icon == null) {
			try {
				icon = ImageIO.read(getClass().getResource("/boardPieces/WaterImage.jpeg"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public double getDailyIncome() {
		return 0;
	}

	@Override
	public int getNumResidents() {
		return 0;
	}

	@Override
	public double getCostToBuild() {
		return 10000000000.0;
	}

	@Override
	public BufferedImage getPieceImage() {
		return icon;
	}

}
