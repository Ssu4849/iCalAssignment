
package edu.onze.cal;

import java.io.File;
import java.io.FileWriter;
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
		}
		try {
			String time = dateTimeStart.split(" ")[1];
			for (String s : time.split(":")) {
				Integer.parseInt(s);
			}
		} catch (NumberFormatException e) {
			System.err.println("Hour, second, or time is not a valid integer");
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
		}
		try {
			String time = dateTimeEnd.split(" ")[1];
			for (String s : time.split(":")) {
				Integer.parseInt(s);
			}
		} catch (NumberFormatException e) {
			System.err.println("Hour, second, or time is not a valid integer");
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
		
		// validates geographic position format
		String[] latLongArray = geoPosition.split(" ");
		if (latLongArray.length != 2) {
			System.err.println("Geographic position is not the correct format");
		}
		if (latLongArray[0].split(",").length != 3) {
			System.err.println("Latitude is not the correct format");
		}
		if (latLongArray[1].split(",").length != 3) {
			System.err.println("Longitude is not the correct format");
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
		}

		testCal.addEvent(dateTimeStart, dateTimeEnd, summary, description, location, geoPosition);

		try {
			FileWriter fw = new FileWriter(testCal.getFile() + ".ics");

			fw.write(testCal.toString());

			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
