package application;

/**
 * A custom list interface that defines basic list operations.
 * @param <T> the type of elements in this list
 */
public interface ListInterface<T> {
	/** Adds an element to the end of the list.
     * @param element the element to add
     */
	void add(T element);
	
	/** Retrieves the element at the specified index.
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     */
	T get(int index);
	
	/** Removes the element at the specified index and returns it.
     * @param index the index of the element to remove
     * @return the removed element
     */
	T remove(int index);
	
	/** Returns the number of elements currently in the list.
     * @return the size of the list
     */
	int size();
	
	/** Remove all elements in list */
	void clear();
	
	/** Replaces the element at the specified index with the given element.
     * @param index the index of the element to replace
     * @param element the new element to be stored at the specified index
     */
	void set(int index, T element);
}
