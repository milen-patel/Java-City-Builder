package boardPieces;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Interfaces.BoardPieceInterface;

public class HousePiece implements BoardPieceInterface{
	private int xPos;
	private int yPos;
	private double income;
	private int residents;
	private BufferedImage icon;
	
	public HousePiece(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.income = Math.random()*100;
		this.residents = (int) (1+ (4*Math.random()));
		
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
	public int getXPosition() {
		return this.xPos;
	}

	@Override
	public int getYPosition() {
		return this.yPos;
	}
	
	@Override
	public double getDailyIncome() {
		return income;
	}
	
	@Override
	public int getNumResidents() {
		/* No one can live on empty pieces */
		return residents;
	}
	@Override
	public double getCostToBuild() {
		// TODO Auto-generated method stub
		return 500.0;
	}
	@Override
	public String getPieceName() {
		return "House Piece";
	}
	@Override
	public BufferedImage getPieceImage() {
		return icon;
	}

}
