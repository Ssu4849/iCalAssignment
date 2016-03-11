package edu.onze.cal;

import java.text.ParseException;

public abstract class Component {

	public abstract String getContent();

	public abstract String addTimeDateSpan(String dateStart, String dateEnd) throws ParseException;

	public abstract String addSummary(String summary);
	
	public abstract int getPropertySize();

}
