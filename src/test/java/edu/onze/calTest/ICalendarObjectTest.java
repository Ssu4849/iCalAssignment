package edu.onze.calTest;

import static org.junit.Assert.*;

import java.io.File;
import java.text.ParseException;

import org.junit.Test;

import edu.onze.cal.Event;
import edu.onze.cal.iCalObj;

public class ICalendarObjectTest {

	@Test
	public void test_Calendar_Object_No_Components() {
		iCalObj ical = new iCalObj(null);
		assertEquals(ical.getComponentList().size(), 0);
	}
	
	@Test
	public void test_Calendar_Object_One_Component() {
		iCalObj ical = new iCalObj(null);
		ical.addEvent(new Event());
		assertEquals(ical.getComponentList().size(), 1);
	}

	@Test
	public void test_Calendar_Object_Add_Event() throws ParseException, IllegalArgumentException {
		iCalObj ical = new iCalObj(null);
		ical.createEvent("1000-10-10 10:10:10", "2000-10-10 10:10:10", "Test_Summary", "Test_Description", "Test_Location");
		assertEquals(ical.getComponentList().size(), 1);
	}
	
	@Test
	public void test_File() throws ParseException, IllegalArgumentException {
		File f = new File("Test");
		iCalObj ical = new iCalObj(f);

		assertEquals(ical.getFile(), f);
	}
}
