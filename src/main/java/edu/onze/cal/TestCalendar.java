
package edu.onze.cal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import edu.onze.cal.props.Geo;

/**
 * @authors Daralyn Young, Corey Watanabe, Shengyuan Su
 */
public class TestCalendar {

	static Scanner sc;

	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		sc = new Scanner(System.in);

		if (args.length == 0) {
			System.err.println("Usage: Enter 1 to write, 2 to read a single file, 4 to read all files and calculate great-circle distance");
			System.exit(0);
		}

		if (Integer.parseInt(args[0]) == 1) {
			createICSFile();
		} else if (Integer.parseInt(args[0]) == 2) {
			System.out.println("Enter the name of the ICS file you want to read (without extension)");
			File file = new File(sc.nextLine() + ".ics");
			try {
				readEvents(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (Integer.parseInt(args[0]) == 3) {
			readCalculateCircleDistance();
		} else {
			System.err.println(
					"Usage: Enter 1 to write, 2 to read a single file, 3 to read all files and calculate great circle distance");
			System.exit(0);
		}
	}

	/**
	 * Prints the calendar object to a .ics file
	 * 
	 * @param calObj
	 *            the calendar object
	 * @throws IOException
	 *             if an input/output error occurs
	 */
	public static void printICSFile(iCalObj calObj) throws IOException {
		FileWriter fw = new FileWriter(calObj.getFile());
		fw.write(calObj.toString());
		fw.close();
	}

	/**
	 * Read all events into an icalendar object
	 * 
	 * @param file
	 *            the file with icalendar
	 * @iCalObj the calendar object generated from the file
	 * @throws IOException
	 */
	public static iCalObj readEvents(File file) throws IOException {
		iCalObj calObj = new iCalObj(file);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		while ((line = br.readLine()) != null) {
			if (line.equals(Event.EVENT_HEADER)) {
				Event event = new Event();
				while ((line = br.readLine()) != null && !line.equals(Event.EVENT_TRAILER)) {
					try {
						event.addPropNoFormatRequired(line);
					} catch (IllegalArgumentException e) {
						System.err.println(e.getMessage());
						System.err.println("skipping line...");
					}
				}
				calObj.addEvent(event);
			}
		}
		fr.close();
		return calObj;
	}

	/**
	 * Calculates the great circle distance using mean radius value from
	 * <a href="http://nssdc.gsfc.nasa.gov/planetary/factsheet/earthfact.html">
	 * http://nssdc.gsfc.nasa .gov/planetary/factsheet/earthfact.html</a>
	 * 
	 * @param latitude1
	 * @param latitude2
	 * @param longitude1
	 * @param longitude2
	 * @return
	 */
	public static double getGreatCircleDistance(double latitude1, double latitude2, double longitude1,
			double longitude2) {
		final double EARTH_MEAN_RADIUS = 6371.0;
		double diffLongitude = Math.abs(latitude2 - latitude1);
		double deltaCentralAngle = Math.acos(Math.sin(Math.toRadians(latitude1)) * Math.sin(Math.toRadians(latitude2))
				+ Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
						* Math.cos(Math.toRadians(diffLongitude)));
		return EARTH_MEAN_RADIUS * deltaCentralAngle;
	}

	public static void createICSFile() {
		System.out.println("Enter the file name for your ics file");
		String fileName = sc.nextLine();
		File file = new File(fileName + ".ics");

		iCalObj calendar = new iCalObj(file);

		System.out.println("Enter the time the event start (Format: YYYY-MM-DD HH:MM:SS)");
		String dateTimeStart = sc.nextLine();
		// Checks timeStart for correct format
		if (dateTimeStart.split(":").length != 3 || dateTimeStart.split("-").length != 3) {
			System.err.println("Date/Time start is not the correct format");
			System.exit(0);
		}

		// Parses user input for anything != int
		try {
			String date = dateTimeStart.split(" ")[0];
			for (String s : date.split("-")) {
				Integer.parseInt(s);
			}
		} catch (NumberFormatException e) {
			System.err.println("Month, date, or year is not a valid integer");
			System.exit(0);
		}
		try {
			String time = dateTimeStart.split(" ")[1];
			for (String s : time.split(":")) {
				Integer.parseInt(s);
			}
		} catch (NumberFormatException e) {
			System.err.println("Hour, second, or time is not a valid integer");
			System.exit(0);
		}

		System.out.println("Enter the time the event end (Format: YYYY-MM-DD HH:MM:SS)");
		String dateTimeEnd = sc.nextLine();
		// Checks timeEnd for correct format
		if (dateTimeEnd.split(":").length != 3 || dateTimeEnd.split("-").length != 3) {
			System.err.println("Date/Time end is not the correct format");
			System.exit(0);
		}

		// Parses User input for anything != Int
		try {
			String date = dateTimeEnd.split(" ")[0];
			for (String s : date.split("-")) {
				Integer.parseInt(s);
			}
		} catch (NumberFormatException e) {
			System.err.println("Month, date, or year is not a valid integer");
			System.exit(0);
		}
		try {
			String time = dateTimeEnd.split(" ")[1];
			for (String s : time.split(":")) {
				Integer.parseInt(s);
			}
		} catch (NumberFormatException e) {
			System.err.println("Hour, second, or time is not a valid integer");
			System.exit(0);
		}

		System.out.println("Enter a summary of the event: ");
		String summary = sc.nextLine();

		System.out.println("Enter the location ");
		String location = sc.nextLine();

		System.out.println("Enter a description of the event, or leave it blank");
		String description = sc.nextLine();

		System.out
				.println("Enter the Geographic Position of the event: Latitude [space] longitude, or leave it blank \n"
						+ "	(format: <degrees>,<minutes>,<seconds> <degrees>,<minutes>,<seconds>)");
		String geoPosition = sc.nextLine();

		System.out.println("Enter 1 for the event to be PUBLIC, 2 for PRIVATE, and 3 for CONFIDENTIAL");
		String classification = sc.nextLine();
		int classificationChoice = 1;
		// Validates if user input is a number
		if (classification.trim().length() != 0) {
			try {
				classificationChoice = Integer.parseInt(classification);
			} catch (NumberFormatException e) {
				System.err.println("Not a number");
				System.exit(0);
			}
		}

		// Validates if user input is within range [1,3]
		// if not, defaults to PUBLIC
		if (classificationChoice < 1 || classificationChoice > 3) {
			System.err.println("Not within range. Defaulting classification to PUBLIC");
			classificationChoice = 1;
		}

		// validates geographic position format
		if (geoPosition.trim().length() != 0) {
			String[] latLongArray = geoPosition.split(" ");
			if (latLongArray.length != 2) {
				System.err.println("Geographic position is not the correct format");
				System.exit(0);
			}
			if (latLongArray[0].split(",").length != 3) {
				System.err.println("Latitude is not the correct format");
				System.exit(0);
			}
			if (latLongArray[1].split(",").length != 3) {
				System.err.println("Longitude is not the correct format");
				System.exit(0);
			}

			// Parses User input for anything != a decimal
			try {
				for (String latLong : geoPosition.split(" ")) {
					for (String coordinate : latLong.split(",")) {
						Double.parseDouble(coordinate);
					}
				}
			} catch (NumberFormatException e) {
				System.err.println("Geographic position not a decimal");
				System.exit(0);
			}
		}
		// Checks that event date/time is the correct format
		try {
			Event event1 = calendar.createEvent(dateTimeStart, dateTimeEnd, summary, description, location);
			if (geoPosition.trim().length() != 0) {
				event1.addGeoPosition(geoPosition);
			}
			if (classification.trim().length() != 0) {
				event1.setClassification(classificationChoice);
			}
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			System.exit(0);
		} catch (ParseException e) {
			System.err.println("Date is incorrect format. Usage: 'YYYY-MM-DD HH:MM:SS'");
			System.exit(0);
		} catch (IllegalStateException e) {
			System.err.println(e.getMessage());
			System.exit(0);
		}

		try {
			printICSFile(calendar);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readCalculateCircleDistance() {
		System.out.println("Reading all .ics files in directory...");

		System.out.println("Enter a date you want to read (Format YYYY-MM-DD)");
		String dateString = sc.nextLine();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateSelected = null;
		try {
			dateSelected = dateFormat.parse(dateString);
		} catch (ParseException e) {

		}

		List<Component> eventList = new ArrayList<Component>();
		List<iCalObj> calendarList = new ArrayList<iCalObj>();

		File[] fileList = new File("./").listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File directory, String filename) {
				return filename.endsWith(".ics");
			}
		});

		try {
			for (File file : fileList) {
				iCalObj calendar;
				calendar = readEvents(file);
				eventList.addAll(calendar.getComponentList());
				calendarList.add(calendar);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Will sort the events by start date
		eventList.sort(new ComponentComparable());

		// removes all events in the eventList not same day as the chosen
		// date
		Iterator<Component> it = eventList.iterator();
		while (it.hasNext()) {
			Component c = it.next();
			if (c.getStartDate().getYear() != dateSelected.getYear()
					|| c.getStartDate().getMonth() != dateSelected.getMonth()
					|| c.getStartDate().getDate() != dateSelected.getDate()) {
				it.remove();
			}
		}

		for (int i = 0; i < (eventList.size() - 1); i++) {
			Geo geo1 = eventList.get(i).getGeographicPosition();
			Double latGeo1 = Double.parseDouble(geo1.getContent().split(";")[0]);
			Double longGeo1 = Double.parseDouble(geo1.getContent().split(";")[1]);
			Geo geo2 = eventList.get(i + 1).getGeographicPosition();
			Double latGeo2 = Double.parseDouble(geo2.getContent().split(";")[0]);
			Double longGeo2 = Double.parseDouble(geo2.getContent().split(";")[1]);

			Double distanceKm = new BigDecimal(getGreatCircleDistance(latGeo1, latGeo2, longGeo1, longGeo2))
					.setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();
			Double distanceMi = new BigDecimal(distanceKm * 0.62137).setScale(3, BigDecimal.ROUND_FLOOR).doubleValue();

			eventList.get(i)
					.addComment("The distance to the next event is " + distanceKm + " km or " + distanceMi + " miles.");
		}

		try {
			for (iCalObj calendar : calendarList) {
				printICSFile(calendar);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
