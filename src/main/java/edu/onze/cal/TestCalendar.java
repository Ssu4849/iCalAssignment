
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
		String timeStart = sc.nextLine();

		System.out.println("Enter the time the event end (Format: YYYY-MM-DD HH:MM:SS)");
		String timeEnd = sc.nextLine();

		System.out.println("Enter a summary of the event: ");
		String summary = sc.nextLine();

		System.out.println("Enter the location ");
		String location = sc.nextLine();

		System.out.println("Enter a description of the event, or leave it blank");
		String description = sc.nextLine();

		System.out.println("Enter the Geographic Position of the event: Latitude [space] longitude, or leave it blank \n"
						+ "	(format: <degrees>,<minutes>,<seconds> <degrees>,<minutes>,<seconds>)");
		String geoPosition = sc.nextLine();

		testCal.addEvent(timeStart, timeEnd, summary, description, location, geoPosition);

		try {
			FileWriter fw = new FileWriter(testCal.getFile() + ".ics");

			fw.write(testCal.toString());

			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
