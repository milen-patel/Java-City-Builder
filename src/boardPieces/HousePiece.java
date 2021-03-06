package boardPieces;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HousePiece extends AbstractBoardPiece implements BoardPieceInterface {
	private double income;
	private int residents;
	private BufferedImage icon;
	public static double costToConstruct = 500.0;

	public HousePiece(int xPos, int yPos) {
		super(xPos, yPos, "House Piece");
		this.income = Math.random() * 100;
		this.residents = (int) (1 + (4 * Math.random()));

		if (icon == null) {
			try {
				icon = ImageIO.read(getClass().getResource("/boardPieces/HouseImage.png"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public double getDailyIncome() {
		return income;
	}

	@Override
	public int getNumResidents() {
		return residents;
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
		return 0;
	}
}
