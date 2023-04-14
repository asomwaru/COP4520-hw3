package part1;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Servant implements Runnable {
	LockFreeLinkedList<Integer> list = new LockFreeLinkedList<>();
	ArrayList<Integer> chain = new ArrayList<>();
	int id;

	public Servant(int id, LockFreeLinkedList<Integer> list, ArrayList<Integer> chain) {
		this.id = id;
		this.list = list;
		this.chain = chain;
	}

	@Override
	public void run() {
		System.out.println("Servant " + id + " started");
		Random rand = new Random();
		int initialSize = chain.size();

		while (!chain.isEmpty()) {
			try {
				int choice = ThreadLocalRandom.current().nextInt(0, 2);

				if (choice == 0) {
					int index = rand.nextInt(chain.size());
					int number = chain.get(index);

					this.list.insert(number);
					chain.remove(index);
				} else if (choice == 1) {
					this.list.removeRandom();
				} else {
					int num = rand.nextInt(initialSize);
					this.list.contains(num);
				}

				// System.out.println("Thread " + this.id + ": Removed gift " + number + " from
				// chain");
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
	}
}
