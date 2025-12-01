package hospitalsystem;

import java.util.Scanner;

public class Dashboard {

    // Updated LOGO with block text, Nurse Head, and Box with Credit inside
    private static final String TITLE = Colors.CYAN_BOLD + """
                                           ░█▄▒▄█▒██▀░█▀▄░█░█▒░░█░█▄░█░█▄▀
                                           ░█▒▀▒█░█▄▄▒█▄▀░█▒█▄▄░█░█▒▀█░█▒█
            """ + Colors.RESET;

    private static final String LOGO = """
                                                  %1$s░░░░░░░░░░░░░░░░%2$s
                                                %1$s░░░┈┈┈┈┈┈%3$s▒▒%1$s┈┈┈┈┈┈░░░%2$s
                                               %1$s░░┈┈┈┈┈┈%3$s▒▒▒▒▒▒%1$s┈┈┈┈┈┈░░%2$s
                                               %1$s░░██┈┈┈┈┈┈%3$s▒▒%1$s┈┈┈┈┈┈██░░%2$s
                                               %1$s██████┈┈██████┈┈██████%2$s
                                             %1$s██████████████████████████%2$s
                                           %1$s██████████████████████████████%2$s
                                             %1$s██████░░░░░░░░░░░░░░██████%2$s
                                           %1$s██████░░░░██░░░░░░██░░░░██████%2$s
                                             %1$s████░░░░██░░░░░░██░░░░████%2$s
                                           %1$s██████░░▒▒▒▒░░░░░░▒▒▒▒░░██████%2$s
                                             %1$s██████░░░░░░░░░░░░░░██████%2$s
                                               %1$s▓▓▓▓┈┈░░░░░░░░░░┈┈▓▓▓▓%2$s
            """.formatted(Colors.WHITE_BOLD, Colors.RESET, Colors.RED_BOLD, Colors.CYAN_BOLD);

    private static final String CREDITS = """
                              %1$s┌───────────────────────────────────────────────────┐
                              │            HOSPITAL MANAGEMENT SYSTEM             │
                              │            v2.0 | Java Version | 2025             │
                              │                                                   │
                              │    [ Records • Appointments • Data Archival ]     │
                              │                                                   │
                              │         SYSTEM BY: Ryuske Sendo Fabros            │
                              └───────────────────────────────────────────────────┘%2$s
            """.formatted(Colors.YELLOW, Colors.RESET);

    public void show(Scanner scanner) {
        Main.clearScreen();
        System.out.println(TITLE);
        System.out.print(LOGO);
        System.out.println(CREDITS);

        System.out.println("\n\n            " + Colors.GREEN + "Press Enter to continue to Login..." + Colors.RESET);
        scanner.nextLine();
    }
}