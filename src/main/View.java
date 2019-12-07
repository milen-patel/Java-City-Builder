package main;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class View extends JPanel implements Interfaces.ModelObserver{
	/* Define instance variables */
	private JLabel moneyLabel;
	private Model model;
	private GridBagConstraints c;
	private BoardVisualizerWidget boardDrawer;
	


	public View(Model model) {
		/* Encapsulate the model */
		this.model = model;
		
		/* Set the Layout */
		this.setLayout(new GridBagLayout());
	    c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		this.setPreferredSize(new Dimension(1000, 1000));
		
		/* Add Money Label */
		moneyLabel = new JLabel("Balance: " + model.getBalance());
		c.gridx = 1;
		c.gridy = 0;
		this.add(moneyLabel, c);
		
		/* Add the View as a ModelObserver */
		model.addObserver(this);
		
		/* Add board visualizer widget */
		boardDrawer = new BoardVisualizerWidget(model.getBoard());
		c.gridx = 0;
		c.gridy = 0;
		this.add(boardDrawer, c);
	}

	@Override
	public void BalanceChanged() {
		System.out.println("View Has Been Notified That Model's Balance Has Changed");
		moneyLabel.setText("Balance: " + model.getBalance());
	}

}
