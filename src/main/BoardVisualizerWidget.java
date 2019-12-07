package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Interfaces.BoardPieceInterface;
import boardPieces.GrassPiece;

public class BoardVisualizerWidget extends JPanel {
	private BoardPieceInterface[][] board;
	Graphics2D g2d;
    private BufferedImage iconGrass;


	
	public BoardVisualizerWidget(BoardPieceInterface[][] board) {
		this.board = board;
		this.setPreferredSize(new Dimension(900,900));
		repaint();
		
		   try{
		        iconGrass = ImageIO.read(getClass().getResource("/boardPieces/GrassImage.jpg"));
		        }catch(IOException e){e.printStackTrace();}
		        catch(Exception e){e.printStackTrace();}
		
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
		double eachCellXWidth =  (r.getWidth()/25);
		double eachCellYWidth =  (r.getHeight()/25);
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
				}
				
			}
		}
		
		
		
	}
}
