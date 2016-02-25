package edu.onze.cal;

public abstract class Component {
	
	public abstract String getContent();
	
	public abstract String addTimeSpan(String timeStart, String timeEnd);
	
	public abstract String addTimeStart(String timeStart);
	
	public abstract String addTimeEnd(String timeEnd);
	
	public abstract String addSummary(String summary);
	
	public abstract String addDescription(String description);
	
	public abstract String addLocation(String location);
	
}
