import java.util.ArrayList;

/**
 * This class represents operators in the expression evaluation. It has a constructor to initialize
 * an operator object with an operator character and priority and has methods to get the operator's
 * character and priority.
 */

class OpertorObj {

    // Variables
    protected char operator;
    protected int priority;

    // This is the OpertorObj constructor
    public OpertorObj(char opert, int pri) {
        operator = opert;
        priority = pri;
    } // This is the end of the constructor

    // Method to get the priority
    public int Getprior() {
        return priority;
    }

    // Method to get the operator
    public char Getopert() {
        return operator;
    }
    // Method to print result to readable format
    @Override
    public String toString() {
        return "Operator " + operator + " with priority " + priority;
    }

}//this is the end of the operator class







