public class VariableDefinition {

    private int[][] arrayA;
    private int[][] arrayB;
    private int[][] arrayC;


    // Variable table
    private char[] vart = {'A', 'B', 'C', 'D', 'E', 'F', '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9'}; // Note that we can only use variables A, B, C, D, E, F

    // Corresponding integer array holding the values of the variables in the vart table
    private int[] ivalue = {8, 12, 2, 3, 15, 4, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    // Operator table
    private char[] opert = {'@', '%', '*', '/', '+', '-', ')', '(', '#'}; // This is the set of operators

    // Evaluation priority for the symbols
    private int[] intvalp = {3, 2, 2, 2, 1, 1, 99, -99, -100};

    // Getter methods for accessing the tables
    public char[] getVariableTable() {
        return vart;
    }

    public int[] getVariableValues() {
        return ivalue;
    }

    public char[] getOperatorTable() {
        return opert;
    }

    public int[] getOperatorPriorities() {
        return intvalp;
    }

    public int[][] getArrayA() {
        if (arrayA == null) {
            arrayA = new int[][] {{1, 2}, {3, 4}};
        }
        return arrayA;
    }

    public int[][] getArrayB() {
        if (arrayB == null) {
            arrayB = new int[][] {{6, 6}, {8, 8}};
        }
        return arrayB;
    }

    public int[][] getArrayC() {
        if (arrayC == null) {
            arrayC = new int[][] {{1, 2}, {2, 1}};
        }
        return arrayC;
    }
}