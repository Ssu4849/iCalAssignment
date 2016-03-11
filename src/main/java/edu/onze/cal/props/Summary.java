package edu.onze.cal.props;

public class Summary extends Property {
	
	private String content;
	
	public Summary(String c) {
		this.content = c;
	}
	
	public String toString() {
		return this.content;
	}
}
