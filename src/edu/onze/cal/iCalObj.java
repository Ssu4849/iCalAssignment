package edu.onze.cal;

import java.io.File;

/**
 * 
 * @author Team Onze
 * @to-do: Add event component functionality
 */
public class iCalObj {

	/** These fields are required */
	public static final String CALENDAR_HEADER = "BEGIN:VCALENDAR\r\n";
	public static final String CALENDAR_TRAILER = "END:VCALENDAR\r\n";
	public static final String PRODID = "PRODID:-//Google Inc//Google Calendar 70.9054//EN\r\n";
	public static final String VERSION = "VERSION:2.0\r\n";

	/** These properties are optional */
	public static String CALSCALE_GREGORIAN = "CALSCALE:GREGORIAN\r\n";

	/** Stores the content of this calendar object */
	StringBuilder content;

	/** identifies the file associated with this calendar object */
	File file;

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
	 */
	public void addEvent() {
		// TODO
	}

	/**
	 * Will append content of calendar object with trailer field "END:VCALENDAR"
	 * 
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
