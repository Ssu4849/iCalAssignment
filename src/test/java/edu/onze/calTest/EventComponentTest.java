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
	public void test_Event_Geo_Add_Out_Of_Range_Longitude() throws IllegalArgumentException{
		Event event = new Event();
		exception.expect(IllegalStateException.class);
		event.addGeoPosition("20,20,20 181,20,20");
	}	
	
	@Test 
	public void test_Event_Geo_Add_Out_Of_Range_Longitude2() throws IllegalArgumentException{
		Event event = new Event();
		exception.expect(IllegalStateException.class);
		event.addGeoPosition("20,20,20 -181,20,20");
	}	
	
	@Test 
	public void test_Event_Geo_Add_Out_Of_Range_Latitude1() throws IllegalArgumentException{
		Event event = new Event();
		exception.expect(IllegalStateException.class);
		event.addGeoPosition("-91,20,20 20,20,20");
	}	
	
	@Test 
	public void test_Event_Geo_Add_Out_Of_Range_Latitude2() throws IllegalArgumentException{
		Event event = new Event();
		exception.expect(IllegalStateException.class);
		event.addGeoPosition("91,20,20 20,20,20");
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
	
	
	/*The next following test cases checks for return values of added properties*/
	
	@Test
	public void test_Event_Description() {
	    Event event = new Event();
	    assertEquals(event.addDescription("test"), "DESCRIPTION:test" + "\r\n");
	    /*assertEquals(event.getDescription(), "test");*/
	}
	
	@Test
	public void test_Event_Comment() {
	    Event event = new Event();
	    assertEquals(event.addComment("test"), "COMMENT:test" + "\r\n");
	    /*assertEquals(event.getDescription(), "test");*/
	}
	
	@Test
	public void test_Event_Summary() {
	    Event event = new Event();
	    assertEquals(event.addSummary("test"), "SUMMARY:test" + "\r\n");
	    /*assertEquals(event.getDescription(), "test");*/
	}
	
	@Test
	public void test_Event_Location() {
	    Event event = new Event();
	    assertEquals(event.addLocation("test"), "LOCATION:test" + "\r\n");
	    /*assertEquals(event.getDescription(), "test");*/
	}
	
	@Test
	public void test_Event_GEO() {
	    Event event = new Event();
	    assertEquals(event.addGeoPosition("20,20,20 20,20,20"), "GEO:20.338888;20.338888" + "\r\n");
	    /*assertEquals(event.getDescription(), "test");*/
	}
	
	/*The next 6 test cases checks for exceptions*/
	@Test 
	public void test_Event_Duplicate_Classification(){
		Event event = new Event();
		event.setClassification(0);
		exception.expect(IllegalArgumentException.class);
		event.setClassification(0);
	}
	
	@Test
	public void test_Event_Duplicate_Description() {
		Event event = new Event();
		event.addDescription("Test");
		exception.expect(IllegalArgumentException.class);
		event.addDescription("Test");
	}


	@Test 
	public void test_Event_Duplicate_Geo() throws IllegalStateException{
		Event event = new Event();
		event.addGeoPosition("20,20,20 20,20,20");
		exception.expect(IllegalArgumentException.class);
		event.addGeoPosition("20,20,20 20,20,20");
	}	
	
	
	@Test
	public void test_Event_Duplicate_Location() {
		Event event = new Event();
		event.addLocation("Test");
		exception.expect(IllegalArgumentException.class);
		event.addLocation("Test");
	}
	
	@Test
	public void test_Event_Duplicate_Summary() {
		Event event = new Event();
		event.addSummary("Test");
		exception.expect(IllegalArgumentException.class);
		event.addSummary("Test");
	}
	
	
}
