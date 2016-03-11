/**
 * 
 */
package edu.onze.calTest;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.onze.cal.Event;

/**
 * @author Team Onze
 *
 */
public class EventComponentTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void testAddSummary() {
		Event event = new Event();

		String summary = "This is a test summary";

		assertEquals(Event.SUMMARY_PROPERTY_TAG + summary + Event.CRLF, event.addSummary(summary));
	}

	@Test
	public void testAddLocation() {
		Event event = new Event();

		String location = "Campus Center";

		assertEquals(Event.LOCATION_PROPERTY_TAG + location + Event.CRLF, event.addLocation(location));
	}

	@Test
	public void testAddDateTime() {
		Event event = new Event();

		String dateTimeStart = "2000-01-01 00:00:00";
		String dateTimeEnd = "2000-01-01 00:00:01";

		String formattedDateTimeStart = "20000101T000000";
		String formattedDateTimeEnd = "20000101T000001";

		try {
			assertEquals(Event.DTSTART_PROPERTY_TAG + formattedDateTimeStart + Event.CRLF + Event.DTEND_PROPERTY_TAG
					+ formattedDateTimeEnd + Event.CRLF, event.addTimeDateSpan(dateTimeStart, dateTimeEnd));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testAddDateTimeArgumentException() throws ParseException {
		Event event = new Event();
		String dateTimeStart = "2000-01-01 00:00:00";
		String dateTimeEnd = "1999-01-01 00:00:01";

		exception.expect(IllegalArgumentException.class);
		event.addTimeDateSpan(dateTimeStart, dateTimeEnd);
	}
	
	@Test
	public void testAddDateTimeFormatException() throws ParseException {
		Event event = new Event();
		String dateTimeStart = "2000-01-0A 00:00:00";
		String dateTimeEnd = "2000-08-01 00:00:01";

		exception.expect(ParseException.class);
		event.addTimeDateSpan(dateTimeStart, dateTimeEnd);
	}
}
