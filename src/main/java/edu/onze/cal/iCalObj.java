
package edu.onze.cal;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @authors Daralyn Young, Corey Watanabe, Shengyuan Su
 * team onze
 */
public class iCalObj {

	/**
	 * carriage return & line field
	 */
	public static final String CRLF = "\r\n";

	/**
	 * These fields are required
	 */
	public static final String CALENDAR_HEADER = "BEGIN:VCALENDAR" + CRLF;
	public static final String CALENDAR_TRAILER = "END:VCALENDAR" + CRLF;
	public static final String PRODID = "PRODID:-//Google Inc//Google Calendar 70.9054//EN" + CRLF;
	public static final String VERSION = "VERSION:2.0" + CRLF;
	public static final String TIMEZONE = "X-WR-TIMEZONE:Pacific/Honolulu" + CRLF;

	/**
	 * These properties are optional
	 */
	public static String CALSCALE_GREGORIAN = "CALSCALE:GREGORIAN" + CRLF;

	/**
	 * Stores the content of this calendar object
	 */
	private StringBuilder content;

	/**
	 * identifies the file associated with this calendar object
	 */
	private File file;

	/**
	 * component list
	 */
	private List<Component> componentList = new ArrayList<Component>();

	/**
	 * Constructor for an iCalendar object with no components
	 */
	public iCalObj(File file) {
		this.file = file;
		content = new StringBuilder();
		content.append(CALENDAR_HEADER);
		content.append(PRODID);
		content.append(VERSION);
		content.append(TIMEZONE);
		content.append(CALSCALE_GREGORIAN);
	}

	/**
	 * Adds an event component to the iCalendar object
	 * 
	 * @param dateTimeStart
	 *            time the event starts (Format: YYYY-MM-DD HH:MM:SS")
	 * @param dateTimeEnd
	 *            time the event ends (Format: YYYY-MM-DD HH:MM:SS")
	 * @param summary
	 *            one line summary about event
	 * @param description
	 *            multi-lined description associated with event
	 * @param location
	 *            location of activity
	 * @return the event that has been created
	 * @throws IllegalArgumentException
	 *             if dateStart > dateEnd
	 * @throws ParseException
	 *             if date is in the incorrect format
	 */
	public Event createEvent(String dateTimeStart, String dateTimeEnd, String summary, String description,
			String location) throws ParseException, IllegalArgumentException {
		Event event = new Event();
		event.addTimeDateSpan(dateTimeStart, dateTimeEnd);
		event.addDescription(description);
		event.addSummary(summary);
		event.addLocation(location);
		getComponentList().add(event);
		return event;
	}

	/**
	 * Adds an event to the calendar
	 * 
	 * @param e
	 *            the event to add
	 */
	public void addEvent(Event e) {
		componentList.add(e);
	}

	/**
	 * @return the componentList
	 */
	public List<Component> getComponentList() {
		return componentList;
	}

	/**
	 * Will append content of calendar object with trailer field "END:VCALENDAR"
	 * 
	 * @return content of this iCalendar object
	 */
	public String toString() {
		for (Component c : componentList) {
			content.append(c.getContent());
		}
		return content.append(CALENDAR_TRAILER).toString();
	}

	/**
	 * Gets the file associated with this iCalendar object
	 */
	public File getFile() {
		return this.file;
	}

	/**
	 * @return the number of component this calendar has
	 */
	public int getComponentSize() {
		return this.componentList.size();
	}
	
	public void setFileName(File filename) {
		this.file.renameTo(filename);
	}
}
