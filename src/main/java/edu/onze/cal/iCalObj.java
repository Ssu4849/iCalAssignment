
package edu.onze.cal;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Team Onze
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

	/**
	 * These properties are optional
	 */
	public static String CALSCALE_GREGORIAN = "CALSCALE:GREGORIAN" + CRLF;

	/**
	 * ISO.8601.2004 format compatible with iCalendar
	 */
	public static final String ISO_8601_FORMAT = "yyyyMMdd'T'HHmmss";

	/**
	 * Date format provided by the user
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * Stores the content of this calendar object
	 */
	private StringBuilder content;

	/**
	 * identifies the file associated with this calendar object
	 */
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
	 * 
	 * @param timeStart
	 *            time the event starts (Format: YYYY-MM-DD HH:MM:SS")
	 * @param timeEnd
	 *            time the event ends (Format: YYYY-MM-DD HH:MM:SS")
	 * @param summary
	 *            one line summary about event
	 * @param description
	 *            multi-lined description associated with event
	 * @param location
	 *            location of activity
	 */
	public void addEvent(String dateStart, String dateEnd, String summary, String description, String location) {
		Event event = new Event();
		String dateStartParsed = "";
		String dateEndParsed = "";

		event.addDescription(description);
		event.addLocation(location);
		event.addSummary(summary);
		
		try {
			dateStartParsed = parseDate(dateStart);
			dateEndParsed = parseDate(dateEnd);
			event.addDateSpan(dateStartParsed, dateEndParsed);
		} catch (IllegalArgumentException e) {
			System.err.println("Start date is > end date!");
		} catch (ParseException p) {
			System.err.println("Date is incorrect format. Usage: 'YYYY-MM-DD HH:MM:SS'");
		}

		content.append(event.getContent());
	}

	/**
	 * Formats the date string into ISO_8601.2004 format
	 * 
	 * @param date
	 *            the date to format
	 * @return the formatted date
	 */
	private String parseDate(String date) throws ParseException {
		String returnStr = "";
		DateFormat originalFormat = new SimpleDateFormat(DATE_FORMAT);
		DateFormat targetFormat = new SimpleDateFormat(ISO_8601_FORMAT);
		Date originalDate = null;

		originalDate = originalFormat.parse(date);
		returnStr = targetFormat.format(originalDate);
		return returnStr;
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
