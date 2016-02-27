
package edu.onze.cal;

import java.io.File;

/**
 * 
 * @author Team Onze
 */
public class iCalObj {
	
	/** carriage return & line field */
	public static final String CRLF = "\r\n";

	/** These fields are required */
	public static final String CALENDAR_HEADER = "BEGIN:VCALENDAR" + CRLF;
	public static final String CALENDAR_TRAILER = "END:VCALENDAR" + CRLF;
	public static final String PRODID = "PRODID:-//Google Inc//Google Calendar 70.9054//EN" + CRLF;
	public static final String VERSION = "VERSION:2.0" + CRLF;

	/** These properties are optional */
	public static String CALSCALE_GREGORIAN = "CALSCALE:GREGORIAN" + CRLF;

	/** Stores the content of this calendar object */
	private StringBuilder content;

	/** identifies the file associated with this calendar object */
	private File file;

	/**
	 * Instantiates an iCalendar object with no components
	 */
	public iCalObj(File fileName) {
		file = fileName;
		content = new StringBuilder();
		content.append(CALENDAR_HEADER);
		content.append(PRODID);
		content.append(VERSION);
		content.append(CALSCALE_GREGORIAN);
	}

	/**
	 * Adds an event component to the iCalendar object
	 * @param timeStart time the event starts
	 * @param timeEnd time the event ends
	 * @param summary one line summary about event
	 * @param description multi-lined description associated with event
	 * @param location location of activity
	 */
	public void addEvent(String timeStart, String timeEnd, String summary, 
						 String description, String location) {
		Event event = new Event();
		
		event.addTimeSpan(timeStart, timeEnd);
		event.addDescription(description);
		event.addLocation(location);
		event.addSummary(summary);
		
		content.append(event.getContent());
	}

	/**
	 * Will append content of calendar object with trailer field "END:VCALENDAR"
	 * @return content of this iCalendar object
	 */
	public String toString() {
		return content.append(CALENDAR_TRAILER).toString();
	}

	/**
	 * Gets the file associated with this iCalendar object
	 */
	public File getFile() {
		return this.file;
	}
}
