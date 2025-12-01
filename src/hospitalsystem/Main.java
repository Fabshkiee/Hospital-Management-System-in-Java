package hospitalsystem;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final HospitalSystem system = new HospitalSystem();

    public static void main(String[] args) {
        // Instantiate Login handler
        Login loginHandler = new Login(scanner);
        Dashboard dashboard = new Dashboard();
        dashboard.show(scanner);
        // Run Login
        if (loginHandler.performLogin()) {
            // If successful, initialize and run system
            system.initialize();
            runMainMenu();
        } else {
            System.out.println("Login failed. Exiting system.");
        }
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
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static void printMainMenu() {
        clearScreen();
        
        System.out.println("\n");
        // Header
        System.out.println(Colors.BLUE_BOLD + "   HOSPITAL MANAGEMENT SYSTEM" + Colors.RESET);
        System.out.println(Colors.BLUE + "   ──────────────────────────────────────────" + Colors.RESET);
        
        // Menu Options (Clean List)
        System.out.println("   " + Colors.WHITE_BOLD + "1." + Colors.RESET + " Register New Patient");
        System.out.println("   " + Colors.WHITE_BOLD + "2." + Colors.RESET + " Update Patient Info");
        System.out.println("   " + Colors.WHITE_BOLD + "3." + Colors.RESET + " Schedule Appointment");
        System.out.println("   " + Colors.WHITE_BOLD + "4." + Colors.RESET + " Search/View Records");
        System.out.println("   " + Colors.WHITE_BOLD + "5." + Colors.RESET + " Archive Patient Record");
        
        System.out.println(Colors.BLUE + "   ──────────────────────────────────────────" + Colors.RESET);
        System.out.println("   " + Colors.RED_BOLD + "0. LOGOUT" + Colors.RESET);
        System.out.println("\n");
        System.out.print(Colors.BLUE_BOLD + "   >> Select Option: " + Colors.RESET);
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
                scanner.next();
            }
        }
        scanner.nextLine();
        return choice;
    }

    public static void clearScreen() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            e.printStackTrace(); 
        }
    }
}