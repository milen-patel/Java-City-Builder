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

import Interfaces.BoardPieceInterface;
import boardPieces.GrassPiece;
import boardPieces.HousePiece;
import boardPieces.RoadPiece;

public class BoardVisualizerWidget extends JPanel implements MouseListener {
	//TODO: No need to encapsulate board if we encapsulate the model
	private BoardPieceInterface[][] board;
	private Model model;
	Graphics2D g2d;
    private BufferedImage iconGrass;
    private BufferedImage iconRoad;
    private BufferedImage iconHouse;


	
	public BoardVisualizerWidget(BoardPieceInterface[][] board, Model model) {
		this.addMouseListener(this);
		this.board = board;
		this.setPreferredSize(new Dimension(900,900));
		this.model = model;
		repaint();
		
		   try{
		        iconGrass = ImageIO.read(getClass().getResource("/boardPieces/GrassImage.jpg"));
		        iconRoad = ImageIO.read(getClass().getResource("/boardPieces/StraightRoadImage.png"));
		        iconHouse = ImageIO.read(getClass().getResource("/boardPieces/HouseImage.png"));
		   } catch(IOException e) {
			   e.printStackTrace();
		   } catch(Exception e) { 
			   e.printStackTrace();
		   }
		
	}
	
	public void repaint(BoardPieceInterface[][] board) {
		this.board = board;
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Rectangle r = getBounds();
		System.out.println("Board Visualizer Widget Repainting");
		System.out.println("|--> Width: " + r.getWidth());
		System.out.println("|--> Height: " + r.getHeight());
		double eachCellXWidth =  (r.getWidth()/Model.BOARD_X);
		double eachCellYWidth =  (r.getHeight()/Model.BOARD_Y);
		g2d = (Graphics2D) g.create();
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(4));
		
		for (int y=0; y<board.length; y++) {
			for (int x=0; x<board[0].length; x++) {
				//Always draw rectangle
				g2d.setColor(Color.LIGHT_GRAY);
				g2d.drawRect((int)(x*eachCellXWidth), (int)(y*eachCellYWidth), (int)(eachCellXWidth), (int)(eachCellYWidth));
				
				/* If the position is a grass piece, then visualize grass */
				if (board[y][x] instanceof GrassPiece) {
			        g2d.drawImage(iconGrass, (int)(x*eachCellXWidth), (int)(y*eachCellYWidth), (int) (eachCellXWidth), (int) (eachCellYWidth), null);
				} else if (board[y][x] instanceof RoadPiece) {
			        g2d.drawImage(iconRoad, (int)(x*eachCellXWidth), (int)(y*eachCellYWidth), (int) (eachCellXWidth), (int) (eachCellYWidth), null);
				} else if (board[y][x] instanceof HousePiece) {
			        g2d.drawImage(iconHouse, (int)(x*eachCellXWidth), (int)(y*eachCellYWidth), (int) (eachCellXWidth), (int) (eachCellYWidth), null);
				}
				
			}
		}
		
		
		
	}

	
	public Dimension convertPointToCoordinate(int x, int y) {
		Rectangle r = getBounds();
		double eachCellXWidth =  (r.getWidth()/Model.BOARD_X);
		double eachCellYWidth =  (r.getHeight()/Model.BOARD_Y);
		
		return new Dimension((int) (x/eachCellXWidth), (int) (y/eachCellYWidth));
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		String input;
		Dimension temp = convertPointToCoordinate(e.getX(), e.getY());
		System.out.println("Cell Clicked At: " + temp.getWidth() + ", " + temp.getHeight());
		String[] opters = this.model.getAvailableChoices((int) (temp.getWidth()), (int) (temp.getHeight()));
		if (opters == null) {
	        JOptionPane.showMessageDialog(null, "You cannot build anything here", "Builder", JOptionPane.INFORMATION_MESSAGE);
	        return;
		} else {
			input = (String) JOptionPane.showInputDialog(null, "Select Option", "Build Spot", JOptionPane.QUESTION_MESSAGE, null, opters, opters[0]); 
		}
		
		/* Make sure they didnt cancel */
		if (input == null) {return;}
		
		if (input.contains("House")) {
			this.model.construct(new HousePiece((int)(temp.getWidth()), ((int)(temp.getHeight()))));
		} else if (input.contains("Road")) {
			this.model.construct(new RoadPiece((int)(temp.getWidth()), ((int)(temp.getHeight()))));
		}
		
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
