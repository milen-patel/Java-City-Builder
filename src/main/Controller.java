package main;

import Interfaces.ViewObserver;

public class Controller implements ViewObserver {
	private Model model;
	private View view;
	
	public Controller(View view, Model model) {
		this.model = model;
		this.view = view;
		this.view.addObserver(this);
	}

	@Override
	public void nextDayButtonClicked() {
		model.nextDay();
	}
}
