package boardPieces;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FactoryPiece extends AbstractBoardPiece implements BoardPieceInterface {

	private double dailyIncome;
	public static double costToConstruct = 100000.0;
	private static BufferedImage icon;

	public FactoryPiece(int xPos, int yPos) {
		super(xPos, yPos, "Factory Piece");
		this.dailyIncome = Math.random() * 25000.0 + 35000.0;

		if (icon == null) {
			try {
				icon = ImageIO.read(getClass().getResource("/boardPieces/FactoryImage.png"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public double getDailyIncome() {
		return this.dailyIncome;
	}

	@Override
	public int getNumResidents() {
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
	public void updateCost() {
		/* Make it more expensive to construct the next one */
		costToConstruct *= (1 + Math.random());
	}

	@Override
	public int getNumEmployeePositions() {
		return 10000;
	}
}
