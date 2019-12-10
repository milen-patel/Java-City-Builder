package boardPieces;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ParkPiece extends AbstractBoardPiece implements BoardPieceInterface{
	private double income;
	private BufferedImage icon;
	public static double costToConstruct = 500.0; 
	
	public ParkPiece(int xPos, int yPos) {
		super(xPos, yPos, "Park Piece");
		this.income = Math.random()*100;
		
		if (icon == null) {
			try {
				icon = ImageIO.read(getClass().getResource("/boardPieces/ParkImage.png"));
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
	public void updateCost() {
		/* Make it more expensive to construct the next one */
		costToConstruct *= (1 + Math.random());		
	}
}
