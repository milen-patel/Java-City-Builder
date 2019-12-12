package boardPieces;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HousePiece extends AbstractBoardPiece implements BoardPieceInterface{
	private double income;
	private int residents;
	private BufferedImage icon;
	private BufferedImage icon2;
	private BufferedImage icon3;
	public static double costToConstruct = 500.0; 
	
	public HousePiece(int xPos, int yPos) {
		super(xPos, yPos, "House Piece");
		this.income = Math.random()*100;
		this.residents = (int) (1+ (4*Math.random()));
		
		if (icon == null) {
			try {
				icon = ImageIO.read(getClass().getResource("/boardPieces/HouseImageOne.jpg"));
				icon2 = ImageIO.read(getClass().getResource("/boardPieces/HouseImageTwo.jpg"));
				icon3 = ImageIO.read(getClass().getResource("/boardPieces/HouseImageThree.jpg"));

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
		return 0;
	}
}
