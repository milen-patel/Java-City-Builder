package main;

import java.util.ArrayList;
import java.util.List;

import Interfaces.LogObserver;

public class EventLog {

	private List<String> events;
	private List<LogObserver> observers;
	private static EventLog eventLog;

	
	private EventLog() {
		events = new ArrayList<String>();
		observers = new ArrayList<LogObserver>();
	}
	
	public static EventLog getEventLog() {
		if (eventLog == null) {
			eventLog = new EventLog();
			return eventLog;
		} else {
			return eventLog;
		}
	}
	
	public void addEntry(String entry) {
		if (entry == null) { throw new RuntimeException("Null string passed to log addEntry()"); }
		events.add(entry);
		notifyObservers(entry);
	}
	
	
	public List<String> getLog() {
		return this.events;
	}
	
	/* Observable Methods */
	public void addObserver(LogObserver o) {
		observers.add(o);
	}
	public void removeObserver(LogObserver o) {
		observers.remove(o);
	}
	public void notifyObservers(String s) {
		for (LogObserver o: observers) {
			o.newLogEntry(s);
		}
	}
	
}
