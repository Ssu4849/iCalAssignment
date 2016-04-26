package edu.onze.cal;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;

public class Application {

	private JFrame frmTeamOnze;
	private JTextField textField;
	private JLabel lblLocation;
	private JTextField locationField;
	private JLabel lblLatititude;
	private JTextField secLatField;
	private JComboBox tStartHrBox;
	private JComboBox tEndHrBox;
	private JComboBox dStartMon;
	private JComboBox dEndMon;
	private JComboBox dStartDay;
	private JComboBox dEndDay;
	private JComboBox dStartYr;
	private JComboBox dEndYr;
	private JComboBox degLatBox;
	private JComboBox minLatBox;
	private JComboBox directionLatBox;
	private JComboBox degLngBox;
	private JComboBox minLngBox;
	private JTextField secLngField;
	private JComboBox directionLngBox;
	private JButton btnExport;
	private JLabel degLabel;
	private JLabel lblMin;
	private JLabel secLabel;
	private JLabel dirLabel;
	private JButton btnCalcDist;

	private static final String HOURS[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
	private static final String MINUTES[] = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
			"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
			"31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48",
			"49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" };
	private static final String MERIDIEM[] = { "AM", "PM" };

	private static final String MONTHS[] = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "SEPT", "OCT", "NOV",
			"DEC" };
	private static final String DAYS[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13",
			"14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" };
	private static final String YEARS[] = { "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979",
			"1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992",
			"1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005",
			"2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018",
			"2019", "2020" };

	private static String Degrees[] = new String[181];
	static {
		for (int i = 0; i < Degrees.length; i++) {
			if (i < 10) {
				Degrees[i] = "0" + String.valueOf(i);
			} else {
				Degrees[i] = String.valueOf(i);
			}
		}
	}
	private static final String LONGITUDE_DIRECTIONS[] = { "W", "E" };
	private static final String LATITUDE_DIRECTIONS[] = { "N", "S" };

	private JComboBox tStartMinBox;
	private JComboBox tEndMinBox;
	private JComboBox tStartMeridiemBox;
	private JComboBox tEndMeridiemBox;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (Exception e) {
						// Do something with windows theme is not available
					}
					window.frmTeamOnze.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Application() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		final JFileChooser fileChooser = new JFileChooser("./");

		frmTeamOnze = new JFrame();
		frmTeamOnze.setResizable(false);
		frmTeamOnze.setTitle("Team Onze Event Generator");
		frmTeamOnze.setBounds(100, 100, 540, 446);
		frmTeamOnze.setLocationRelativeTo(null);
		frmTeamOnze.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ButtonGroup bg = new ButtonGroup();

		textField = new JTextField();
		textField.setBounds(101, 11, 405, 20);
		textField.setColumns(10);

		JLabel lblSummary = new JLabel("Summary");
		lblSummary.setBounds(31, 14, 76, 14);

		JLabel lblStartTime = new JLabel("Start Time");
		lblStartTime.setBounds(32, 45, 75, 14);

		JLabel lblStartDate = new JLabel("Start Date");
		lblStartDate.setBounds(251, 45, 71, 14);

		JLabel lblEndTime = new JLabel("End Time");
		lblEndTime.setBounds(32, 76, 75, 14);

		JLabel lblEndDate = new JLabel("End Date");
		lblEndDate.setBounds(251, 76, 71, 14);

		lblLocation = new JLabel("Location");
		lblLocation.setBounds(32, 117, 75, 14);

		locationField = new JTextField();
		locationField.setBounds(101, 114, 405, 20);
		locationField.setColumns(10);

		lblLatititude = new JLabel("Latititude");
		lblLatititude.setBounds(32, 165, 75, 14);

		secLatField = new JTextField();
		secLatField.setBounds(212, 162, 55, 20);
		secLatField.setColumns(10);

		JLabel lblLongitutde = new JLabel("Longitutde");
		lblLongitutde.setBounds(32, 200, 75, 14);

		JRadioButton rdbtnPublic = new JRadioButton("Public");
		rdbtnPublic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JRadioButton classBtn = (JRadioButton) e.getSource();

				if (classBtn.getText().equals("Public")) {
					bg.clearSelection();
					rdbtnPublic.setSelected(true);
				}
			}
		});
		
		rdbtnPublic.setBounds(394, 161, 83, 23);

		JRadioButton rdbtnPrivate = new JRadioButton("Private");
		rdbtnPrivate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JRadioButton classBtn = (JRadioButton) e.getSource();

				if (classBtn.getText().equals("Private")) {
					bg.clearSelection();
					rdbtnPrivate.setSelected(true);
				}
			}
		});
		rdbtnPrivate.setBounds(394, 187, 83, 23);

		JRadioButton rdbtnConfidential = new JRadioButton("Confidential");
		rdbtnConfidential.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JRadioButton classBtn = (JRadioButton) e.getSource();

				if (classBtn.getText().equals("Confidential")) {
					bg.clearSelection();
					rdbtnConfidential.setSelected(true);
				}
			}
		});
		rdbtnConfidential.setBounds(394, 213, 112, 23);
		rdbtnConfidential.setSelected(true);
		
		bg.add(rdbtnPublic);
		bg.add(rdbtnPrivate);
		bg.add(rdbtnConfidential);

		tStartHrBox = new JComboBox(HOURS);
		tStartHrBox.setBounds(101, 42, 45, 20);

		tEndHrBox = new JComboBox(HOURS);
		tEndHrBox.setBounds(101, 73, 45, 20);

		dStartMon = new JComboBox(MONTHS);
		dStartMon.setBounds(319, 42, 55, 20);

		dEndMon = new JComboBox(MONTHS);
		dEndMon.setBounds(319, 73, 55, 20);

		dStartDay = new JComboBox(DAYS);
		dStartDay.setBounds(384, 42, 55, 20);

		dEndDay = new JComboBox(DAYS);
		dEndDay.setBounds(384, 73, 55, 20);

		dStartYr = new JComboBox(YEARS);
		dStartYr.setBounds(449, 42, 57, 20);

		dEndYr = new JComboBox(YEARS);
		dEndYr.setBounds(449, 73, 57, 20);

		JLabel descLabel = new JLabel("Description");
		descLabel.setBounds(32, 228, 97, 14);

		JLabel classLabel = new JLabel("Select visibility");
		classLabel.setBounds(384, 142, 109, 14);

		degLatBox = new JComboBox(Degrees);
		degLatBox.setBounds(101, 162, 51, 20);
		degLatBox.setSelectedIndex(0);

		minLatBox = new JComboBox(MINUTES);
		minLatBox.setBounds(157, 162, 51, 20);

		directionLatBox = new JComboBox(LATITUDE_DIRECTIONS);
		directionLatBox.setBounds(271, 162, 51, 20);

		degLngBox = new JComboBox(Degrees);
		degLngBox.setBounds(101, 197, 51, 20);
		degLngBox.setSelectedIndex(0);

		minLngBox = new JComboBox(MINUTES);
		minLngBox.setBounds(157, 197, 51, 20);

		secLngField = new JTextField();
		secLngField.setBounds(212, 197, 55, 20);
		secLngField.setColumns(10);

		directionLngBox = new JComboBox(LONGITUDE_DIRECTIONS);
		directionLngBox.setBounds(271, 197, 51, 20);

		btnExport = new JButton("Export to .ICS");
		btnExport.setBounds(384, 359, 122, 23);
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton classBtn = (JButton) e.getSource();

				if (classBtn.getText().equals("Export to .ICS")) {
					fileChooser.showSaveDialog(classBtn);
				}
			}
		});
		frmTeamOnze.getContentPane().setLayout(null);
		frmTeamOnze.getContentPane().add(lblLatititude);
		frmTeamOnze.getContentPane().add(lblLongitutde);
		frmTeamOnze.getContentPane().add(degLatBox);
		frmTeamOnze.getContentPane().add(minLatBox);
		frmTeamOnze.getContentPane().add(secLatField);
		frmTeamOnze.getContentPane().add(directionLatBox);
		frmTeamOnze.getContentPane().add(degLngBox);
		frmTeamOnze.getContentPane().add(minLngBox);
		frmTeamOnze.getContentPane().add(secLngField);
		frmTeamOnze.getContentPane().add(directionLngBox);
		frmTeamOnze.getContentPane().add(descLabel);
		frmTeamOnze.getContentPane().add(rdbtnPrivate);
		frmTeamOnze.getContentPane().add(rdbtnPublic);
		frmTeamOnze.getContentPane().add(rdbtnConfidential);
		frmTeamOnze.getContentPane().add(btnExport);
		frmTeamOnze.getContentPane().add(classLabel);
		frmTeamOnze.getContentPane().add(lblEndTime);
		frmTeamOnze.getContentPane().add(lblStartTime);
		frmTeamOnze.getContentPane().add(lblSummary);
		frmTeamOnze.getContentPane().add(lblLocation);
		frmTeamOnze.getContentPane().add(tStartHrBox);
		frmTeamOnze.getContentPane().add(tEndHrBox);
		frmTeamOnze.getContentPane().add(lblEndDate);
		frmTeamOnze.getContentPane().add(lblStartDate);
		frmTeamOnze.getContentPane().add(dEndMon);
		frmTeamOnze.getContentPane().add(dStartMon);
		frmTeamOnze.getContentPane().add(dEndDay);
		frmTeamOnze.getContentPane().add(dStartDay);
		frmTeamOnze.getContentPane().add(dStartYr);
		frmTeamOnze.getContentPane().add(dEndYr);
		frmTeamOnze.getContentPane().add(locationField);
		frmTeamOnze.getContentPane().add(textField);

		degLabel = new JLabel("Deg");
		degLabel.setBounds(101, 149, 45, 14);
		degLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frmTeamOnze.getContentPane().add(degLabel);

		lblMin = new JLabel("Min");
		lblMin.setBounds(162, 149, 45, 14);
		lblMin.setHorizontalAlignment(SwingConstants.CENTER);
		frmTeamOnze.getContentPane().add(lblMin);

		secLabel = new JLabel("Sec");
		secLabel.setBounds(218, 149, 45, 14);
		secLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frmTeamOnze.getContentPane().add(secLabel);

		dirLabel = new JLabel("Dir");
		dirLabel.setBounds(277, 149, 45, 14);
		dirLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frmTeamOnze.getContentPane().add(dirLabel);

		btnCalcDist = new JButton("Calc Distance");
		btnCalcDist.setBounds(384, 325, 122, 23);
		frmTeamOnze.getContentPane().add(btnCalcDist);

		tStartMinBox = new JComboBox(MINUTES);
		tStartMinBox.setBounds(150, 42, 44, 20);
		frmTeamOnze.getContentPane().add(tStartMinBox);

		tEndMinBox = new JComboBox(MINUTES);
		tEndMinBox.setBounds(150, 73, 44, 20);
		frmTeamOnze.getContentPane().add(tEndMinBox);

		tStartMeridiemBox = new JComboBox(MERIDIEM);
		tStartMeridiemBox.setBounds(198, 42, 43, 20);
		tStartMeridiemBox.setSelectedIndex(0);
		frmTeamOnze.getContentPane().add(tStartMeridiemBox);

		tEndMeridiemBox = new JComboBox(MERIDIEM);
		tEndMeridiemBox.setBounds(198, 73, 43, 20);
		tEndMeridiemBox.setSelectedIndex(0);
		frmTeamOnze.getContentPane().add(tEndMeridiemBox);
		
		textArea = new JTextArea();
		textArea.setBounds(32, 251, 343, 131);
		textArea.setBorder(LineBorder.createGrayLineBorder());
		frmTeamOnze.getContentPane().add(textArea);
		
	}
}
