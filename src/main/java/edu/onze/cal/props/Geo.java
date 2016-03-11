package edu.onze.cal.props;

public class Geo extends Property {
	
	private String content;
	
	public Geo(String c) {
		this.content = c;
	}
	
	public String toString() {
		return this.content;
	}
}
