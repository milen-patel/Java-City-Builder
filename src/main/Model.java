package main;

import java.util.ArrayList;
import java.util.List;

import Interfaces.BoardPieceInterface;
import Interfaces.ModelObserver;
import boardPieces.GrassPiece;
import boardPieces.HousePiece;
import boardPieces.*;

public class Model {
	
	public static final int BOARD_X = 25;
	public static final int BOARD_Y = 25;
	
	private BoardPieceInterface[][] board;
	private double balance;
	private int day;
	private List<Interfaces.ModelObserver> observers;
	
	public Model() {
		/* Set default value for instance variables */
		board = new BoardPieceInterface[BOARD_Y][BOARD_X];
		balance = 5000.0;
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
	
	public int getDay() {
		return day;
	}
	
	public void addToBalance(double amount) {
		balance += amount;
		notifyObservers(Interfaces.ModelObserver.EventTypes.BALANCE_CHANGED);
		EventLog.getEventLog().addEntry("Balance has been changed by: $" + amount);
	}
	
	public double getDailyIncome() {
		double runningTotal = 0.0;
		for (int y=0; y<board.length; y++) {
			for(int x=0; x<board[0].length; x++) {
				runningTotal += board[y][x].getDailyIncome();
			}
		}
		return runningTotal;
	}
	
	public int getPopulation() {
		int runningTotal = 0;
		for (int y=0; y<board.length; y++) {
			for(int x=0; x<board[0].length; x++) {
				runningTotal += board[y][x].getNumResidents();
			}
		}
		return runningTotal;
	}
	
	
	public String[] getAvailableChoices(int x, int y) {
		/* If something has already been constructed here, don't allow something else */
		if (!(board[y][x] instanceof GrassPiece)) { return null; }
		/* Create an empty list */
		List<String> potentialOptions = new ArrayList<String>();
		/* Add whatever we have enough money for */
		if (this.getBalance() >= ((new HousePiece(-1,-1)).getCostToBuild())) { potentialOptions.add("House"); }
		if (this.getBalance() >= (new RoadPiece(-1,-1).getCostToBuild())) { potentialOptions.add("Road"); }
		
		/* Validate list size before returning */
		if (potentialOptions.size() != 0) {
			return potentialOptions.toArray(new String[potentialOptions.size()]);
		} else {
			return null;
		}
	}
	
	
	public void construct(BoardPieceInterface piece) {
		/* Subtract balance */
		balance -= piece.getCostToBuild();
		/* Update Board */
		board[piece.getYPosition()][piece.getXPosition()] = piece;
		/* Add Log Notification */
		EventLog.getEventLog().addEntry(piece.getPieceName() + " constructed at (" + piece.getXPosition() + ", " + piece.getYPosition() + ").");
		/* Notify observers */
		notifyObservers(ModelObserver.EventTypes.BALANCE_CHANGED);
		notifyObservers(ModelObserver.EventTypes.DAILYINCOME_CHANGED);
		notifyObservers(ModelObserver.EventTypes.POPULATION_CHANGED);
		notifyObservers(ModelObserver.EventTypes.BOARD_CHANGED);
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
			} else if (eventType == Interfaces.ModelObserver.EventTypes.DAILYINCOME_CHANGED) {
				o.DailyIncomeChanged();
			} else if (eventType == Interfaces.ModelObserver.EventTypes.DAY_CHANGED) {
				o.DayChanged();
			} else if (eventType == Interfaces.ModelObserver.EventTypes.POPULATION_CHANGED) {
				o.PopulationChanged();
			} else if (eventType == Interfaces.ModelObserver.EventTypes.BOARD_CHANGED) {
				o.BoardChanged();
			}
		}
	}
}
