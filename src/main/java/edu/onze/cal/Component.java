package edu.onze.cal;

import java.text.ParseException;

import java.util.Date;

import edu.onze.cal.props.Geo;


import edu.onze.cal.props.Geo;
  
/**
 * @authors Daralyn Young, Corey Watanabe, Shengyuan Su
 */
public abstract class Component {

	public abstract String getContent();

	public abstract String addTimeDateSpan(String dateStart, String dateEnd) throws ParseException;

	public abstract String addSummary(String summary);
	
	public abstract String addComment(String comment);
	
	public abstract int getPropertySize(); 
	
	public abstract Date getStartDate();
	
	public abstract Geo getGeographicPosition();

}
