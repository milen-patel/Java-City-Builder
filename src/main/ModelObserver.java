package main;

public interface ModelObserver {
	enum EventTypes {BALANCE_CHANGED, POPULATION_CHANGED, DAILYINCOME_CHANGED, DAY_CHANGED, BOARD_CHANGED};
	public void BalanceChanged();
	public void PopulationChanged();
	public void DailyIncomeChanged();
	public void DayChanged();
	public void BoardChanged();
}
