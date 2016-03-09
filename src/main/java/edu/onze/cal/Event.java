package edu.onze.cal;

/**
 * This class defines the VEVENT component
 * 
 * @author Team Onze
 */
public class Event extends Component {

	/**
	 * carriage return & line field
	 */
	public static final String CRLF = "\r\n";

	/**
	 * These fields are required
	 */
	public static final String EVENT_HEADER = "BEGIN:VEVENT" + CRLF;
	public static final String EVENT_TRAILER = "END:VEVENT" + CRLF;

	/**
	 * These parameters are optional
	 */
	public static final String DESCRIPTION_PROPERTY = "DESCRIPTION:";
	public static final String DTSTART_PROPERTY = "DTSTART:";
	public static final String DTEND_PROPERTY = "DTEND:";
	public static final String SUMMARY_PROPERTY = "SUMMARY:";
	public static final String LOCATION_PROPERTY = "LOCATION:";

	/** Stores the content of this calendar object */
	private StringBuilder content;

	/**
	 * Instantiates an empty Event component
	 */
	public Event() {
		content = new StringBuilder();
		content.append(EVENT_HEADER);
	}

	@Override
	public String addDateSpan(String dateStart, String dateEnd) {
		String dateStartLine = "";
		String dateEndLine = "";

		if (dateStart.compareTo(dateEnd) > 0) {
			throw new IllegalArgumentException();
		} else {
			dateStartLine = DTSTART_PROPERTY + dateStart + CRLF;
			dateEndLine = DTEND_PROPERTY + dateEnd + CRLF;
			content.append(dateStartLine);
			content.append(dateEndLine);
		}
		return dateStartLine + dateEndLine;
	}

	@Override
	public String addSummary(String summary) {
		String returnStr;
		String sumLine = SUMMARY_PROPERTY + summary + CRLF;
		if (summary.compareTo("") == 0) {
			returnStr = "";
		} else {
			content.append(sumLine);
			returnStr = sumLine;
		}
		return returnStr;

	}

	/**
	 * Adds a description to the event.
	 * 
	 * @param description
	 *            the description of the event
	 * @return the line to add to the ics file under event component
	 */
	public String addDescription(String description) {
		String returnStr;
		String descLine = DESCRIPTION_PROPERTY + description + CRLF;
		if (description.compareTo("") == 0) {
			returnStr = "";
		} else {
			content.append(descLine);
			returnStr = descLine;
		}
		return returnStr;
	}

	/**
	 * Adds a location to the event. The location can be a conference room, etc
	 * 
	 * @param location
	 *            the location of the event
	 * @return the line to add to the ics file under event component
	 */
	public String addLocation(String location) {
		String returnStr;
		String locLine = LOCATION_PROPERTY + location + CRLF;
		if (location.compareTo("") == 0) {
			returnStr = "";
		} else {
			content.append(locLine);
			returnStr = locLine;
		}
		return returnStr;
	}
	
	/**
	 * Adds a geographic position to the event. 
	 * 
	 * @param location
	 *            the location of the event
	 * @return the line to add to the ics file under event component
	 */
	public String addGeoPosition(String position) {
		String returnStr = "";
		String geoLine = LOCATION_PROPERTY + position + CRLF;
		if (geoLine.compareTo("") == 0) {
			returnStr = "";
		} else {
			content.append(geoLine);
			returnStr = geoLine;
		}
		System.out.println(geoLine);
		return returnStr;
	}

	/**
	 * appends event trailer tag to the event component and returns the string
	 */
	public String getContent() {
		return content.append(EVENT_TRAILER).toString();
	}
}
