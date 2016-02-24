
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

		try {
			FileWriter fw = new FileWriter(testCal.getFile() + ".ics");

			fw.write(testCal.toString());

			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
