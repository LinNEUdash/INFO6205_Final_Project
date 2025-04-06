package application;

import java.util.EmptyStackException;
import java.util.Vector;

/**
 * A class of stacks whose entries are stored in a vector.
 */

public final class VectorStack<T> implements StackInterface<T> {
	private Vector<T> stack;
	private boolean initialized = false;
	private static final int DEFAULT_INITIAL_CAPACITY = 50;
    private static final int MAX_CAPACITY = 10000;

    /**
     * Default constructor with default initial capacity.
     */
    public VectorStack() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * A constructor that specifies the initial capacity and checks that the capacity is reasonable.
     * @param initialCapacity
     */
    public VectorStack(int initialCapacity) {
        checkCapacity(initialCapacity);
        stack = new Vector<T>(initialCapacity);// Size doubles as needed
        initialized = true;
    }
    
    /** 
     * Throws an exception if this object is not initialized.
     */
    private void checkInitialization()
    {
        if (!initialized)
             throw new SecurityException("VectorStack object is not initialized " +
                                        "properly.");
   }

    /** Determine if the asked for capacity is less than the maximum.
     * @param desiredCapacity The requested capacity for the stack
     */
    private void checkCapacity(int desiredCapacity){
        if (desiredCapacity > MAX_CAPACITY)
            throw new IllegalStateException("Attempt to create a stack " +
                                            "whose capacity exceeds " +
                                            "allowed maximum.");
    }
    
    /** Adds a new entry to the top of this stack.
     * @param newEntry an object to be added to the stack 
     */
    public void push(T newEntry){
        checkInitialization();
        stack.add(newEntry);
    }

    /** Removes and returns this stack’s top entry.
     * @return  The object at the top of the stack.
     * @throws EmptyStackException if the stack is empty before the operation. 
     */
    public T pop() {
        checkInitialization();
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            return stack.remove(stack.size() - 1);
        }
    }

    /** Retrieves this stack’s top entry.
     * @return The object at the top of the stack or null if
     * @throws EmptyStackException if the stack is empty. 
     */
    public T peek(){
        checkInitialization();
        if(isEmpty())
            throw new EmptyStackException();
        else
            return stack.lastElement();
    }

    /** Detects whether this stack is empty.
     * @return True if the stack is empty. 
     */
    public boolean isEmpty(){
    	checkInitialization();
        return stack.isEmpty();
    }
    
    /**
     * Returns the number of elements in this stack.
     * @return The size of the stack.
     */
    @Override
    public int size() {
        checkInitialization();
        return stack.size();
    }

    /** Removes all entries from this stack */
    public void clear(){
    	checkInitialization();
        stack.clear();
    }

    /** Override the toString() method so that we get a more useful display of 
     * the contents in the stack.
     * @return A string representation of the contents of the stack. 
     */
    public String toString() {
        String result = "Stack[ ";
        for (int index = 0; index < stack.size(); index++) {
            result += stack.get(index) + " ";
        }
        result += "]*Top*";
        return result;
    }
}
