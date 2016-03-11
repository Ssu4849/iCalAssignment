package edu.onze.cal.props;

public class Location extends Property {
	
	private String content;
	
	public Location(String c) {
		this.content = c;
	}
	
	public String toString() {
		return this.content;
	}
}
