package hospitalsystem;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static HospitalSystem system = new HospitalSystem();

    public static void main(String[] args) {
        system.initialize(); //load doctors
        //TODO: add main menu
    }

    public static int getIntegerInput(int min, int max) {
        int choice = -1;
        while (choice < min || choice > max) {
            try {
                choice = scanner.nextInt();
                if (choice < min || choice > max) {
                    System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter numbers only.");
                scanner.next(); // Clear the invalid input from the scanner
            }
        }
        scanner.nextLine(); // Consume the leftover newline character
        return choice;
    }


    public static void clearScreen() {
        // This is a simple way to "clear" the console
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            // Handle exceptions, e.g., print stack trace
            e.printStackTrace();
        }

    }
}

