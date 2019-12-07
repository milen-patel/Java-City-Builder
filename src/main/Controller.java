package main;

public class Controller {
	private Model model;
	private View view;
	
	public Controller(View view, Model model) {
		this.model = model;
		this.view = view;
	}
}
