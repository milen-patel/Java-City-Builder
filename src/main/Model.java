package main;

import java.util.ArrayList;
import java.util.List;

import Interfaces.BoardPieceInterface;
import boardPieces.GrassPiece;

public class Model {
	
	private BoardPieceInterface[][] board;
	private double balance;
	private int day;
	private List<Interfaces.ModelObserver> observers;
	
	public Model() {
		/* Set default value for instance variables */
		board = new BoardPieceInterface[25][25];
		balance = 0;
		day = 0;
		observers = new ArrayList<Interfaces.ModelObserver>();
		
		/* Set all the pieces to grass pieces */
		for (int y=0; y<board.length; y++) {
			for (int x=0; x<board[0].length; x++) {
				board[y][x] = new GrassPiece(x,y);
			}
		}
	}
	
	/* Returns the board, but cloned */
	public BoardPieceInterface[][] getBoard() {
		return this.board;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void addToBalance(double amount) {
		balance += amount;
		notifyObservers(Interfaces.ModelObserver.EventTypes.BALANCE_CHANGED);
	}
	
	/* Observable Methods */
	public void addObserver(Interfaces.ModelObserver o) {
		observers.add(o);
	}
	public void removeObserver(Interfaces.ModelObserver o) {
		observers.remove(o);
	}
	public void notifyObservers(Interfaces.ModelObserver.EventTypes eventType) {
		for (Interfaces.ModelObserver o : observers) {
			if (eventType == Interfaces.ModelObserver.EventTypes.BALANCE_CHANGED) {
				o.BalanceChanged();
			}
		}
	}
}
