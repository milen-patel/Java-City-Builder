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
	public static double COST_TO_DEMOLISH = 1250.00;
	
	private BoardPieceInterface[][] board;
	private double balance;
	private int day;
	private List<Interfaces.ModelObserver> observers;
	
	public Model() {
		/* Set default value for instance variables */
		board = new BoardPieceInterface[BOARD_Y][BOARD_X];
		balance = 5000.0;
		day = 1;
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
		/* Create an empty list */
		List<String> potentialOptions = new ArrayList<String>();
		/* If something has already been constructed here, only allow for demolish */
		if (!(board[y][x] instanceof GrassPiece) && this.getBalance() >= COST_TO_DEMOLISH) { 
			potentialOptions.add("Demolish: $" + COST_TO_DEMOLISH);
			return potentialOptions.toArray(new String[potentialOptions.size()]);
			
		} else if (!(board[y][x] instanceof GrassPiece)) {
			return null;
		}
		
		/* Add whatever we have enough money for */
		//TODO make cost method be static
		if (this.getBalance() >= HousePiece.costToConstruct) { potentialOptions.add("House: $" + View.round(HousePiece.costToConstruct, 2)); }
		if (this.getBalance() >= RoadPiece.costToConstruct) { potentialOptions.add("Road: $" + View.round(RoadPiece.costToConstruct,2)); }
		if (this.getBalance() >= ApartmentPiece.costToConstruct) { potentialOptions.add("Apartment: $" + View.round(ApartmentPiece.costToConstruct,2)); }
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
	
	public void demolish(int x, int y) {
		/* Change the board spot */
		board[y][x] = new GrassPiece(x,y);
		/* Charge the user */
		this.addToBalance(-COST_TO_DEMOLISH);
		/* Make it more expensive to demolish the next spot */
		COST_TO_DEMOLISH *= 2;
		/* Write to log */
		EventLog.getEventLog().addEntry("Building demolished at (" + x + ", " + y + ")");
		/* Notify Observers */
		notifyObservers(ModelObserver.EventTypes.BOARD_CHANGED);
		notifyObservers(ModelObserver.EventTypes.POPULATION_CHANGED);
		notifyObservers(ModelObserver.EventTypes.DAILYINCOME_CHANGED);
	}
	
	public void nextDay() {
		EventLog.getEventLog().addEntry("Day "+  day++ + " has ended.");
		this.balance += this.getDailyIncome();
		notifyObservers(ModelObserver.EventTypes.BALANCE_CHANGED);
		notifyObservers(ModelObserver.EventTypes.DAY_CHANGED);
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
