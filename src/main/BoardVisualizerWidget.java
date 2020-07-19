package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import boardPieces.*;

/*
 * BoardVisualizerWidget is responsible for encapsulating an instance of the model and visualizing it on the UI
 */
public class BoardVisualizerWidget extends JPanel implements MouseListener {
	/*
	 * Variable that models how thick the lines should be between each element in
	 * the board
	 */
	public static final int BOARD_THICKNESS = 1;
	/*
	 * Instance of model that the Widget encapsulates to know how to draw each piece
	 */
	private Model model;
	/*
	 * Graphics2d Instance needed to draw the images and grid itself
	 */
	Graphics2D g2d;

	public BoardVisualizerWidget(Model model) {
		/*
		 * Add ourselves as a mouse listener, so that we can detect when the user clicks
		 * on a specific part of the grid
		 */
		this.addMouseListener(this);

		/* Set our preferred size */
		this.setPreferredSize(new Dimension(900, 900));

		/* Encapsulate the model and repaint the display */
		this.model = model;
		repaint();
	}

	/* Method that repaints the entire widget */
	public void paintComponent(Graphics g) {
		/* Make call to super method */
		super.paintComponent(g);
		/* Get the bounds of our widget */
		Rectangle r = getBounds();
		/*
		 * Model the dimension of each cell using the dimensions of our widget and the
		 * public class fields in Model that dictate the board dimensions
		 */
		double eachCellXWidth = (r.getWidth() / Model.BOARD_X);
		double eachCellYWidth = (r.getHeight() / Model.BOARD_Y);
		/* Set up Graphics2D instance */
		g2d = (Graphics2D) g.create();
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(BOARD_THICKNESS));

		/*
		 * Loop over the 2D array of BoardPieceInterface objects that the model
		 * encapsulates
		 */
		for (int y = 0; y < model.getBoard().length; y++) {
			for (int x = 0; x < model.getBoard()[0].length; x++) {
				/* Always draw rectangle around the border of each cell */
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.drawRect((int) (x * eachCellXWidth), (int) (y * eachCellYWidth), (int) (eachCellXWidth),
						(int) (eachCellYWidth));
				/* Visualize the piece inside of the cell */
				g2d.drawImage(model.getBoard()[y][x].getPieceImage(), (int) (x * eachCellXWidth),
						(int) (y * eachCellYWidth), (int) (eachCellXWidth), (int) (eachCellYWidth), null);
			}
		}

	}

	/*
	 * Helper method that converts an ordered pair to a dimension The dimension
	 * represents the ordered pair of the model's grid itself Make calls to
	 * dimension.getWidth() and dimension.getHeight() to get the output
	 */
	public Dimension convertPointToCoordinate(int x, int y) {
		/* Get the dimensions of this widget */
		Rectangle r = getBounds();
		/* Compute the cell width and height based on the dimensions */
		double eachCellXWidth = (r.getWidth() / Model.BOARD_X);
		double eachCellYWidth = (r.getHeight() / Model.BOARD_Y);
		/* Create and return a new dimension */
		return new Dimension((int) (x / eachCellXWidth), (int) (y / eachCellYWidth));
	}

	@Override
	/*
	 * When the user clicks on the grid, we need to get it to a position on the
	 * board Then we need to prompt the user to construct on that piece, if possible
	 */
	public void mouseClicked(MouseEvent e) {
		/* Empty string that will represent the construction choice */
		String input;
		/*
		 * Create a dimension so that we can convert the coordinates of a click to
		 * coordinates on the grid
		 */
		Dimension temp = convertPointToCoordinate(e.getX(), e.getY());
		/* Create array that models all of the possible choices the user has */
		String[] opters = this.model.getAvailableChoices((int) (temp.getWidth()), (int) (temp.getHeight()));
		/* If it is null, then the user cannot build anything here */
		if (opters == null) {
			JOptionPane.showMessageDialog(null, "You cannot build anything here", "Builder",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		} else {
			input = (String) JOptionPane.showInputDialog(null, "Select Option", "Build Spot",
					JOptionPane.QUESTION_MESSAGE, null, opters, opters[0]);
		}
		/* Make sure they didn't cancel */
		if (input == null) {
			return;
		}
		/* Parse through input and make appropriate model method call */
		if (input.contains("House")) {
			this.model.construct(new HousePiece((int) (temp.getWidth()), ((int) (temp.getHeight()))));
		} else if (input.contains("Road")) {
			this.model.construct(new RoadPiece((int) (temp.getWidth()), ((int) (temp.getHeight()))));
		} else if (input.contains("Apartment")) {
			this.model.construct(new ApartmentPiece((int) (temp.getWidth()), (int) (temp.getHeight())));
		} else if (input.contains("Demolish")) {
			this.model.demolish((int) (temp.getWidth()), (int) (temp.getHeight()));
		} else if (input.contains("Factory")) {
			this.model.construct(new FactoryPiece((int) (temp.getWidth()), (int) (temp.getHeight())));
		} else if (input.contains("Park")) {
			this.model.construct(new ParkPiece((int) (temp.getWidth()), (int) (temp.getHeight())));
			;
		} else if (input.contains("Retail")) {
			this.model.construct(new RetailPiece((int) (temp.getWidth()), (int) (temp.getHeight())));
			;
		}

	}

	/*
	 * Methods that we are required to override as part of the MouseListener
	 * Interface, but that we don't actually need
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
