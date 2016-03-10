
package edu.onze.cal;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	 * component list
	 */
	List<Component> componentList = new ArrayList<Component>();
	
	/**
	 * Instantiates an iCalendar object with no components
	 */
	public iCalObj(File file) {
		this.file = file;
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
	public void addEvent(String dateStart, String dateEnd, String summary, String description, String location,
			String geoPosition) {
		Event event = new Event();
		String dateStartParsed = "";
		String dateEndParsed = "";

		event.addDescription(description);
		event.addSummary(summary);

		if (location.compareTo("") != 0) {
			event.addLocation(location);
		}

		if (geoPosition.compareTo("") != 0) {
			try {
				String formattedPosition = parseGeographicPosition(geoPosition);
				event.addGeoPosition(formattedPosition);
			} catch (IllegalArgumentException e) {
				System.err.println("Longitude range [-180,180] Latitude range [-90, 90]");
			}
		}

		try {
			dateStartParsed = parseDate(dateStart);
			dateEndParsed = parseDate(dateEnd);
			event.addTimeDateSpan(dateStartParsed, dateEndParsed);
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
	 * @throws ParseException
	 *             if input date is not in the correct format
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
	 * Formats the geographic position
	 * 
	 * @param position
	 *            the position to format
	 * @return the formatted position
	 * @throws illegalArgumentException
	 *             if longitude/latitude degree is out of range
	 */
	private String parseGeographicPosition(String position) {
		String returnStr = "";

		String latlon[] = position.split(" ");
		String latDegMinSec[] = latlon[0].split(",");
		String lonDegMinSec[] = latlon[1].split(",");

		// Checks if -90<latitude<90 and -180<longitude<180
		if (Double.parseDouble(latDegMinSec[0]) > 90 || Double.parseDouble(latDegMinSec[0]) < -90) {
			throw new IllegalArgumentException();
		}
		if (Double.parseDouble(lonDegMinSec[0]) > 180 || Double.parseDouble(lonDegMinSec[0]) < -180) {
			throw new IllegalArgumentException();
		}

		Double degreeLat = Double.parseDouble(latDegMinSec[0]) + Double.parseDouble(latDegMinSec[1]) / 60
				+ Double.parseDouble(latDegMinSec[2]) / 3600;
		Double degreeLon = Double.parseDouble(lonDegMinSec[0]) + Double.parseDouble(lonDegMinSec[1]) / 60
				+ Double.parseDouble(lonDegMinSec[2]) / 3600;

		String truncDegLat = new BigDecimal(degreeLat).setScale(6, BigDecimal.ROUND_FLOOR).toString();
		String truncDegLon = new BigDecimal(degreeLon).setScale(6, BigDecimal.ROUND_FLOOR).toString();

		returnStr = truncDegLat + ";" + truncDegLon;
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
