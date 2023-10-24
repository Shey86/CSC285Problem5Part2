import java.io.File;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) {
        // Create instances of ExpressionParser and PrintWriter
        ExpressionParser expressionParser = new ExpressionParser();
        PrintWriter output;

        try {
            // Equate the internal name to an external file through the PrintWriter
            output = new PrintWriter(new File("JavaStackParserPart2.txt"));

            // Define the expression
            String expression1 = "2*A-3*((B-2*C)/(A+3)-B*3)";


            // Access the two-dimensional arrays from VariableDefinition class
            VariableDefinition variableDefinition = new VariableDefinition();
            int[][] arrayA = variableDefinition.getArrayA();
            int[][] arrayB = variableDefinition.getArrayB();
            int[][] arrayC = variableDefinition.getArrayC();

            // Evaluate regular expressions
            int result = expressionParser.evaluateExpression(expression1, arrayA, arrayB, arrayC, output);

            // Evaluate the array expression
            int[][] arrayResult = expressionParser.evaluateArrayExpression(expression1, arrayA, arrayB, arrayC);            // Evaluate regular expressions

            // Close the PrintWriter (output)
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

