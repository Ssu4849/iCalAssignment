package edu.onze.cal;

import java.util.Comparator;

/**
 * @authors Daralyn Young, Corey Watanabe, Shengyuan Su
 */
public class ComponentComparable implements Comparator<Component>{

	@Override
	public int compare(Component o1, Component o2) {
		if (o1.getStartDate().before(o2.getStartDate())) {
			return -1;
		}
		else if (o1.getStartDate().after(o2.getStartDate())) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
}
