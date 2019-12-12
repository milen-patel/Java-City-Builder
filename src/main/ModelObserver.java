package main;

public interface ModelObserver {
	/*
	 * An enumeration to represent the types of events that can happen on the model
	 * The enumeration is held in the interface but is used in the model class'
	 * notifyObserver method In our example, the view observers the model so that it
	 * knows when to update various UI elements
	 */
	enum EventTypes {
		BALANCE_CHANGED, POPULATION_CHANGED, DAILYINCOME_CHANGED, DAY_CHANGED, BOARD_CHANGED, HAPPINESS_CHANGED, UNEMPLOYEMENT_CHANGED
	};

	/*
	 * Tells observer that the balance has changed, usually because of construction
	 * or a new day
	 */
	public void BalanceChanged();

	/*
	 * Tells observer that the population has changed, usually because of
	 * construction or demolition
	 */
	public void PopulationChanged();

	/*
	 * Tells observer that the total daily income has changed, usually because of
	 * construction or demolition
	 */
	public void DailyIncomeChanged();

	/*
	 * Tells observer that the day has changed, usually because the user has clicked
	 * the 'next day' button
	 */
	public void DayChanged();

	/*
	 * Tells observer that one or more positions on the board has changed, usually
	 * because of construction or demolition
	 */
	public void BoardChanged();
	
	/*
	 * Tells observer that the overall happiness of the game has changed
	 */
	public void HappinessChanged();
	
	/*
	 * Tells the observer that the unemployment rate has changed
	 */
	public void UnemployementChanged();
}
