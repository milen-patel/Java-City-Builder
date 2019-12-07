package main;

import java.util.ArrayList;
import java.util.List;

public class Model {
	/*
	 * 2D-Array representing all of the pieces on the board grid
	 * 0 = Empty Ground
	 * 1 = Road
	 * 2 = House
	 */
	private int[][] boardPieces;
	private double balance;
	private int day;
	private List<Interfaces.ModelObserver> observers;
	
	public Model() {
		/* Set default value for instance variables */
		boardPieces = new int[25][25];
		balance = 0;
		day = 0;
		observers = new ArrayList<Interfaces.ModelObserver>();
	}
	
	/* Returns the board, but cloned */
	public int[][] getBoard() {
		return boardPieces.clone();
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
