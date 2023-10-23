import java.io.File;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) {

        // Create a PrintWriter
        PrintWriter output;

        try {
            // Equate the internal name to an external file through the PrintWriter
            output = new PrintWriter(new File("JavaStackParcerPart2.txt"));

            // Definitions of arrays A, B, and C
            int[][] arrayA = {{1, 2}, {3, 4}};
            int[][] arrayB = {{6, 6}, {8, 8}};
            int[][] arrayC = {{1, 2}, {2,1}} ;

            String expression = "2*A-3*((B-2*C)/(A+3)-B*3)";
            // Create an evaluator for integer operators
            evaluateExpression(expression, output, arrayA, arrayB, arrayC);

            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //*************************************Creating the Operator Table ******************************************
        // Create the symbols for the operators table
        char[] opert = {'@', '%', '*', '/', '+', '-', ')', '(', '#'};// This is the set of operators
        // Create the evaluation priority for the symbols. The higher the priority, the higher the number in the table
        int[] intvalp = {3, 2, 2, 2, 1, 1, 99, -99, -100};
        //************************************Creating the Stacks for the Operands and the Operators*****************
        // Create a stack for the integer operands
        GenericManagerStacks<Integer> opnd = new GenericManagerStacks<Integer>();
        // Create a stack for the Operators
        GenericManagerStacks<OpertorObj> oper = new GenericManagerStacks<OpertorObj>();
        // WE MUST INITIALIZE the OPERATOR STACK so the first Operator can be pushed on.
        // Must put an end of operation on the operator stack.
        System.out.println("Pushing Operator # with priority -100");
        //Create the operator object and push it on the stack.
        OpertorObj pnode1 = new OpertorObj('#', -100);
        oper.pushnode(pnode1);
        int oprior, exvalue;
        //**************************************Done with table and Stack creation*************************************
    }

    public static void evaluateExpression(String expression, PrintWriter output, int[] arrayA, int[] arrayB,
                                          int[] arrayC) {
        // Convert the expression from String to char array
        char[] express = expression.toCharArray();
        int i = 0;
        int num, ivalu;
        char[] vart = {'A', 'B', 'C'};
        int[][] ivalue = {{1, 2}, {3, 4}, {6, 6}, {8, 8}, {1, 2}, {2, 1}};
        ;// Definitions of arrays A, B, and C


        while (express[i] != '#') {
            System.out.println("Parsing " + express[i]);
            GenericManagerStacks<Integer> opnd = null;
            if (((express[i] >= '0') && (express[i] <= '9')) || ((express[i] >= 'A') && (express[i] <= 'F')))
            // Check to see if this character is a variable or an operator.
            {// we have a variable or a constant
                System.out.println("This is an operand " + express[i]);
                // Find the character in the vart table that corresponds with the value
                ivalu = findval(express[i], vart, ivalue, 15);
                if (ivalu == -99) System.out.println("No value in table for " + express[i]);
                // Now that we have the value we need to place it on the operand stack
                System.out.println("Were pushing it on the operand stack " + ivalu);
                opnd.pushnode(ivalu);
            }// End of variable stack
            else {// We are an operator
                System.out.println("This is an operator " + express[i]);
                GenericManagerStacks<OpertorObj> oper = null;
                if (express[i] == '(') { // This is a left parenthesis, push it on the stack
                    System.out.println("Pushing on operator stack " + express[i]);
                    // Create node to push on stack
                    OpertorObj pnodeo = new OpertorObj(express[i], -99);
                    oper.pushnode(pnodeo);
                } else if (express[i] == ')') {// This is a right parenthesis, we must begin to pop operands and operators
                    //until we find the a left parenthesis (
                    while ((oper.peeknode()).operator != '(') {// Must pop and evaluate the stuff on operand and operator stack
                        popevalandpush(oper, opnd);
                    }
                    // Now pop the ( node
                    oper.popnode();
                }// End of this is a right parenthesis
                else {// This is not either ( or ) is is another operator
                    int oprior = 0;
                    System.out.println("Peeking at top of stack " + (oper.peeknode()).priority);
                    //**********oprior MUST BE STRICTLY GREATER THAN BEFORE WE CAN PUT IT ON THE STACK********
                    while (oprior <= (oper.peeknode()).priority) popevalandpush(oper, opnd);
                    // Now push this operator on the stack.
                    System.out.println("Pushing Operator " + express[i] + " with priority " + oprior);
                    OpertorObj pnodeo = new OpertorObj(express[i], oprior);
                    oper.pushnode(pnodeo);
                }// This is the end of this is not () operator
            }// End of on operator stack
            i++;
        }// End of while express loop
    }

    // This method evaluates the operators in the operator table and prints the result
    public static int IntEval(int oper1, char oper, int oper2) {// This is an evaluator for binary operators operating on integers.
        int result;
        switch (oper) {
            case '+':
                result = oper1 + oper2;
                System.out.println("Evaluating  " + oper1 + " " + oper + " " + oper2 + " result: " + result);
                return result;
            case '-':
                result = oper1 - oper2;
                System.out.println("Evaluating  " + oper1 + " " + oper + " " + oper2 + " result: " + result);
                return result;
            case '*':
                result = oper1 * oper2;
                System.out.println("Evaluating  " + oper1 + " " + oper + " " + oper2 + " result: " + result);
                return result;
            case '/':
                if (oper2 != 0) {
                    result = oper1 / oper2;
                    return result;
                } else {
                    System.out.println("Attempted division by zero not allowed.");
                    return -99;
                }
                // Add two more symbols to the operator table
            case '@': // Handle the '@' operator as exponentiation
                if (oper == '@') {
                    // Ensure that exponential values are not allowed
                    if (oper1 == 0 && oper2 < 0) {
                        System.out.println("Exponential values not allowed.");
                        return -99;
                    }
                    // Handle anything raised to the power of 0 as 1
                    if (oper2 == 0) {
                        result = 1;
                    } else {
                        result = (int) Math.pow(oper1, oper2);
                    }
                    System.out.println("Evaluating  " + oper1 + " " + oper + " " + oper2 + " result: " + result);
                    return result;
                }
            case '%': // Handle '%' as the modulus operator
                result = oper1 % oper2;
                System.out.println("Evaluating  " + oper1 + " " + oper + " " + oper2 + " result: " + result);
                return result;
            default:
                System.out.println("Bad operator: " + oper);
                return -99;
        }// End of switch(oper)
    }// End of IntEval

// Everything outside of this is for the rules, priority, etc

    // This method finds the character x in the value table and returns its integer value
    public static int findval(char x, char[] vtab, int[][] valtb, int last) {
        int i, vreturn = -99;
        // This finds the character x in the value table vtab and returns the
        // correspond interger value table from valtb
        for (i = 0; i <= last; i++)
            if (vtab[i] == x) vreturn = valtb[i][i];
        System.out.println("Found this char " + x + " priority is " + vreturn);
        return vreturn;
    }// End of findval;

    /* This method pops the operators and operands from the stack and evaluates them
    and pushes the result back on the stack
      */
    public static void popevalandpush(GenericManagerStacks<OpertorObj> x,
                                      GenericManagerStacks<Integer> y) {// This is the start of pop and push
        int a, b, c;
        char operandx;
        operandx = (x.popnode()).Getopert();
        a = y.popnode();
        b = y.popnode();
        System.out.println("Pop, Evaluate, and Push " + b + " " + operandx + " " + a);
        c = IntEval(b, operandx, a);
        // Now push the value back on the stack for integers
        y.pushnode(c);
        return;
    }// This is the end of popevalandpush


    public static void evaluateExpression(String expression, PrintWriter output, int[][] arrayA, int[][] arrayB, int[][] arrayC) {
        // Your previous expression evaluation logic here...

        // Perform element-wise multiplication
        performArrayOperation(arrayA, arrayB, arrayC, "*", 2.0);

        // Perform element-wise division
        performArrayOperation(arrayA, arrayB, arrayC, "/", 2.0);

        // Perform element-wise subtraction
        performArrayOperation(arrayA, arrayB, arrayC, "-", 2.0);

        // Perform element-wise addition
        performArrayOperation(arrayA, arrayB, arrayC, "+", 2.0);


        // Print the resulting arrayC
        System.out.println("Result:");
        printArray(arrayC);


    }

    public static void performArrayOperation(int[][] arrayA, int[][] arrayB, int[][] arrayC, String operator, double constant) {
        for (int i = 0; i < arrayA.length; i++) {
            for (int j = 0; j < arrayA[0].length; j++) {
                if (operator.equals("*")) {
                    arrayC[i][j] = arrayA[i][j] * arrayB[i][j];
                } else if (operator.equals("/")) {
                    if (arrayB[i][j] != 0) {
                        arrayC[i][j] = arrayA[i][j] / arrayB[i][j];
                    }
                } else if (operator.equals("-")) {
                    arrayC[i][j] = arrayA[i][j] - arrayB[i][j];
                } else if (operator.equals("+")) {
                    arrayC[i][j] = arrayA[i][j] + arrayB[i][j];
                }

                // Apply the constant if necessary
                if (constant != 0.0) {
                    arrayC[i][j] = (int) (arrayC[i][j] + constant);
                }
            }
        }
    }

    // Helper method to print a 2D array
    private static void printArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }
}

