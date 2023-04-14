package part2;

import java.util.*;

public class Sensor implements Runnable {
	private static Random rand;
	private final int[] readings;
	private int totalReadings = 0;

	private int LOW;
	private int RANGE;
	private int READING_INT;
	private int TOTAL_TIME;
	private int READINGS_PER_SENSOR;

	public Sensor(int[] readings, int low, int range, int readingInt, int totalTime, int readingsPerSensor) {
		rand = new Random();
		this.readings = readings;

		this.LOW = low;
		this.RANGE = range;
		this.READING_INT = readingInt;
		this.TOTAL_TIME = totalTime;
		this.READINGS_PER_SENSOR = readingsPerSensor;
	}

	public void run() {

		while (totalReadings * READING_INT < this.TOTAL_TIME) {
			int index = totalReadings++ % this.READINGS_PER_SENSOR;
			readings[index] = rand.nextInt(this.RANGE + 1) - Math.abs(this.LOW);

			try {
				Thread.sleep(this.READING_INT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
