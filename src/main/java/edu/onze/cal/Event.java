package edu.onze.cal;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	public static final String EVENT_HEADER = "BEGIN:VEVENT";
	public static final String EVENT_TRAILER = "END:VEVENT";

	/**
	 * These parameters are optional
	 */
	public static final String DESCRIPTION_PROPERTY = "DESCRIPTION:";
	public static final String DTSTART_PROPERTY = "DTSTART:";
	public static final String DTEND_PROPERTY = "DTEND:";
	public static final String SUMMARY_PROPERTY = "SUMMARY:";
	public static final String LOCATION_PROPERTY = "LOCATION:";
	public static final String GEOGRAPHIC_LOCATION_PROPERTY = "GEO:";
	public static final String CLASSIFICATION_PROPERTY = "CLASS:";

	/**
	 * Stores the strings of classification types
	 */
	public static final String PUBLIC = "PUBLIC";
	public static final String PRIVATE = "PRIVATE";
	public static final String CONFIDENTIAL = "CONFIDENTIAL";

	/**
	 * Stores the properties of event component
	 */
	private StringBuilder props;

	/**
	 * ISO.8601.2004 format compatible with iCalendar
	 */
	public static final String ISO_8601_FORMAT = "yyyyMMdd'T'HHmmss";

	/**
	 * Date format provided by the user
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * Instantiates an empty Event component
	 */
	public Event() {
		props = new StringBuilder();
		props.append(EVENT_HEADER + CRLF);
	}

	/**
	 * @throws IllegalArgumentException
	 *             if dateEnd > dateStart
	 * @throws Parse
	 *             Exception is date is incorrect format
	 */
	@Override
	public String addTimeDateSpan(String dateStart, String dateEnd) throws ParseException {
		String dateStartLine = "";
		String dateEndLine = "";
		String dateStartParsed = "";
		String dateEndParsed = "";

		if (endDateGtrStartDate(dateStart, dateEnd)) {
			throw new IllegalArgumentException("Start date is > end date!");
		}

		dateStartParsed = parseDate(dateStart);
		dateEndParsed = parseDate(dateEnd);
		dateStartLine = DTSTART_PROPERTY + dateStartParsed + CRLF;
		dateEndLine = DTEND_PROPERTY + dateEndParsed + CRLF;
		props.append(dateStartLine);
		props.append(dateEndLine);

		return dateStartLine + dateEndLine;
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

	private boolean endDateGtrStartDate(String dateStart, String dateEnd) {
		return dateStart.compareTo(dateEnd) > 0;
	}

	@Override
	public String addSummary(String summary) {
		String returnStr;
		String sumLine = SUMMARY_PROPERTY + summary + CRLF;
		if (summary.compareTo("") == 0) {
			returnStr = "";
		} else {
			props.append(sumLine);
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
			props.append(descLine);
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
			props.append(locLine);
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
	 * @throws IllegalStateException
	 *             if geoposition is out of range
	 */
	public String addGeoPosition(String geoPosition) throws IllegalStateException {
		String returnStr = "";
		String geoPositionFormatted = "";
		String geoLine = "";
		if (geoPosition.compareTo("") != 0) {
			geoPositionFormatted = parseGeographicPosition(geoPosition);
			geoLine = GEOGRAPHIC_LOCATION_PROPERTY + geoPositionFormatted + CRLF;
		}

		if (geoLine.compareTo("") == 0) {
			returnStr = "";
		} else {
			props.append(geoLine);
			returnStr = geoLine;
		}
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
			throw new IllegalStateException("Latitude range should be [-90,90]");
		}
		if (Double.parseDouble(lonDegMinSec[0]) > 180 || Double.parseDouble(lonDegMinSec[0]) < -180) {
			throw new IllegalStateException("Longitude range should be [-180,180]");
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
	 * @see <a href="https://tools.ietf.org/html/rfc2445#section-4.8.1.3">https:
	 *      //tools.ietf.org/html/rfc2445#section-4.8.1.3</a>
	 * @param access 
	 *            "PUBLIC" = 1 / "PRIVATE" = 2 / "CONFIDENTIAL" = 3 default is "PUBLIC"
	 * @return the classification type added to the event
	 */
	public String setClassification(int access) {
		String returnStr = "";

		switch (access) {
		case 2:
			String s1 = CLASSIFICATION_PROPERTY + "PRIVATE" + CRLF;
			props.append(s1);
			returnStr = s1;
			break;
		case 3:
			String s2 = CLASSIFICATION_PROPERTY + "CONFIDENTIAL" + CRLF;
			props.append(s2);
			returnStr = s2;
			break;
		default:
			String s3 = CLASSIFICATION_PROPERTY + "PRIVATE" + CRLF;
			props.append(s3);
			returnStr = s3;
			break;
		}
		return returnStr;
	}

	/**
	 * appends event trailer tag to the event component and returns the string
	 */
	public String getContent() {
		return props.append(EVENT_TRAILER + CRLF).toString();
	}
}
