package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import boardPieces.*;
/*
 * BoardVisualizerWidget is responsible for encapsulating an instance of the model and visualizing it on the UI
 */
public class BoardVisualizerWidget extends JPanel implements MouseListener {
	public static final int BOARD_THICKNESS = 1;
	private Model model;
	Graphics2D g2d;

	public BoardVisualizerWidget(Model model) {
		this.addMouseListener(this);
		this.setPreferredSize(new Dimension(900, 900));
		this.model = model;
		repaint();
	}

	

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Rectangle r = getBounds();
		System.out.println("Board Visualizer Widget Repainting");
		System.out.println("|--> Width: " + r.getWidth());
		System.out.println("|--> Height: " + r.getHeight());
		double eachCellXWidth = (r.getWidth() / Model.BOARD_X);
		double eachCellYWidth = (r.getHeight() / Model.BOARD_Y);
		g2d = (Graphics2D) g.create();
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(BOARD_THICKNESS));

		for (int y = 0; y < model.getBoard().length; y++) {
			for (int x = 0; x < model.getBoard()[0].length; x++) {
				// Always draw rectangle
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.drawRect((int) (x * eachCellXWidth), (int) (y * eachCellYWidth), (int) (eachCellXWidth),
						(int) (eachCellYWidth));
				/* Visualize the piece */
				g2d.drawImage(model.getBoard()[y][x].getPieceImage(), (int) (x * eachCellXWidth), (int) (y * eachCellYWidth),
						(int) (eachCellXWidth), (int) (eachCellYWidth), null);

			}
		}

	}

	public Dimension convertPointToCoordinate(int x, int y) {
		Rectangle r = getBounds();
		double eachCellXWidth = (r.getWidth() / Model.BOARD_X);
		double eachCellYWidth = (r.getHeight() / Model.BOARD_Y);

		return new Dimension((int) (x / eachCellXWidth), (int) (y / eachCellYWidth));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		String input;
		Dimension temp = convertPointToCoordinate(e.getX(), e.getY());
		System.out.println("Cell Clicked At: " + temp.getWidth() + ", " + temp.getHeight());
		String[] opters = this.model.getAvailableChoices((int) (temp.getWidth()), (int) (temp.getHeight()));
		if (opters == null) {
			JOptionPane.showMessageDialog(null, "You cannot build anything here", "Builder",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		} else {
			input = (String) JOptionPane.showInputDialog(null, "Select Option", "Build Spot",
					JOptionPane.QUESTION_MESSAGE, null, opters, opters[0]);
		}

		/* Make sure they didnt cancel */
		if (input == null) {
			return;
		}

		if (input.contains("House")) {
			this.model.construct(new HousePiece((int) (temp.getWidth()), ((int) (temp.getHeight()))));
		} else if (input.contains("Road")) {
			this.model.construct(new RoadPiece((int) (temp.getWidth()), ((int) (temp.getHeight()))));
		} else if (input.contains("Apartment")) {
			this.model.construct(new ApartmentPiece((int) (temp.getWidth()), (int) (temp.getHeight())));
		} else if (input.contains("Demolish")) {
			this.model.demolish((int) (temp.getWidth()), (int) (temp.getHeight()));
		} else if (input.contains("Factory")) {
			this.model.construct(new FactoryPiece((int) (temp.getWidth()), (int)(temp.getHeight())));
		} else if (input.contains("Park")) {
			this.model.construct(new ParkPiece((int)(temp.getWidth()),(int)(temp.getHeight())));;
		} else if (input.contains("Retail")) {
			this.model.construct(new RetailPiece((int)(temp.getWidth()),(int)(temp.getHeight())));;
		}
		//TODO make these variables into integers instead of casting each time we want to use them

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
