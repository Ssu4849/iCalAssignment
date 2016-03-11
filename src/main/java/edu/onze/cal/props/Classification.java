package edu.onze.cal.props;

public class Classification extends Property {
	
	private String content;
	
	public Classification(String c) {
		this.content = c;
	}
	
	public String toString() {
		return this.content;
	}
}
