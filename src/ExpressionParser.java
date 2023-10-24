import java.io.PrintWriter;

public class ExpressionParser {

    // Member variables for tables and stacks
    private static char[] vart;
    private static int[] ivalue;
    private char[] opert;
    private int[] intvalp;
    private GenericManagerStacks<Integer> opnd;
    private GenericManagerStacks<OpertorObj> oper;

    public ExpressionParser() {
        // Initialize tables and stacks
        vart = new char[]{'A', 'B', 'C', 'D', 'E', 'F', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        ivalue = new int[]{8, 12, 2, 3, 15, 4, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        opert = new char[]{'@', '%', '*', '/', '+', '-', ')', '(', '#'};
        intvalp = new int[]{3, 2, 2, 2, 1, 1, 99, -99, -100};
        opnd = new GenericManagerStacks<Integer>();
        oper = new GenericManagerStacks<OpertorObj>();

        // Initialize the operator stack with an end of operation
        OpertorObj pnode1 = new OpertorObj('#', -100);
        oper.pushnode(pnode1);
    }

    public static int findval(char x, char[] vtab, int[] valtb, int last) {
        int i, vreturn = -99;
        // This finds the character x in the value table vtab and returns the
        // correspond integer value from valtb
        for (i = 0; i <= last; i++)
            if (vtab[i] == x) vreturn = valtb[i];
        System.out.println("Found this char " + x + " priority is " + vreturn);
        return vreturn;
    }

    public int evaluateExpression(String expression, int[][] arrayA, int[][] arrayB, int[][] arrayC, PrintWriter output) {
        // Initialize the result array with zeros
        int[][] result = new int[arrayA.length][arrayA[0].length];


        char[] express = expression.toCharArray();
        int i = 0;

        // local operand and operator stacks
        GenericManagerStacks<Integer> opnd = new GenericManagerStacks<>();
        GenericManagerStacks<OpertorObj> oper = new GenericManagerStacks<>();

        while (i < express.length) {
            if (express[i] != '#') {
                System.out.println("Parsing " + express[i]);
                if (((express[i] >= '0') && (express[i] <= '9')) || ((express[i] >= 'A') && (express[i] <= 'F'))) {
                    // Operand handling
                    int ivalu = findval(express[i], vart, ivalue, 15);
                    if (ivalu == -99) {
                        System.out.println("No value in the table for " + express[i]);
                    } else {
                        System.out.println("Pushing it on the operand stack " + ivalu);
                        opnd.pushnode(ivalu);
                    }
                } else {
                    // Operator handling
                    if (express[i] == '(') {
                        // Left parenthesis
                        System.out.println("Pushing on operator stack " + express[i]);
                        OpertorObj pnodeo = new OpertorObj(express[i], -99);
                        oper.pushnode(pnodeo);
                    } else if (express[i] == ')') {
                        // Right parenthesis
                        while (oper.peeknode().operator != '(') {
                            popevalandpush(oper, opnd);
                        }
                        oper.popnode();
                    } else {
                        // Other operators
                        char currentOperator = express[i]; // The current operator character
                        OpertorObj operatorObj = new OpertorObj(currentOperator, 0); // Create an instance of OpertorObj with priority 0
                        int oprior = operatorObj.getPriority(); // Get the priority using the method

                        if (!oper.stackEmpty() && oper.peeknode().operator != '(') {
                            popevalandpush(oper, opnd);
                        }

                        System.out.println("Pushing Operator " + express[i] + " with priority " + oprior);

                        // Now create an instance of OpertorObj with the actual priority
                        operatorObj = new OpertorObj(currentOperator, oprior);
                        oper.pushnode(operatorObj);
                    }

                }
                i++;
            }
        }

        // Perform element-wise multiplication
        result = performArrayOperation(result, arrayA, arrayB, "*", 2);

        // Print intermediate result
        System.out.println("Intermediate result after element-wise multiplication:");
        printArray(result);
        return i;
    }


    public static int[][] evaluateArrayExpression(String expression, int[][] arrayA, int[][] arrayB, int[][] arrayC) {
        int[][] result = new int[arrayA.length][arrayA[0].length];

        String[] components = expression.split(" "); // Split the expression into its components

        for (String component : components) {
            if (component.matches("[A-C]")) {
                // Handle variables (A, B, C) and perform element-wise operations with arrays
                performArrayOperation(arrayA, arrayB, arrayC, component, (int) 0.0);
            } else if (component.matches("[*/-]")) {
                // Handle operators (*, /, -) and apply element-wise operations with arrays
                performArrayOperation(arrayA, arrayB, arrayC, component, (int) 0.0);
            } else {
                try {
                    int constant = Integer.parseInt(component);
                    // Handle constants and apply element-wise operations with arrays
                    performArrayOperation(arrayA, arrayB, arrayC, "+", constant);
                } catch (NumberFormatException e) {
                    // Handle invalid components
                    System.err.println("Invalid component: " + component);
                }
            }
        }

        return arrayC;
    }

    /* This method pops the operators and operands from the stack and evaluates them
    and pushes the result back on the stack
      */
    public static void popevalandpush(GenericManagerStacks<OpertorObj> x,
                                      GenericManagerStacks<Integer> y) {
        int a, b, c = 0;
        char operandx;
        operandx = (x.popnode()).getOperator();
        a = y.popnode();
        b = y.popnode();
        System.out.println("Pop, Evaluate, and Push " + b + " " + operandx + " " + a);

        // Perform the operation directly
        if (operandx == '+') {
            c = a + b;
        } else if (operandx == '-') {
            c = b - a;
        } else if (operandx == '*') {
            c = a * b;
        } else if (operandx == '/') {
            if (a != 0) {
                c = b / a;
            } else {
                // Handle division by zero error
                System.err.println("Division by zero error.");
                // TODO: throw an exception or handle it differently
            }
        } else {
            // Handle other operators if needed
            System.err.println("Unsupported operator: " + operandx);
            // TODO: hrow an exception or handle it differently
            return;
        }

        // Push the result back on the stack for integers
        y.pushnode(c);
    }

    public static int[][] performArrayOperation(int[][] result, int[][] arrayA, int[][] arrayB, String operator, int constant) {
        for (int i = 0; i < arrayA.length; i++) {
            for (int j = 0; j < arrayA[0].length; j++) {
                if (operator.equals("*")) {
                    result[i][j] = arrayA[i][j] * arrayB[i][j];
                } else if (operator.equals("/")) {
                    if (arrayB[i][j] != 0) {
                        result[i][j] = arrayA[i][j] / arrayB[i][j];
                    }
                } else if (operator.equals("-")) {
                    result[i][j] = arrayA[i][j] - arrayB[i][j];
                } else if (operator.equals("+")) {
                    result[i][j] = arrayA[i][j] + arrayB[i][j];
                }

                // Apply the constant if necessary
                if (constant != 0) {
                    result[i][j] = (int) (result[i][j] + constant);
                }
            }
        }
        return result;
    }

    public static void evaluateArrayExpression(String expression, PrintWriter output, int[] arrayA, int[] arrayB, int[] arrayC) {
        // Split the expression into its components (variables and operators)
        String[] components = expression.split(" ");
        int result = 0;

        for (int i = 0; i < components.length; i++) {
            if (components[i].equals("A")) {
                // If the component is "A", use the value from arrayA
                result += arrayA[i];
            } else if (components[i].equals("B")) {
                // If the component is "B", use the value from arrayB
                result += arrayB[i];
            } else if (components[i].equals("*")) {
                // If the component is "*", perform multiplication
                result *= Integer.parseInt(components[i + 1]);
                i++; // Skip the next component (the value to multiply)
            }
        }

        // Store the result in arrayC
        arrayC[0] = result;

        // Print the result to the console and the output file
        System.out.println("Result: " + result);
        output.println("Result: " + result);
    }

    // Helper method to print a 2D array
    public void printArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }

    }
}
