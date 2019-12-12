package boardPieces;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FactoryPiece extends AbstractBoardPiece implements BoardPieceInterface {

	private double dailyIncome;
	public static double costToConstruct = 100000.0;
	private static BufferedImage icon;
	private static BufferedImage icon2;
	private static BufferedImage icon3;

	public FactoryPiece(int xPos, int yPos) {
		super(xPos, yPos, "Factory Piece");
		this.dailyIncome = Math.random()*25000.0 + 35000.0;
		
		if (icon == null) {
			try {
				icon = ImageIO.read(getClass().getResource("/boardPieces/FactoryImage.png"));
				icon2 = ImageIO.read(getClass().getResource("/boardPieces/FactorySecondImage.png"));
				icon3 = ImageIO.read(getClass().getResource("/boardPieces/FactoryThirdImage.png"));
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
	public BufferedImage getSecondaryImage() {
		return icon2;
	}


	@Override
	public BufferedImage getThirdImage() {
		return icon3;
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
