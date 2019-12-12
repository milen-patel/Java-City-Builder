package boardPieces;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RetailPiece extends AbstractBoardPiece implements BoardPieceInterface{
	private double income;
	private BufferedImage icon;
	public static double costToConstruct = 50000.0; 
	
	public RetailPiece(int xPos, int yPos) {
		super(xPos, yPos, "Retail Piece");
		this.income = Math.random()*100 + 10250.00;
		
		if (icon == null) {
			try {
				icon = ImageIO.read(getClass().getResource("/boardPieces/RetailImage.png"));
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
		/* No one can live in a park */
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
		costToConstruct *= (1 + Math.random());		
	}
	
	@Override
	public int getNumEmployeePositions() {
		return 250;
	}
}
