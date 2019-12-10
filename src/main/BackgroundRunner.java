package main;

public class BackgroundRunner extends Thread {
	
	/* Instance Variables */
	private boolean isDone;
	private Model model;
	private double pauseDuration;

	/* Constructor */
	public BackgroundRunner(Model model, double pauseDuration) {
		/* Set Default Values */
		this.isDone = false;
		this.model = model;

		/* Validate Input */
		if (pauseDuration < 10 || pauseDuration > 1000) {
			pauseDuration = 500;
		} else {
			this.pauseDuration = pauseDuration;
		}
	}

	/* Ends termination of the thread */
	public void terminate() {
		isDone = true;
	}
	
	public double getPauseDuration() {
		return this.pauseDuration;
	}

	public void run() {
		/* Loop indefinetly, while possible */
		while (!isDone) {
			/* Sleep the thread for the correct duration */
			try {
				Thread.sleep((long) pauseDuration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			/* Make a move, then repeat the loop */
			model.nextDay();
		}
	}

}