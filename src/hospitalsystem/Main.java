package hospitalsystem;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static HospitalSystem system = new HospitalSystem();

    public static void main(String[] args) {
        system.initialize(); //load doctors
        runMainMenu();
        
    }

    private static void runMainMenu() {
        boolean isRunning = true;

        while (isRunning) {
            printMainMenu();
            int choice = getIntegerInput(0, 5);

            switch (choice) {
                case 1:
                    system.createNewPatient();
                    break;
                case 2:
                    system.updateExistingPatient();
                    break;
                case 3:
                    system.scheduleNewAppointment();
                    break;
                case 4:
                    // Updated to call the new search menu
                    system.searchPatientMenu();
                    break;
                case 5:
                    system.archivePatientRecord();
                    break;
                case 0:
                    isRunning = false;
                    System.out.println("Thank you for using the Hospital Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 5.");
            }

            if (isRunning) {
                System.out.println("\nPress Enter to return to the main menu...");
                scanner.nextLine(); // Wait for user
            }
        }
        scanner.close(); // Close the scanner when the program exits
    }

    private static void printMainMenu() {
        clearScreen();
        System.out.println("\n***********************************************");
        System.out.println("* *");
        System.out.println("* HOSPITAL MANAGEMENT SYSTEM MENU       *");
        System.out.println("* *");
        System.out.println("***********************************************");
        System.out.println("* 1. Create New Patient Record              *");
        System.out.println("* 2. Update Existing Patient's Information  *");
        System.out.println("* 3. Add Patient's Appointment              *");
        // Updated menu text
        System.out.println("* 4. Search/View Patient Information        *");
        System.out.println("* 5. Delete Patient's Information           *");
        System.out.println("* 0. Exit Program                           *");
        System.out.println("***********************************************");
        System.out.print("\nPlease input a number to continue: ");
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

