package edu.onze.cal;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.onze.cal.props.Classification;
import edu.onze.cal.props.Description;
import edu.onze.cal.props.Dtend;
import edu.onze.cal.props.Dtstart;
import edu.onze.cal.props.Geo;
import edu.onze.cal.props.Location;
import edu.onze.cal.props.UniqueProperty;
import edu.onze.cal.props.Property;
import edu.onze.cal.props.Summary;

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
	public static final String DESCRIPTION_PROPERTY_TAG = "DESCRIPTION:";
	public static final String DTSTART_PROPERTY_TAG = "DTSTART:";
	public static final String DTEND_PROPERTY_TAG = "DTEND:";
	public static final String SUMMARY_PROPERTY_TAG = "SUMMARY:";
	public static final String LOCATION_PROPERTY_TAG = "LOCATION:";
	public static final String GEOGRAPHIC_LOCATION_PROPERTY_TAG = "GEO:";
	public static final String CLASSIFICATION_PROPERTY_TAG = "CLASS:";

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
	 * Props list
	 */
	private List<Property> propList = new ArrayList<Property>();

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

		dateStartParsed = parseDate(dateStart);
		dateEndParsed = parseDate(dateEnd);
		if (endDateGtrStartDate(dateStart, dateEnd)) {
			throw new IllegalArgumentException("Start date is > end date!");
		}
		dateStartLine = DTSTART_PROPERTY_TAG + dateStartParsed + CRLF;
		dateEndLine = DTEND_PROPERTY_TAG + dateEndParsed + CRLF;

		addProperty(new Dtstart(dateStartLine));
		addProperty(new Dtend(dateEndLine));

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
		String sumLine = SUMMARY_PROPERTY_TAG + summary + CRLF;
		if (summary.compareTo("") == 0) {
			returnStr = "";
		} else {
			addProperty(new Summary(sumLine));
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
		String descLine = DESCRIPTION_PROPERTY_TAG + description + CRLF;
		if (description.compareTo("") == 0) {
			returnStr = "";
		} else {
			addProperty(new Description(descLine));
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
		String locLine = LOCATION_PROPERTY_TAG + location + CRLF;
		if (location.compareTo("") == 0) {
			returnStr = "";
		} else {
			addProperty(new Location(locLine));
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
			geoLine = GEOGRAPHIC_LOCATION_PROPERTY_TAG + geoPositionFormatted + CRLF;
			addProperty(new Geo(geoLine));
		}

		if (geoLine.compareTo("") == 0) {
			returnStr = "";
		} else {
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

		// Formula Degree(decimal) = Degree + Minute/60 + Seconds/3600
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
	 *            "PUBLIC" = 1 / "PRIVATE" = 2 / "CONFIDENTIAL" = 3 default is
	 *            "PUBLIC"
	 * @return the classification type added to the event
	 */
	public String setClassification(int access) {
		String returnStr = "";

		switch (access) {
		case 2:
			String s1 = CLASSIFICATION_PROPERTY_TAG + "PRIVATE" + CRLF;
			props.append(s1);
			returnStr = s1;
			break;
		case 3:
			String s2 = CLASSIFICATION_PROPERTY_TAG + "CONFIDENTIAL" + CRLF;
			props.append(s2);
			returnStr = s2;
			break;
		default:
			String s3 = CLASSIFICATION_PROPERTY_TAG + "PRIVATE" + CRLF;
			props.append(s3);
			returnStr = s3;
			break;
		}
		return returnStr;
	}

	/**
	 * Adds a property to the event without formatting. Use this when a line
	 * when reading from ics file
	 * 
	 * @param line
	 *            the current line that is a property
	 * @throws IllegalArgumentException
	 *             if an unique property is added twice
	 */
	public void addPropNoFormatRequired(String line) throws IllegalArgumentException {
		String propHeader = line.substring(0, line.indexOf(":") + 1);
		switch (propHeader) {

		case CLASSIFICATION_PROPERTY_TAG:
			addProperty(new Classification(line + CRLF));
			break;
		case DESCRIPTION_PROPERTY_TAG:
			addProperty(new Description(line + CRLF));
			break;
		case DTSTART_PROPERTY_TAG:
			addProperty(new Dtstart(line + CRLF));
			break;
		case DTEND_PROPERTY_TAG:
			addProperty(new Dtend(line + CRLF));
			break;
		case LOCATION_PROPERTY_TAG:
			addProperty(new Location(line + CRLF));
			break;
		case GEOGRAPHIC_LOCATION_PROPERTY_TAG:
			addProperty(new Geo(line + CRLF));
			break;
		case SUMMARY_PROPERTY_TAG:
			addProperty(new Summary(line + CRLF));
			break;
		default:
			System.err.println("Property unsupported: " + line.substring(0, (line.indexOf(":"))));
			break;
		}
	}

	/**
	 * appends event trailer tag to the event component and returns the string
	 */
	public String getContent() {
		for (Property p : propList) {
			props.append(p);
		}
		return props.append(EVENT_TRAILER + CRLF).toString();
	}

	/**
	 * @return the number of properties this event contain
	 */
	public int getPropertySize() {
		return this.propList.size();
	}

	/**
	 * Adds a property to the property list
	 */
	private void addProperty(Property property) {
		if (property instanceof UniqueProperty) {
			if (this.propList.contains(property)) {
				throw new IllegalArgumentException("Property " + property.toString() + " already exists");
			} else {
				this.propList.add(property);
			}
		} else {
			this.propList.add(property);
		}
	}
}
