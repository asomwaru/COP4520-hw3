import java.io.*;

import part2.Sensor;
import part2.Report;

public class Rover {
	static final int NUM_SENSORS = 8;
	static final int LOW = -100, HIGH = 70, RANGE = HIGH - LOW;
	static final int READING_INT = 50, REPORT_INT = READING_INT * 60, CHANGE_INT = 10;
	static final int READINGS_PER_SENSOR = REPORT_INT / READING_INT, NUM_REPORTS = 5,
			TOTAL_TIME = REPORT_INT * NUM_REPORTS;

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		int[][] readings = new int[NUM_SENSORS][READINGS_PER_SENSOR];

		Thread[] sensors = new Thread[NUM_SENSORS];
		for (int i = 0; i < NUM_SENSORS; i++) {
			sensors[i] = new Thread(new Sensor(readings[i], LOW, RANGE, READING_INT, TOTAL_TIME, READINGS_PER_SENSOR));
			sensors[i].start();
		}

		Thread report = new Thread(
				new Report(readings, HIGH, LOW, READINGS_PER_SENSOR, CHANGE_INT, NUM_REPORTS, REPORT_INT, NUM_SENSORS));
		report.start();
	}
}
