package main;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import boardPieces.ApartmentPiece;
import boardPieces.HousePiece;
import boardPieces.RoadPiece;

public class View extends JPanel implements main.ModelObserver, ActionListener, LogObserver{
	/* Define instance variables */
	private JLabel moneyLabel;
	private JLabel dailyIncomeLabel;
	private JLabel populationLabel;
	private JLabel dayLabel; 
	private JLabel happinessLabel;
	private JTextArea logLabel;
	
	private JButton nextDayButton;
	private JButton helpButton;
	private JButton playButton;
	
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
		
		
		/* Add board visualizer widget */
		boardDrawer = new BoardVisualizerWidget(model);
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 10;
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
		
		/* Add happiness label */
		happinessLabel = new JLabel("<html><b>Happiness: </b>" + model.getHappiness() + "</html>");
		c.gridx = 2;
		c.gridy = 4;
		this.add(happinessLabel, c);
		
		/* Add Next Day Button */
		nextDayButton = new JButton("Next Day");
		nextDayButton.setActionCommand("NextDayButton");
		nextDayButton.addActionListener(this);
		c.gridx = 2;
		c.gridy = 5;
		this.add(nextDayButton, c);
		
		/* Add help button */
		helpButton = new JButton("Help");
		helpButton.setActionCommand("HelpButton");
		helpButton.addActionListener(this);
		c.gridx = 2;
		c.gridy = 6;
		this.add(helpButton, c);
		
		/* Set up log visualizer */
		logLabel = new JTextArea(20,22);
		logLabel.setEditable(false);
		c.gridx = 2;
		c.gridy = 7;
		this.add(new JScrollPane(logLabel), c);
		
		/* Add the View as a ModelObserver */
		model.addObserver(this);
		/* Add the View as a log Observer */
		EventLog.getEventLog().addObserver(this);
	
	}

	@Override
	public void BalanceChanged() {
		System.out.println("View Has Been Notified That Model's Balance Has Changed");
		moneyLabel.setText("<html><b>Balance: </b>" + round(model.getBalance(),2) + "</html>");
	}

	@Override
	public void PopulationChanged() {
		System.out.println("<html><b>Population: </b>" + model.getPopulation() + "</html>");
		populationLabel.setText(("<html><b>Population: </b>" + model.getPopulation() + "</html>"));
	}

	@Override
	public void DailyIncomeChanged() {
		System.out.println("View Has Been Notified That Model's Daily Income Has Changed");
		dailyIncomeLabel.setText("<html><b>Daily Income: </b>" + round(model.getDailyIncome(),2) + "</html>");
	}
	
	@Override
	public void DayChanged() {
		System.out.println("View Has Been Notified That Model's Day Has Changed");
		dayLabel.setText("<html><b>Day: </b>" + model.getDay() + "</html>");

	}
	
	@Override
	public void HappinessChanged() {
		happinessLabel.setText("<html><b>Happiness: </b>" + round(model.getHappiness(),2) + "</html>");
	}


	@Override
	//TODO: Make the help button more useful
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().contentEquals("NextDayButton")) {
			model.nextDay();
		} else if (e.getActionCommand().contentEquals("HelpButton")) {
			EventLog.getEventLog().addEntry("--------------------------------");
			EventLog.getEventLog().addEntry("Welcome to City Builder");
			EventLog.getEventLog().addEntry("Current Costs: ");
			EventLog.getEventLog().addEntry("|--> House: $" + View.round(HousePiece.costToConstruct, 2));
			EventLog.getEventLog().addEntry("|--> Apartment: $" + View.round(ApartmentPiece.costToConstruct, 2));
			EventLog.getEventLog().addEntry("|--> Road: $" + View.round(RoadPiece.costToConstruct, 2));
			EventLog.getEventLog().addEntry("|--> Demolish: $" + View.round(Model.COST_TO_DEMOLISH, 2));
			EventLog.getEventLog().addEntry("--------------------------------");
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

	/* Internal helper method used for rounding doubles when displaying UI elements */
	//TODO: Make this return a string an add commas
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	

}
