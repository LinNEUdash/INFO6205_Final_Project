package application;

import java.util.Comparator;

/**
 * A simple array-based priority queue implementation using a binary heap.
 * This priority queue maintains a min-heap structure.
 *
 * @param <T> the type of elements stored in the priority queue
 */
public class PriorityQueueScore<T> {
	private T[] heap;
	private int size;
	private Comparator<? super T> comp;
	private static final int INITIAL_CAPACITY = 10;
	
	public PriorityQueueScore(Comparator<? super T> comp) {
		this.comp = comp;
		this.heap = (T[]) new Object[INITIAL_CAPACITY];
		this.size = 0;
	}
	
	/**
     * Inserts a new element into the priority queue.
     * @param element the element to insert
     */
	public void insert(T element) {
		if (size == heap.length) {
			resize();
		}
		heap[size] = element;
		percolateUp(size);
		size++;
	}
	
	/**
     * Removes and returns the minimum element (the root) of the priority queue.
     * @return the minimum element
     * @throws IllegalStateException if the queue is empty
     */
	public T removeMin() {
		if (isEmpty()) {
			throw new IllegalStateException("Priority queue is empty");
		}
		T min = heap[0];
		heap[0] = heap[size - 1];
		size--;
		percolateDown(0);
		return min;
	}
	
	/**
     * Returns the minimum element without removing it.
     * @return the minimum element
     */
	public T peek() {
		if (isEmpty()) {
			throw new IllegalStateException("Priority queue is empty");
		}
		return heap[0];
	}
	
	/**
     * Checks whether the priority queue is empty.
     * @return true if empty, false otherwise
     */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
     * Returns the number of elements in the priority queue.
     * @return the size of the queue
     */
	public int size() {
		return size;
	}
	
	/**
     * Returns a copy of the elements in the priority queue as an array.
     * The order in the returned array is not guaranteed to be sorted.
     * @return an array of elements
     */
	public T[] toArray() {
		T[]copy = (T[]) new Object[size];
		System.arraycopy(heap, 0, copy, 0, size);
		return copy;
	}
	
	private void resize() {
		T[] newHeap = (T[]) new Object[heap.length * 2];
		System.arraycopy(heap, 0, newHeap, 0, heap.length);
		heap = newHeap;
	}
	
	private void percolateUp(int index) {
		T temp = heap[index];
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (comp.compare(temp, heap[parent]) >= 0) {
                break;
            }
            heap[index] = heap[parent];
            index = parent;
        }
        heap[index] = temp;
	}
	
	private void percolateDown(int index) {
		T temp = heap[index];
        while (index * 2 + 1 < size) {
            int child = index * 2 + 1;
            if (child + 1 < size && comp.compare(heap[child + 1], heap[child]) < 0) {
                child++;
            }
            if (comp.compare(temp, heap[child]) <= 0) {
                break;
            }
            heap[index] = heap[child];
            index = child;
        }
        heap[index] = temp;
	}
}
