package edu.onze.cal.props;

public class Dtend extends Property implements UniqueProperty {

	private String content;

	public Dtend(String c) {
		this.content = c;
	}

	public String toString() {
		return this.content;
	}

	public String getContent() {
		if (content != null) {
			return content.substring(content.indexOf(":") + 1, content.length());
		}
		return "";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Property) {
			if (this.toString().equals(((Property) o).toString())) {
				return true;
			}
		}
		return false;
	}
}
