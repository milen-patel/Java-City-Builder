package main;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Interfaces.*;

public class View extends JPanel implements Interfaces.ModelObserver, ActionListener, LogObserver{
	/* Define instance variables */
	private JLabel moneyLabel;
	private JLabel dailyIncomeLabel;
	private JLabel populationLabel;
	private JLabel dayLabel; 
	private JTextArea logLabel;
	
	private JButton nextDayButton;
	private JButton helpButton;
	
	private Model model;
	private GridBagConstraints c;
	private BoardVisualizerWidget boardDrawer;
	private List<ViewObserver> observers;


	public View(Model model) {
		/* Encapsulate the model */
		this.model = model;
		
		/* Set up observer list */
		observers = new ArrayList<ViewObserver>();
		
		/* Set the Layout */
		this.setLayout(new GridBagLayout());
	    c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		this.setPreferredSize(new Dimension(1000, 1000));
		
		
		/* Add board visualizer widget */
		boardDrawer = new BoardVisualizerWidget(model.getBoard(), model);
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 7;
		this.add(boardDrawer, c);
		
	    c.gridx = 1;
	    this.add(new JLabel("   "), c);

		/* Add Money Label */
		moneyLabel = new JLabel("<html><b>Balance: </b>" + model.getBalance() + "</html>");
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 1;
		this.add(moneyLabel, c);
		
		/* Add daily income label */
		dailyIncomeLabel = new JLabel("<html><b>Daily Income: </b>" + model.getDailyIncome() + "</html>");
		c.gridx = 2;
		c.gridy = 1;
		this.add(dailyIncomeLabel, c);
		
		/* Add population label */
		populationLabel = new JLabel("<html><b>Population: </b>" + model.getPopulation() + "</html>");
		c.gridx = 2;
		c.gridy = 2;
		this.add(populationLabel, c);
		
		/* Add Day Label */
		dayLabel = new JLabel("<html><b>Day: </b>" + model.getDay() + "</html>");
		c.gridx = 2;
		c.gridy = 3;
		this.add(dayLabel, c);
		
		/* Add Next Day Button */
		nextDayButton = new JButton("Next Day");
		nextDayButton.setActionCommand("NextDayButton");
		nextDayButton.addActionListener(this);
		c.gridx = 2;
		c.gridy = 4;
		this.add(nextDayButton, c);
		
		/* Add help button */
		helpButton = new JButton("Help");
		helpButton.setActionCommand("HelpButton");
		c.gridx = 2;
		c.gridy = 5;
		this.add(helpButton, c);
		
		/* Set up log visualizer */
		logLabel = new JTextArea(40,22);
		logLabel.setEditable(false);
		c.gridx = 2;
		c.gridy = 6;
		this.add(new JScrollPane(logLabel), c);
		
		/* Add the View as a ModelObserver */
		model.addObserver(this);
		/* Add the View as a log Observer */
		EventLog.getEventLog().addObserver(this);
	
	}

	@Override
	public void BalanceChanged() {
		System.out.println("View Has Been Notified That Model's Balance Has Changed");
		moneyLabel.setText("<html><b>Balance: </b>" + model.getBalance() + "</html>");
	}

	@Override
	public void PopulationChanged() {
		System.out.println("<html><b>Population: </b>" + model.getPopulation() + "</html>");
		populationLabel.setText(("<html><b>Population: </b>" + model.getPopulation() + "</html>"));
	}

	@Override
	public void DailyIncomeChanged() {
		System.out.println("View Has Been Notified That Model's Daily Income Has Changed");
		dailyIncomeLabel.setText("<html><b>Daily Income: </b>" + model.getDailyIncome() + "</html>");
	}
	
	@Override
	public void DayChanged() {
		System.out.println("View Has Been Notified That Model's Day Has Changed");
		dayLabel.setText("<html><b>Day: </b>" + model.getDay() + "</html>");

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().contentEquals("NextDayButton")) {
			notifyObservers(ViewObserver.ViewEvent.NEXTDAYBUTTONCLICKED);
		}
	}
	
	/* Implementation of Observer Methods */
	public void addObserver(ViewObserver o) {
		this.observers.add(o);
	}
	public void removeObserver(ViewObserver o) {
		this.observers.remove(o);
	}
	public void notifyObservers(ViewObserver.ViewEvent e) {
		for (ViewObserver o : observers) {
			if (e == ViewObserver.ViewEvent.NEXTDAYBUTTONCLICKED) {
				o.nextDayButtonClicked();
			}
		}
	}

	@Override
	public void newLogEntry(String entry) {
		logLabel.append(entry + "\n\n");
	}

	@Override
	public void BoardChanged() {
		boardDrawer.repaint();
	}



}
