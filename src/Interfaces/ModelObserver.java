package Interfaces;

public interface ModelObserver {
	enum EventTypes {BALANCE_CHANGED, POPULATION_CHANGED, DAILYINCOME_CHANGED};
	public void BalanceChanged();
	public void PopulationChanged();
	public void DailyIncomeChanged();
}
