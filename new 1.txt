import java.util.Scanner;

public class SimpleCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Simple Java Calculator ===");

        // Input first number
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();

        // Input operator
        System.out.print("Enter an operator (+, -, *, /): ");
        char operator = scanner.next().charAt(0);

        // Input second number
        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();

        double result;
        boolean validOperation = true;

        // Perform calculation based on the operator
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 == 0) {
                    System.out.println("Error: Division by zero is not allowed.");
                    validOperation = false;
                    result = 0;
                } else {
                    result = num1 / num2;
                }
                break;
            default:
                System.out.println("Error: Invalid operator entered.");
                validOperation = false;
                result = 0;
        }

        // Display the output if no error occurred
        if (validOperation) {
            System.out.println("Result: " + num1 + " " + operator + " " + num2 + " = " + result);
        }

        scanner.close();
    }
}