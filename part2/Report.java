package part2;

import java.util.*;

public class Report implements Runnable {
	private static final String spacer = " ------------------------------- ", nl = "\n";
	private final int[][] readings;

	private static int index = 0;

	private int HIGH;
	private int LOW;
	private int READINGS_PER_SENSOR;
	private int CHANGE_INT;
	private int NUM_REPORTS;
	private int REPORT_INT;
	private int NUM_SENSORS;

	public Report(int[][] readings, int high, int low, int readingsPerSensor, int changeInt, int numReports,
			int reportInt, int numSensors) {
		this.readings = readings;

		this.HIGH = high;
		this.LOW = low;
		this.READINGS_PER_SENSOR = readingsPerSensor;
		this.CHANGE_INT = changeInt;
		this.NUM_REPORTS = numReports;
		this.REPORT_INT = reportInt;
		this.NUM_SENSORS = numSensors;
	}

	private void report(int[] minTemps, int[] maxTemps) {
		int maxDelta = Integer.MIN_VALUE, maxDeltaIndex = -1;

		for (int i = 0; i < this.READINGS_PER_SENSOR - this.CHANGE_INT; i++) {
			int max = maxTemps[i], min = minTemps[i];
			for (int j = 1; j < this.CHANGE_INT; j++) {
				max = Math.max(max, maxTemps[i + j]);
				min = Math.min(min, minTemps[i + j]);
			}

			int currDelta = max - min;
			if (currDelta > maxDelta) {
				maxDelta = currDelta;
				maxDeltaIndex = i;
			}
		}

		Arrays.sort(maxTemps);
		Arrays.sort(minTemps);

		System.out.println(nl + spacer + "Report #" + index + spacer + nl);

		System.out.print("The five highest reported temperatures were:");
		for (int i = this.READINGS_PER_SENSOR - 1; i > this.READINGS_PER_SENSOR - 6; i--)
			System.out.print(" " + maxTemps[i]);

		System.out.print(nl + "The five lowest reported temperatures were:");
		for (int i = 0; i < 5; i++)
			System.out.print(" " + minTemps[i]);

		System.out.println(nl + "The largest difference in temperatures was " + maxDelta + " between " + maxDeltaIndex
				+ " and " + (maxDeltaIndex + this.CHANGE_INT));
	}

	public void run() {
		while (index++ < this.NUM_REPORTS) {
			try {
				Thread.sleep(this.REPORT_INT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			int[] minTemps = new int[this.READINGS_PER_SENSOR];
			int[] maxTemps = new int[this.READINGS_PER_SENSOR];

			Arrays.fill(minTemps, this.HIGH + 1);
			Arrays.fill(maxTemps, this.LOW - 1);

			for (int i = 0; i < this.READINGS_PER_SENSOR; i++) {
				for (int j = 0; j < this.NUM_SENSORS; j++) {
					minTemps[i] = Math.min(minTemps[i], readings[j][i]);
					maxTemps[i] = Math.max(maxTemps[i], readings[j][i]);
				}
			}

			report(minTemps, maxTemps);
		}
	}
}
