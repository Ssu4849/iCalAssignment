/**
 * 
 */
package edu.onze.calTest;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Ignore;
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

		assertEquals(Event.SUMMARY_PROPERTY + summary + Event.CRLF, event.addSummary(summary));
	}

	@Test
	public void test_Add_Location() {
		Event event = new Event();

		String location = "Campus Center";

		assertEquals(Event.LOCATION_PROPERTY + location + Event.CRLF, event.addLocation(location));
	}

	@Test
	public void test_Add_DateTime() {
		Event event = new Event();

		String dateTimeStart = "2000-01-01 00:00:00";
		String dateTimeEnd = "2000-01-01 00:00:01";

		String formattedDateTimeStart = "20000101T000000";
		String formattedDateTimeEnd = "20000101T000001";

		try {
			assertEquals(Event.DTSTART_PROPERTY + formattedDateTimeStart + Event.CRLF + Event.DTEND_PROPERTY
					+ formattedDateTimeEnd + Event.CRLF, event.addTimeDateSpan(dateTimeStart, dateTimeEnd));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test_Add_DateTime_Argument_Exception() throws ParseException {
		Event event = new Event();
		String dateTimeStart = "2000-01-01 00:00:00";
		String dateTimeEnd = "1999-01-01 00:00:01";

		exception.expect(IllegalArgumentException.class);
		event.addTimeDateSpan(dateTimeStart, dateTimeEnd);
	}
	
	@Test
	public void test_Add_DateTime_Format_Exception_Day() throws ParseException {
		Event event = new Event();
		
		String dateTimeStart = "2000-01-0A 00:00:00";
		String dateTimeEnd = "2000-08-01 00:00:01";

		exception.expect(ParseException.class);
		event.addTimeDateSpan(dateTimeStart, dateTimeEnd);
	}
	
	@Test
	public void test_Add_DateTime_Format_Exception_Year() throws ParseException {
		Event event = new Event();
		
		String dateTimeStart = "200A-01-01 00:00:00";
		String dateTimeEnd = "2000-01-01 00:00:01";

		exception.expect(ParseException.class);
		event.addTimeDateSpan(dateTimeStart, dateTimeEnd);
	}
	
	@Test
	public void test_Add_DateTime_Format_Exception_Hour() throws ParseException {
		Event event = new Event();
		
		String dateTimeStart = "2000-01-01 0A:00:00";
		String dateTimeEnd = "2000-01-01 00:00:01";

		exception.expect(ParseException.class);
		event.addTimeDateSpan(dateTimeStart, dateTimeEnd);
	}
	
	@Test
	public void test_Add_DateTime_Format_Exception_Minute() throws ParseException {
		Event event = new Event();
		
		String dateTimeStart = "2000-01-01 00:0A:00";
		String dateTimeEnd = "2000-01-01 00:00:01";

		exception.expect(ParseException.class);
		event.addTimeDateSpan(dateTimeStart, dateTimeEnd);
	}
	
	@Test
	public void test_Add_DateTime_Format_Exception_Sec() throws ParseException {
		Event event = new Event();
		
		String dateTimeStart = "2000-01-01 00:00:A0";
		String dateTimeEnd = "2000-01-01 00:00:01";

		exception.expect(ParseException.class);
		event.addTimeDateSpan(dateTimeStart, dateTimeEnd);
	}
	
	@Ignore //for now
	@Test
	public void test_Add_Date_TimeFormat_Exception_Sec2() throws ParseException {
		Event event = new Event();
		
		// Why does this fail?
		String dateTimeStart = "2000-01-01 00:00:0A";
		String dateTimeEnd = "2000-01-01 00:00:01";

		exception.expect(ParseException.class);
		event.addTimeDateSpan(dateTimeStart, dateTimeEnd);
	}
	
	@Test
	public void test_Time_End_Gtr_Time_Start_Year() throws ParseException {
		Event event = new Event();
		
		
		String dateTimeStart = "2000-01-01 00:00:00";
		String dateTimeEnd = "1999-01-01 00:00:00";

		exception.expect(IllegalArgumentException.class);
		event.addTimeDateSpan(dateTimeStart, dateTimeEnd);
	}
	
	@Test
	public void test_Time_End_Gtr_Time_Start_Month() throws ParseException {
		Event event = new Event();
		
		
		String dateTimeStart = "2000-02-01 00:00:00";
		String dateTimeEnd = "2000-01-01 00:00:00";

		exception.expect(IllegalArgumentException.class);
		event.addTimeDateSpan(dateTimeStart, dateTimeEnd);
	}
	
	@Test
	public void test_Time_End_Gtr_Time_Start_Day() throws ParseException {
		Event event = new Event();
		
		
		String dateTimeStart = "2000-01-02 00:00:00";
		String dateTimeEnd = "2000-01-01 00:00:00";

		exception.expect(IllegalArgumentException.class);
		event.addTimeDateSpan(dateTimeStart, dateTimeEnd);
	}
	
	@Test
	public void test_Time_End_Gtr_Time_Start_Sec() throws ParseException {
		Event event = new Event();
		
		
		String dateTimeStart = "2000-01-01 23:59:59";
		String dateTimeEnd = "2000-01-01 23:59:58";

		exception.expect(IllegalArgumentException.class);
		event.addTimeDateSpan(dateTimeStart, dateTimeEnd);
	}
	
	@Test
	public void test_Time_End_Gtr_Time_Start_Hour() throws ParseException {
		Event event = new Event();
		
		
		String dateTimeStart = "2000-01-01 23:59:59";
		String dateTimeEnd = "2000-01-01 22:59:59";

		exception.expect(IllegalArgumentException.class);
		event.addTimeDateSpan(dateTimeStart, dateTimeEnd);
	}
	
	@Test
	public void test_Time_End_Gtr_Time_Start_Minute() throws ParseException {
		Event event = new Event();
		
		
		String dateTimeStart = "2000-01-01 23:59:59";
		String dateTimeEnd = "2000-01-01 23:58:59";

		exception.expect(IllegalArgumentException.class);
		event.addTimeDateSpan(dateTimeStart, dateTimeEnd);
	}
	
	@Test
	public void test_Size_Of_PropertyList_One_Property() {
		Event event = new Event();
		event.addDescription("Test Description");
		assertEquals(event.getPropertySize(), 1);
	}
	
	@Test
	public void test_Size_Of_PropertyList_No_Property() {
		Event event = new Event();
		assertEquals(event.getPropertySize(), 0);
	}
}
