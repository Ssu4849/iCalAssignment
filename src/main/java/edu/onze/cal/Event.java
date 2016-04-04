package edu.onze.cal;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.onze.cal.props.Classification;
import edu.onze.cal.props.Comment;
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
 * @authors Daralyn Young, Corey Watanabe, Shengyuan Su
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
	public static final String DTSTART_PROPERTY = "DTSTART:";
	public static final String DTEND_PROPERTY = "DTEND:";

	/**
	 * These properties are optional
	 */
	public static final String DESCRIPTION_PROPERTY = "DESCRIPTION:";
	public static final String SUMMARY_PROPERTY = "SUMMARY:";
	public static final String LOCATION_PROPERTY = "LOCATION:";
	public static final String GEOGRAPHIC_LOCATION_PROPERTY = "GEO:";
	public static final String CLASSIFICATION_PROPERTY = "CLASS:";
	public static final String COMMENT_PROPERTY = "COMMENT:";

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
	 * DateTime the event starts
	 */
	private Date dateTimeStart;

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
	 * Adds the start and end time of the event. Only accepts one date format
	 * for now: yyyy-MM-dd HH:mm:ss
	 * 
	 * @throws IllegalArgumentException
	 *             if dateEnd > dateStart
	 * @throws Parse
	 *             Exception is date is incorrect format
	 */
	@Override
	public String addTimeDateSpan(String dateStart, String dateEnd) throws ParseException {
		String dateStartParsed = parseDate(dateStart);
		String dateEndParsed = parseDate(dateEnd);
		if (endDateGtrStartDate(dateStart, dateEnd)) {
			throw new IllegalArgumentException("Start date is > end date!");
		}
		String dateStartLine = DTSTART_PROPERTY + dateStartParsed + CRLF;
		String dateEndLine = DTEND_PROPERTY + dateEndParsed + CRLF;

		addProperty(new Dtstart(dateStartLine));
		addProperty(new Dtend(dateEndLine));

		// sets the start date of this event
		SimpleDateFormat ISOFORMAT = new SimpleDateFormat(DATE_FORMAT);
		this.dateTimeStart = ISOFORMAT.parse(dateStart);

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
		DateFormat originalFormat = new SimpleDateFormat(DATE_FORMAT);
		DateFormat targetFormat = new SimpleDateFormat(ISO_8601_FORMAT);
		Date originalDate = null;
		originalDate = originalFormat.parse(date);
		return targetFormat.format(originalDate);
	}

	/**
	 * Compares end date with starting date. Assumes that the date format is
	 * YYYY-MM-DD HH:MM:SS.
	 * 
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	private boolean endDateGtrStartDate(String dateStart, String dateEnd) {
		return dateStart.compareTo(dateEnd) > 0;
	}

	@Override
	public String addSummary(String summary) {
		String sumLine = "";
		if (summary.trim().compareTo("") == 0) {
			sumLine = SUMMARY_PROPERTY + "(No Title)" + CRLF;
		} else {
			sumLine = SUMMARY_PROPERTY + summary + CRLF;
		}
		addProperty(new Summary(sumLine));
		return sumLine;
	}

	/**
	 * Adds a description to the event.
	 * 
	 * @param description
	 *            the description of the event
	 * @return the line to add to the ics file under event component
	 */
	public String addDescription(String description) {
		String descLine = "";
		if (description.trim().compareTo("") != 0) {
			descLine = DESCRIPTION_PROPERTY + description + CRLF;
			addProperty(new Description(descLine));
		}
		return descLine;
	}

	/**
	 * Adds a location to the event. The location can be a conference room, etc
	 * 
	 * @param location
	 *            the location of the event
	 * @return the line to add to the ics file under event component
	 */
	public String addLocation(String location) {
		String locLine = "";
		if (location.trim().compareTo("") != 0) {
			locLine = LOCATION_PROPERTY + location + CRLF;
			addProperty(new Location(locLine));
		}
		return locLine;
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
		String geoLine = "";
		if (geoPosition.compareTo("") != 0) {
			String geoPositionFormatted = parseGeographicPosition(geoPosition);
			geoLine = GEOGRAPHIC_LOCATION_PROPERTY + geoPositionFormatted + CRLF;
			addProperty(new Geo(geoLine));
		}
		return geoLine;
	}

	/**
	 * @see <a href="https://tools.ietf.org/html/rfc5545#section-3.8.1.4">https:
	 *      //tools.ietf.org/html/rfc5545#section-3.8.1.4</a>
	 * @param access
	 *            "PUBLIC" = 1 / "PRIVATE" = 2 / "CONFIDENTIAL" = 3 default is
	 *            "PUBLIC"
	 * @return the classification type added to the event
	 */
	public String addComment(String comment) {
		String commentLine = "";
		if (comment.trim().compareTo("") != 0) {
			commentLine = COMMENT_PROPERTY + comment + CRLF;
			addProperty(new Comment(commentLine));
		}
		return commentLine;
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

		Double degreeLat = convertToDegrees(Double.parseDouble(latDegMinSec[0]), Double.parseDouble(latDegMinSec[1]),
				Double.parseDouble(latDegMinSec[2]));
		Double degreeLon = convertToDegrees(Double.parseDouble(lonDegMinSec[0]), Double.parseDouble(lonDegMinSec[1]),
				Double.parseDouble(lonDegMinSec[2]));

		String truncDegLat = new BigDecimal(degreeLat).setScale(6, BigDecimal.ROUND_FLOOR).toString();
		String truncDegLon = new BigDecimal(degreeLon).setScale(6, BigDecimal.ROUND_FLOOR).toString();

		returnStr = truncDegLat + ";" + truncDegLon;
		return returnStr;
	}

	/**
	 * @param d
	 *            degree
	 * 
	 * @param m
	 *            minute
	 * 
	 * @param s
	 *            second
	 * 
	 * @return
	 */
	private double convertToDegrees(double d, double m, double s) {
		return (d + m / 60 + s / 3600);
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
			String s1 = CLASSIFICATION_PROPERTY + "PRIVATE" + CRLF;
			addProperty(new Classification(s1));
			returnStr = s1;
			break;
		case 3:
			String s2 = CLASSIFICATION_PROPERTY + "CONFIDENTIAL" + CRLF;
			addProperty(new Classification(s2));
			returnStr = s2;
			break;
		default:
			String s3 = CLASSIFICATION_PROPERTY + "PRIVATE" + CRLF;
			addProperty(new Classification(s3));
			returnStr = s3;
			break;
		}
		return returnStr;
	}

	/**
	 * Adds a property to the event without formatting. Use this when reading
	 * from ics file
	 * 
	 * @param line
	 *            the current line that is a property
	 * @throws IllegalArgumentException
	 *             if an unique property is added twice
	 */
	public boolean addPropNoFormatRequired(String line) throws IllegalArgumentException {
		boolean isValid = true;
		String propHeader = line.substring(0, line.indexOf(":") + 1);
		switch (propHeader) {

		case CLASSIFICATION_PROPERTY:
			addProperty(new Classification(line + CRLF));
			break;
		case DESCRIPTION_PROPERTY:
			addProperty(new Description(line + CRLF));
			break;
		case DTSTART_PROPERTY:
			// sets the start date of this event
			SimpleDateFormat ISOFORMAT = new SimpleDateFormat(ISO_8601_FORMAT);
			try {
				this.dateTimeStart = ISOFORMAT.parse(line.substring((line.indexOf(":") + 1), line.length()));
				addProperty(new Dtstart(line + CRLF));
			} catch (ParseException e) {
				System.err.println("Error parsing date: " + line.substring((line.indexOf(":") + 1), line.length()));
			}
			break;
		case DTEND_PROPERTY:
			addProperty(new Dtend(line + CRLF));
			break;
		case LOCATION_PROPERTY:
			addProperty(new Location(line + CRLF));
			break;
		case GEOGRAPHIC_LOCATION_PROPERTY:
			addProperty(new Geo(line + CRLF));
			break;
		case SUMMARY_PROPERTY:
			addProperty(new Summary(line + CRLF));
			break;
		case COMMENT_PROPERTY:
			addProperty(new Comment(line + CRLF));
			break;
		default:
			System.err.println("Property unsupported: " + line.substring(0, (line.indexOf(":"))));
			isValid = false;
			break;
		}
		return isValid;
	}

	/**
	 * Adds a property to the property list
	 */
	private void addProperty(Property property) {
		if (property instanceof UniqueProperty && this.propList.contains(property)) {
			throw new IllegalArgumentException("Property " + property.toString() + " already exists");
		}
		this.propList.add(property);
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
	 * @return the date of this event
	 */
	public Date getStartDate() {
		return this.dateTimeStart;
	}

	/**
	 * Returns the instance of geographic position associated with this event
	 */
	public Geo getGeographicPosition() {
		for (Property p : this.propList) {
			if (p instanceof Geo) {
				return (Geo) p;
			}
		}
		return null;
	}
}
