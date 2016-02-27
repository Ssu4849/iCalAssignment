
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
		
		System.out.println("Enter the time the event starts (Eg, 20160225T220000Z):");
		String timeStart = sc.nextLine();
		
		System.out.println("Enter the time the event ends (Eg, 20160225T221500Z)");
		String timeEnd = sc.nextLine();
		
		System.out.println("Enter a summary of the event: ");
		String summary = sc.nextLine();
		
		System.out.println("Enter the location ");
		String location = sc.nextLine();
	
		System.out.println("Enter a description of the event, or leave it blank");
		String description = sc.nextLine();
		
		testCal.addEvent(timeStart, timeEnd, summary, description, location);

		try {
			FileWriter fw = new FileWriter(testCal.getFile() + ".ics");

			fw.write(testCal.toString());

			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
