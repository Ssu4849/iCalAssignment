package edu.onze.cal.props;

public class Description extends Property {
	
	private String content;
	
	public Description(String c) {
		this.content = c;
	}
	
	public String toString() {
		return this.content;
	}
}