/*
    public static void evaluateExpression1(String expression, PrintWriter output, int[][] arrayA, int[][] arrayB, int[][] arrayC) {
        // Split the expression into its components (variables and operators)
        String[] components = expression.split(" ");

        // Initialize a result array to store the final result
        int[][] result = new int[arrayA.length][arrayA[0].length];

        for (int i = 0; i < components.length; i++) {
            if (components[i].equals("A")) {
                // If the component is "A", perform division of corresponding elements
                for (int row = 0; row < arrayA.length; row++) {
                    for (int col = 0; col < arrayA[0].length; col++) {
                        if (result[row][col] == 0) {
                            result[row][col] = arrayA[row][col];
                        } else {
                            result[row][col] /= arrayA[row][col];
                        }
                    }
                }
            } else if (components[i].equals("B")) {
                // If the component is "B", perform division of corresponding elements
                for (int row = 0; row < arrayB.length; row++) {
                    for (int col = 0; col < arrayB[0].length; col++) {
                        if (result[row][col] == 0) {
                            result[row][col] = arrayB[row][col];
                        } else {
                            result[row][col] /= arrayB[row][col];
                        }
                    }
                }
            } else if (components[i].equals("/")) {
                // If the component is "/", perform division by a constant
                int constant = Integer.parseInt(components[i + 1]);
                for (int row = 0; row < result.length; row++) {
                    for (int col = 0; col < result[0].length; col++) {
                        result[row][col] /= constant;
                    }
                }
                i++; // Skip the next component (the constant)
            }
        }

        // Store the result in arrayC
        for (int row = 0; row < result.length; row++) {
            System.arraycopy(result[row], 0, arrayC[row], 0, result[row].length);
        }

        // Print the result to the console and the output file
        System.out.println("Result:");
        printArray(result);
        output.println("Result:");
        output.println(result);
    }
}

 */



