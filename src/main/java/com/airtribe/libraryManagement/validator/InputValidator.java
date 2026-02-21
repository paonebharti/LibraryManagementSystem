package main.java.com.airtribe.libraryManagement.validator;

import java.time.Year;
import java.util.Scanner;

public class InputValidator {

    private InputValidator() {}

    public static int readInt(Scanner scanner, String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid integer.");
            }
        }
    }

    public static String readNonEmptyString(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty.");
        }
    }

    public static int readValidYear(Scanner scanner, String message) {
        while (true) {
            int year = readInt(scanner, message);

            if (year > 0 && year <= Year.now().getValue()) {
                return year;
            }

            System.out.println("Enter a valid year.");
        }
    }

    public static String readValidIsbn(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String isbn = scanner.nextLine().trim();

            if (isbn.matches("\\d{10}") || isbn.matches("\\d{13}")) {
                return isbn;
            }

            System.out.println("Invalid ISBN. Must be 10 or 13 digits.");
        }
    }

    public static int readPositiveInt(Scanner scanner, String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = Integer.parseInt(scanner.nextLine().trim());

                if (value > 0) {
                    return value;
                }

                System.out.println("Value must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid integer.");
            }
        }
    }
}
