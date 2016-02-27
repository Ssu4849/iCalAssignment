package edu.onze.cal;

/**
 * @author Team Onze
 */
public class Event extends Component {
	
	/** carriage return & line field */
	public static final String CRLF = "\r\n";

	/** These fields are required */
	public static final String EVENT_HEADER = "BEGIN:VEVENT" + CRLF;
	public static final String EVENT_TRAILER = "END:VEVENT" + CRLF;
	
	/** These parameters are optional */
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
	
	public String addTimeSpan(String timeStart, String timeEnd) {
		return addTimeStart(timeStart) + addTimeEnd(timeEnd);
	}
	
	public String addTimeStart(String timeStart) {
		return content.append(DTSTART_PROPERTY + timeStart + CRLF).toString();
	}
	
	public String addTimeEnd(String timeEnd) {
		return content.append(DTEND_PROPERTY + timeEnd + CRLF).toString();
	}
	
	@Override
	public String addSummary(String summary) {
		return content.append(SUMMARY_PROPERTY + summary + CRLF).toString();
	}
	
	@Override
	public String addDescription(String description) {
		String returnStr;
		if (description.compareTo("") == 0) {
			returnStr = "";
		}
		else {
			returnStr = content.append(DESCRIPTION_PROPERTY + description + CRLF).toString();
		}
		return returnStr;
	}
	
	public String addLocation(String location) {
		return content.append(LOCATION_PROPERTY + location + CRLF).toString();
	}
	
	/**
	 * appends event trailer tag to the event component
	 * and returns the string
	 */
	public String getContent() {
		return content.append(EVENT_TRAILER).toString();
	}
}
