import java.util.ArrayList;

/**
 * The GenericManagerStacks class serves as a generic stack data structure,
 * that stores and manipulates elements of a generic type (T).
 */
class GenericManagerStacks<T> {

    // Declare generic ArrayList(mystack) to store (T) elements/nodes
    protected ArrayList<T> mystack;
    // Declare integer number to keep track of the number of elements in stack
    protected int number;

    // This is the GenericManagerStacks constructor
    public GenericManagerStacks() {

        number = 0; // Set number to 0 to indicate stack is empty initially
        mystack = new ArrayList<T>(100);// Creates an initial arraylist of 100
    } // This is the end of the GenericManagerStacks constructor

    // This method returns the number of nodes in the stack
    public int getnumber() {
        return number;
    }

    // This method pushes(adds) a node on the stack
    public int pushnode(T x) {
        // Prints message, stating pushnode method is being called and node is being pushed on the stack
        System.out.println("In pushnode " + number + " node is " + x.toString());
        // This pushes(adds) a node on the stack.  It will always add to the front(top) of the stack
        mystack.add(number, x);
        number++; // Increment number by 1
        System.out.println("Leaving pushnode"); // Prints message, stating method pushnode has completed
        return number; // Returns the updated number (new number of elements on the stack)
    }// End of pushnode

    // This method pops(removes) a node off the stack
    public T popnode() {
        if (number == 0) { // Check if stack is empty
            System.out.println("Stack is empty.");
            return null; // Return null if stack is empty
        }
        T nodeval;// This is the value in the node to be popped
        // Find the node at the head of the list and assign it to nodeval
        nodeval = mystack.get(number - 1);
        // Pop the node by taking it off the list and moving head
        mystack.remove(number - 1);
        number--; // Decrement number by 1 to indicate that an element has been popped off the stack
        // Return the value of this node.
        return nodeval;
    }// This is the end of popnode

    /*
    This function returns the contents of the top of the stack.  It does not
    pop the node, just allows the user to look (peek) at the contents of the
    first node on the stack.
     */
    public T peeknode() {

        T nodeval; // This is the value to be peeked
        nodeval = mystack.get(number - 1); // Retrieves the element at the top of
        // the the stack and assigns it to nodeval
        return nodeval;
    }// This is the end of peeknode

    // This method returns true if the stack is empty
    boolean stackempty() {
        if (number == 0) return true;
        else return false;
    }
}// End of GenericManager class