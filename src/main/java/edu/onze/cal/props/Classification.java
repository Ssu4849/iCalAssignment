package edu.onze.cal.props;

public class Classification extends Property {
	
	private String content;
	
	private final boolean allowDuplicates = false;
	
	public Classification(String c) {
		this.content = c;
	}
	
	public String toString() {
		return this.content;
	}
	
	public boolean isUnique() {
		return !allowDuplicates;
	}
	
	public String getTag() {
		String tag = "";
		if (content.length() == 0) {
			tag = "";
		}
		else {
			tag = content.substring(0, content.indexOf(":") + 1);
		}
		return tag;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Property) {
			if (this.getTag().equals(((Property) o).getTag())) {
				return true;
			}
		}
		return false;
	}
}
