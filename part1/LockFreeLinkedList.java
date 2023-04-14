package part1;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

public class LockFreeLinkedList<T extends Comparable<T>> {
	private AtomicReference<Node<T>> head;

	public LockFreeLinkedList() {
		Node<T> baseNode = new Node<>(null);
		this.head = new AtomicReference<>(baseNode);
	}

	public boolean removeRandom() {
		Node<T> pred = head.get();
		Node<T> curr = pred.next.get();

		while (curr != null) {
			if (ThreadLocalRandom.current().nextBoolean()) {
				break;
			}

			pred = curr;
			curr = curr.next.get();
		}

		// remove the node at the chosen index
		if (curr != null && pred.next.compareAndSet(curr, curr.next.get())) {
			curr.next.set(null);
			return true;
		} else {
			return false;
		}
	}

	public boolean contains(T data) {
		Node<T> curr = head.get().next.get();
		while (curr != null && data.compareTo(curr.data) > 0) {
			curr = curr.next.get();
		}
		return (curr != null && data.compareTo(curr.data) == 0);
	}

	public void insert(T data) {
		Node<T> newNode = new Node<>(data);
		while (true) {
			Node<T> pred = head.get();
			Node<T> curr = pred.next.get();
			while (curr != null && data.compareTo(curr.data) > 0) {
				pred = curr;
				curr = curr.next.get();
			}
			newNode.next.set(curr);
			if (pred.next.compareAndSet(curr, newNode)) {
				return;
			}
		}
	}

	public boolean delete(T data) {
		while (true) {
			Node<T> pred = head.get();
			Node<T> curr = pred.next.get();
			while (curr != null && data.compareTo(curr.data) > 0) {
				pred = curr;
				curr = curr.next.get();
			}
			if (curr == null || data.compareTo(curr.data) != 0) {
				return false;
			}
			Node<T> succ = curr.next.get();
			if (!pred.next.compareAndSet(curr, succ)) {
				continue;
			}
			curr.next.set(null);
			return true;
		}
	}

	private static class Node<T> {
		private T data;
		private AtomicReference<Node<T>> next;

		public Node(T data) {
			this.data = data;
			next = new AtomicReference<>(null);
		}
	}
}