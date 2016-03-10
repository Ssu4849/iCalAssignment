
package edu.onze.cal;

import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;
import java.util.Scanner;

/**
 * @author Team Onze
 *
 */
public class TestCalendar {

	static Scanner sc;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		sc = new Scanner(System.in);

		System.out.println("Enter the file name for your ics file");
		String fileName = sc.nextLine();
		File file = new File(fileName);

		iCalObj testCal = new iCalObj(file);

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
		try {
			classificationChoice = Integer.parseInt(classification);
		} catch (NumberFormatException e) {
			System.err.println("Not a number");
			System.exit(0);
		}

		// Validates if user input is within range [1,3]
		// if not, defaults to PUBLIC
		if (classificationChoice < 1 || classificationChoice > 3) {
			System.err.println("Not within range. Defaulting classification to PUBLIC");
			classificationChoice = 1;
		}

		// validates geographic position format
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

		try {
			Event event1 = testCal.createEvent(dateTimeStart, dateTimeEnd, summary, description, location);
			event1.addGeoPosition(geoPosition);
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
			FileWriter fw = new FileWriter(testCal.getFile() + ".ics");

			fw.write(testCal.toString());

			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
