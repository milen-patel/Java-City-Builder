package main;

import java.util.ArrayList;
import java.util.List;

import boardPieces.*;

public class Model {

	public static final int BOARD_X = 25;
	public static final int BOARD_Y = 25;
	public static double COST_TO_DEMOLISH = 1250.00;

	private BoardPieceInterface[][] board;
	private double balance;
	private int day;
	private List<main.ModelObserver> observers;
	private double happiness;

	public Model() {
		/* Set default value for instance variables */
		board = new BoardPieceInterface[BOARD_Y][BOARD_X];
		balance = 5000.0;
		day = 1;
		happiness = 0.000;
		observers = new ArrayList<main.ModelObserver>();

		/* Set all the pieces to grass pieces */
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				if (x == 0) {
					/* Start the user with a row of road */
					board[y][x] = new RoadPiece(x, y);
					/* We built roads, so we dont want to inflate the price from the start */
					RoadPiece.costToConstruct = 1000;
				} else {
					board[y][x] = new GrassPiece(x, y);
				}
			}
		}
		/* Place some water at the top of the board */
		for (int i = 0; i <= 3; i++) {
			for (int j = 1; j <= 4 - i; j++) {
				board[i][BOARD_X - j] = new WaterPiece(i, BOARD_X - j);
			}
		}

		/* Place some water in the middle of the board */
		for (int x = (BOARD_X / 2) - 1; x <= (BOARD_X / 2) + 1; x++) {
			for (int y = (BOARD_Y / 2) - 1; y <= (BOARD_Y / 2) + 1; y++) {
				board[y][x] = new WaterPiece(x, y);
			}
		}
		/* Give the user one park piece for employement purposes */
		board[BOARD_X / 2 - 2][BOARD_Y / 2 - 1] = new ParkPiece(BOARD_X / 2 - 2, BOARD_Y / 2 - 1);

	}

	/* Returns the board, but cloned */
	public synchronized BoardPieceInterface[][] getBoard() {
		return this.board;
	}

	public synchronized double getBalance() {
		return balance;
	}

	public synchronized int getDay() {
		return day;
	}

	public synchronized void addToBalance(double amount) {
		/* Stop it from printing to the screen after every day */
		if (amount == 0.0) {
			return;
		}
		balance += amount;
		notifyObservers(main.ModelObserver.EventTypes.BALANCE_CHANGED);
		EventLog.getEventLog().addEntry("Balance has been changed by: $" + amount);
	}

	public synchronized double getDailyIncome() {
		double employmentMultiplier = this.getEmploymentMultiplier();
		double runningTotal = 0.0;
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				runningTotal += board[y][x].getDailyIncome() * employmentMultiplier;
			}
		}
		return runningTotal;
	}

	public synchronized int getPopulation() {
		int runningTotal = 0;
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				runningTotal += board[y][x].getNumResidents();
			}
		}
		return runningTotal;
	}

	public synchronized String[] getAvailableChoices(int x, int y) {
		/* Create an empty list */
		List<String> potentialOptions = new ArrayList<String>();
		/* If something has already been constructed here, only allow for demolish */
		if (!(board[y][x] instanceof GrassPiece) && this.getBalance() >= COST_TO_DEMOLISH
				&& !(board[y][x] instanceof WaterPiece) && !(board[y][x] instanceof RoadPiece)) {
			potentialOptions.add("Demolish: $" + COST_TO_DEMOLISH);
			return potentialOptions.toArray(new String[potentialOptions.size()]);

		} else if (!(board[y][x] instanceof GrassPiece)) {
			return null;
		}

		/* Add whatever we have enough money for */
		if (this.getBalance() >= HousePiece.costToConstruct && isPieceTouchingRoad(x, y)) {
			potentialOptions.add("House: $" + View.round(HousePiece.costToConstruct, 2));
		}
		if (this.getBalance() >= RoadPiece.costToConstruct && isPieceTouchingRoad(x, y)) {
			potentialOptions.add("Road: $" + View.round(RoadPiece.costToConstruct, 2));
		}
		if (this.getBalance() >= ApartmentPiece.costToConstruct && isPieceTouchingRoad(x, y)) {
			potentialOptions.add("Apartment: $" + View.round(ApartmentPiece.costToConstruct, 2));
		}
		if (this.getBalance() >= FactoryPiece.costToConstruct && isPieceTouchingWater(x, y)
				&& isPieceTouchingRoad(x, y)) {
			potentialOptions.add("Factory: $" + View.round(FactoryPiece.costToConstruct, 2));
		}
		if (this.getBalance() >= ParkPiece.costToConstruct && isPieceTouchingWater(x, y)) {
			potentialOptions.add("Park: $" + View.round(ParkPiece.costToConstruct, 2));
		}
		if (this.getBalance() >= RetailPiece.costToConstruct && isPieceTouchingRoad(x, y)) {
			potentialOptions.add("Retail: $" + View.round(RetailPiece.costToConstruct, 2));
		}
		/* Validate list size before returning */
		if (potentialOptions.size() != 0) {
			return potentialOptions.toArray(new String[potentialOptions.size()]);
		} else {
			return null;
		}
	}

	public synchronized void construct(BoardPieceInterface piece) {
		/* Subtract balance */
		balance -= piece.getCostToBuild();
		/* Update Board */
		board[piece.getYPosition()][piece.getXPosition()] = piece;
		/* Add Log Notification */
		EventLog.getEventLog().addEntry(
				piece.getPieceName() + " constructed at (" + piece.getXPosition() + ", " + piece.getYPosition() + ").");
		/* Make the piece more expensive to construct */
		piece.updateCost();
		/* Notify observers */
		notifyObservers(ModelObserver.EventTypes.BALANCE_CHANGED);
		notifyObservers(ModelObserver.EventTypes.DAILYINCOME_CHANGED);
		notifyObservers(ModelObserver.EventTypes.POPULATION_CHANGED);
		notifyObservers(ModelObserver.EventTypes.BOARD_CHANGED);
		notifyObservers(ModelObserver.EventTypes.UNEMPLOYEMENT_CHANGED);
		recomputeHappiness();

	}

	public synchronized void demolish(int x, int y) {
		/* Change the board spot */
		board[y][x] = new GrassPiece(x, y);
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
		notifyObservers(ModelObserver.EventTypes.UNEMPLOYEMENT_CHANGED);
		recomputeHappiness();

	}

	public synchronized void nextDay() {
		EventLog.getEventLog().addEntry("Day " + day++ + " has ended.");
		this.balance += this.getDailyIncome();
		notifyObservers(ModelObserver.EventTypes.BALANCE_CHANGED);
		notifyObservers(ModelObserver.EventTypes.DAY_CHANGED);
		notifyObservers(ModelObserver.EventTypes.BOARD_CHANGED);
		this.addToBalance(this.getHappiness() * 100);
		recomputeHappiness();
	}

	private synchronized boolean isPieceTouchingRoad(int x, int y) {
		/* Test right above, if possible */
		if (y != 0 && board[y - 1][x] instanceof RoadPiece) {
			return true;
		}
		/* Rest right below, if possible */
		if (y != BOARD_Y - 1 && board[y + 1][x] instanceof RoadPiece) {
			return true;
		}
		/* Test right spot, if possible */
		if (x != BOARD_X - 1 && board[y][x + 1] instanceof RoadPiece) {
			return true;
		}
		/* Test left spot, if possible */
		if (x != 0 && board[y][x - 1] instanceof RoadPiece) {
			return true;
		}
		/* If none of the cases work, then no */
		return false;
	}

	private synchronized boolean isPieceTouchingWater(int x, int y) {
		/* Test right above, if possible */
		if (y != 0 && board[y - 1][x] instanceof WaterPiece) {
			return true;
		}
		/* Rest right below, if possible */
		if (y != BOARD_Y - 1 && board[y + 1][x] instanceof WaterPiece) {
			return true;
		}
		/* Test right spot, if possible */
		if (x != BOARD_X - 1 && board[y][x + 1] instanceof WaterPiece) {
			return true;
		}
		/* Test left spot, if possible */
		if (x != 0 && board[y][x - 1] instanceof WaterPiece) {
			return true;
		}
		/* If none of the cases work, then no */
		return false;
	}

	private synchronized boolean isPieceTouchingFactory(int x, int y) {
		/* Test right above, if possible */
		if (y != 0 && board[y - 1][x] instanceof FactoryPiece) {
			return true;
		}
		/* Rest right below, if possible */
		if (y != BOARD_Y - 1 && board[y + 1][x] instanceof FactoryPiece) {
			return true;
		}
		/* Test right spot, if possible */
		if (x != BOARD_X - 1 && board[y][x + 1] instanceof FactoryPiece) {
			return true;
		}
		/* Test left spot, if possible */
		if (x != 0 && board[y][x - 1] instanceof FactoryPiece) {
			return true;
		}
		/* If none of the cases work, then no */
		return false;
	}

	private synchronized boolean isPieceTouchingPark(int x, int y) {
		/* Test right above, if possible */
		if (y != 0 && board[y - 1][x] instanceof ParkPiece) {
			return true;
		}
		/* Rest right below, if possible */
		if (y != BOARD_Y - 1 && board[y + 1][x] instanceof ParkPiece) {
			return true;
		}
		/* Test right spot, if possible */
		if (x != BOARD_X - 1 && board[y][x + 1] instanceof ParkPiece) {
			return true;
		}
		/* Test left spot, if possible */
		if (x != 0 && board[y][x - 1] instanceof ParkPiece) {
			return true;
		}
		/* If none of the cases work, then no */
		return false;
	}

	/* Observable Methods */
	public void addObserver(main.ModelObserver o) {
		observers.add(o);
	}

	public void removeObserver(main.ModelObserver o) {
		observers.remove(o);
	}

	public void notifyObservers(main.ModelObserver.EventTypes eventType) {
		/* Notify each observer based on what the event was */
		for (main.ModelObserver o : observers) {
			if (eventType == main.ModelObserver.EventTypes.BALANCE_CHANGED) {
				o.BalanceChanged();
			} else if (eventType == main.ModelObserver.EventTypes.DAILYINCOME_CHANGED) {
				o.DailyIncomeChanged();
			} else if (eventType == main.ModelObserver.EventTypes.DAY_CHANGED) {
				o.DayChanged();
			} else if (eventType == main.ModelObserver.EventTypes.POPULATION_CHANGED) {
				o.PopulationChanged();
			} else if (eventType == main.ModelObserver.EventTypes.BOARD_CHANGED) {
				o.BoardChanged();
			} else if (eventType == main.ModelObserver.EventTypes.HAPPINESS_CHANGED) {
				o.HappinessChanged();
			} else if (eventType == main.ModelObserver.EventTypes.UNEMPLOYEMENT_CHANGED) {
				o.UnemployementChanged();
			}
		}
	}

	public synchronized double getHappiness() {
		return happiness;
	}

	public synchronized void recomputeHappiness() {
		/*
		 * Every person should add 0.01 to total happiness But, if the dwelling is
		 * touching a factory, it should decrease total happiness by 10 If the dwelling
		 * is touching water or a park, then it should increase happiness by 0.05/person
		 */
		this.happiness = 0.0;
		for (int y = 0; y < this.board.length; y++) {
			for (int x = 0; x < this.board[0].length; x++) {
				if (this.isPieceTouchingFactory(x, y) && board[y][x].getNumResidents() != 0) {
					this.happiness -= 10;
				} else if (this.isPieceTouchingPark(x, y) || this.isPieceTouchingWater(x, y)) {
					this.happiness += board[y][x].getNumResidents() * 0.05;
				} else {
					this.happiness += board[y][x].getNumResidents() * 0.01;
				}
			}
		}
		/* Notify observers */
		notifyObservers(ModelObserver.EventTypes.HAPPINESS_CHANGED);
	}

	/*
	 * Returns the current unemployment rate, formatted as a string Unemployment =
	 * (TotalWorkers - TotalJobs)/(TotalWorkers) * 100
	 */
	public synchronized String getUnemploymentRate() {
		int totalJobs = 0;
		for (int y = 0; y < this.board.length; y++) {
			for (int x = 0; x < this.board[0].length; x++) {
				totalJobs += board[y][x].getNumEmployeePositions();
			}
		}

		if (totalJobs >= this.getPopulation()) {
			return "0 %";
		} else {
			return (View.round(((double) (this.getPopulation() - totalJobs) / (double) (this.getPopulation())) * 100, 2)
					+ "%");
		}

	}

	/*
	 * Used for computing daily income Returns the amount of the population that is
	 * employed on the range (0,1) 0 indicates no employment at all 1 indicates that
	 * every citizen is employed
	 */
	private synchronized double getEmploymentMultiplier() {
		int totalJobs = 0;
		for (int y = 0; y < this.board.length; y++) {
			for (int x = 0; x < this.board[0].length; x++) {
				totalJobs += board[y][x].getNumEmployeePositions();
			}
		}
		if (totalJobs >= this.getPopulation()) {
			return 1.0000;
		} else {
			return 1.0 - (double) (this.getPopulation() - totalJobs) / (double) (this.getPopulation());
		}

	}
}
