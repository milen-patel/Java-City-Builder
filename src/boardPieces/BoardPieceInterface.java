package boardPieces;

import java.awt.image.BufferedImage;

/* 
 * A boardPiece represents one of the possible buildings that can be created on the BoardVisualizerWidget
 * A boardPiece knows its relative position on the board in terms of x and y coordinates
 * The implementation also encapsulates relevant images including its cost, income, residents, and name
 * The implementation is also responsible for encapsulating an image to be displayed in the UI
 */

public interface BoardPieceInterface {
	/*
	 * Returns the X position of the piece encapsulated in the model's grid
	 */
	public int getXPosition();

	/*
	 * Returns the Y position of the piece encapsulated in the model's grid
	 */
	public int getYPosition();

	/*
	 * Returns the income that the building will generate, represented in dollars
	 * per day
	 */
	public double getDailyIncome();

	/* Returns the number of residents that live in this board piece */
	public int getNumResidents();

	/*
	 * Returns the price that the user must pay to construct this building
	 */
	public double getCostToBuild();

	/* Returns the piece of a name as a string */
	public String getPieceName();

	/*
	 * Returns an image representation of the building for the BoardVisualizerWidget
	 * to use
	 * 
	 * A current feature is the ability to provide basic animation for each icon The
	 * BoardVisualizerWidget will alternate between these three methods as the day
	 * changes If all three methods return different images, the icon will appear
	 * animated
	 * 
	 */
	public BufferedImage getPieceImage();

	/*
	 * As successive units of a piece are constructed, we want the price of
	 * construction to increase
	 */
	public void updateCost();

	/*
	 * Some board pieces have the opportunity to employ people This method returns
	 * the number of people that the implementation can employ
	 */
	public int getNumEmployeePositions();

}
