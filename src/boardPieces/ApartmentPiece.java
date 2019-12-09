package boardPieces;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Interfaces.BoardPieceInterface;

public class ApartmentPiece extends AbstractBoardPiece implements BoardPieceInterface{
	private double income;
	private int residents;
	private BufferedImage icon; 
	public static double costToConstruct = 10000;
	
	
	public ApartmentPiece(int xPos, int yPos) {
		super(xPos, yPos, "Apartment Piece");
		this.income = Math.random()*1000;
		this.residents = (int) (1+ (400*Math.random()));
		
		if (icon == null) {
			try {
				icon = ImageIO.read(getClass().getResource("/boardPieces/ApartmentPiece.png"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* Make it more expensive to construct the next one */
		costToConstruct *= (1 + Math.random());
		
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

}
