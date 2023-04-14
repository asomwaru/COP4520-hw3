
// import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

import part1.LockFreeLinkedList;
import part1.Servant;

public class Birthday {
	private final static int NUM_SERVANTS = 4;
	private final static int NUM_GIFTS = 50_000;

	public static void main(String[] args) throws InterruptedException {
		LockFreeLinkedList<Integer> list = new LockFreeLinkedList<>();
		ArrayList<Integer> chain = new ArrayList<>();
		ArrayList<Thread> threads = new ArrayList<>();

		for (int i = 0; i < NUM_GIFTS; i++) {
			chain.add(i);
		}

		Collections.shuffle(chain);

		final long start = System.currentTimeMillis();
		for (int i = 0; i < NUM_SERVANTS; i++) {
			Thread t = new Thread(new Servant(i, list, chain));
			threads.add(t);
			t.start();
		}

		for (Thread t : threads) {
			t.join();
		}
		final long end = System.currentTimeMillis();

		System.out.println("Time: " + (end - start) + "ms");
	}
}
