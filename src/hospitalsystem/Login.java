package hospitalsystem;

import java.util.Scanner;

public class Login {
    private Scanner scanner;

    public Login(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean performLogin() {
        int attempts = 0;
        while (attempts < 3) {
            Main.clearScreen();

            System.out.println("\n\n");
            System.out.println(Colors.BLUE_BOLD + "   AUTHENTICATION REQUIRED" + Colors.RESET);
            System.out.println(Colors.BLUE + "   ───────────────────────" + Colors.RESET);

            System.out.print("   Username: ");
            String user = scanner.nextLine().trim();

            System.out.print("   Password: ");
            String pass = scanner.nextLine().trim();
            
            System.out.println(Colors.BLUE + "   ───────────────────────" + Colors.RESET);

            if (user.equals("admin") && pass.equals("admin")) {
                System.out.println("\n   " + Colors.GREEN_BOLD + ">> Login Successful." + Colors.RESET);
                try { Thread.sleep(800); } catch (Exception e) {}
                return true;
            } else {
                attempts++;
                System.out.println("\n   " + Colors.RED + ">> Invalid Credentials. Try again." + Colors.RESET);
                System.out.print("   Press Enter...");
                scanner.nextLine();
            }
        }
        return false;
    }
}