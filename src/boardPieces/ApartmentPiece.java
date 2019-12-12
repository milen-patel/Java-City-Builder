package boardPieces;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ApartmentPiece extends AbstractBoardPiece implements BoardPieceInterface{
	private double income;
	private int residents;
	private static BufferedImage icon; 
	public static double costToConstruct = 10000;
	
	
	public ApartmentPiece(int xPos, int yPos) {
		super(xPos, yPos, "Apartment Piece");
		this.income = Math.random()*1000;
		this.residents = (int) (1+ (400*Math.random()));
		
		if (icon == null) {
			try {
				icon = ImageIO.read(getClass().getResource("/boardPieces/ApartmentImage.png"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	

	@Override
	public double getDailyIncome() {
		return this.income;
	}

	@Override
	public int getNumResidents() {
		return this.residents;
	}

	@Override
	public double getCostToBuild() {
		return 10000;
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
	public BufferedImage getSecondaryImage() {
		return getPieceImage();
	}


	@Override
	public BufferedImage getThirdImage() {
		return getPieceImage();
	}

	@Override
	public int getNumEmployeePositions() {
		return 30;
	}
}
