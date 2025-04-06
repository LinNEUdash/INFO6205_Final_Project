package application;

/**
 * A custom array-based implementation of the ListInterface.
 * This class provides a simple dynamic array implementation similar to Java's ArrayList.
 *
 * @param <T> the type of elements in this list
 */

public class ArrayListMaze<T> implements ListInterface<T> {
	private Object[] elements;
	private int size;
	private static final int INITIAL_CAPACITY = 10;
	
	/**
     * Default constructor.
     * Initializes the list with a default initial capacity.
     */
	public ArrayListMaze() {
		elements = new Object[INITIAL_CAPACITY];
		size = 0;
	}
	
	/**
     * Adds the specified element to the end of the list.
     * If the underlying array is full, it increases the array capacity before adding the element.
     * @param element the element to be added
     */
	public void add(T element) {
		if (size == elements.length) {
			// Check if the array is full. If so, double its capacity.
			Object[] newElements = new Object[elements.length * 2];
			// Copy the contents of the old array into the new array.
			System.arraycopy(elements, 0, newElements, 0, elements.length);
			elements = newElements;
		}
		// Add the element to the end of the list and increment the size.
		elements[size++] = element;
	}
	
	/**
     * Retrieves the element at the specified index.
     * @param index the index of the element to retrieve
     * @return the element at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
	public T get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index over bounds!");
		}
		@SuppressWarnings("unchecked")
		T result = (T) elements[index];
		return result;
	}
	
	 /**
     * Removes the element at the specified index and returns it.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     * @param index the index of the element to remove
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if the index is out of range
     */
	public T remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index over bounds!");
		}
		@SuppressWarnings("unchecked")
		T removed = (T) elements[index];
		// move the following elements
		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		size--;
		return removed;
	}
	
	/**
     * Returns the number of elements currently in the list.
     * @return the size of the list
     */
	public int size() {
		return size;
	}
	
	 /**
     * Removes all elements from the list.
     * Sets each element to null to assist garbage collection and resets the size.
     */
	public void clear() {
		// Set all positions in the array to null.
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}
	
	/** Replaces the element at the specified index with the given element.
     * @param index the index of the element to replace
     * @param element the new element to store at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
	public void set(int index, T element) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index over bounds!");
		}
		elements[index] = element;
	}
}
