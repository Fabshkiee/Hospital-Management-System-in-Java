package hospitalsystem;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator; //sorting
import java.util.List;
import java.util.Map; 
import java.util.HashMap; 
import java.util.Scanner;
import java.util.function.Consumer; 


public class HospitalSystem {
    private final Map<String, Patient> patientMap;
    private final List<Doctor> doctorList;
    private final IDataManager dataManager;
    private final Scanner scanner;

    // Define Doctor's Shift
    private static final LocalTime SHIFT_START = LocalTime.of(9, 0); // 9:00 AM
    private static final LocalTime SHIFT_END = LocalTime.of(19, 0);  // 7:00 PM
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("h:mm a");

    //constructor
    public HospitalSystem() {
        this.patientMap = new HashMap<>();
        this.doctorList = new ArrayList<>();
        this.dataManager = new FilePatientManager("patients");
        this.scanner = new Scanner(System.in);
        loadAllPatients();
    }
    
    private void loadAllPatients() {
        List<Patient> patients = dataManager.loadAllPatients(); //data manager loads all patients
        for (Patient p : patients) {
            patientMap.put(p.getPatientID(), p); //map patientID to Patient object
        }
        System.out.println(Colors.GREEN + "   >> System Loaded: " + patientMap.size() + " records found." + Colors.RESET);
    }

    //initialize doctors
    public void initialize() {
        doctorList.add(new Doctor("Dr. Kim", "N/A", "M", "N/A", "Orthopedic Surgeon"));
        doctorList.add(new Doctor("Dr. Patel", "N/A", "F", "N/A", "Rheumatologist"));
        doctorList.add(new Doctor("Dr. Lee", "N/A", "M", "N/A", "Physical Therapist"));
        doctorList.add(new Doctor("Dr. Chen", "N/A", "M", "N/A", "Psychiatrist"));
        doctorList.add(new Doctor("Dr. Ramirez", "N/A", "F", "N/A", "Psychologist"));
        doctorList.add(new Doctor("Dr. Singh", "N/A", "M", "N/A", "Clinical Psychologist"));
        doctorList.add(new Doctor("Dr. Louis", "N/A", "F", "N/A", "Obstetrician-Gynecologist"));
        doctorList.add(new Doctor("Dr. Smith", "N/A", "M", "N/A", "Family Medicine Physician"));
        doctorList.add(new Doctor("Dr. Will", "N/A", "M", "N/A", "Urologist"));
        doctorList.add(new Doctor("Dr. Ling", "N/A", "F", "N/A", "Pediatrician"));
    }

    private void printHeader(String title) {
        Main.clearScreen();
        System.out.println("\n");
        System.out.println(Colors.BLUE_BOLD + "   " + title + Colors.RESET);
        System.out.println(Colors.BLUE + "   " + "─".repeat(title.length()) + Colors.RESET);
        System.out.println("");
    }

    //crud operations
    public void createNewPatient(){
        printHeader("──── CREATE NEW PATIENT RECORD ────");
        System.out.println(Colors.WHITE + "   Please enter the following details:" + Colors.RESET);
        String name = getStringInput("Full Name: ");
        String dob = getStringInput("Date of Birth (e.g., YYYY-MM-DD): ");
        String sex = getValidatedStringInput("Sex (M or F): ", new String[]{"M", "F"}); //valid choices
        String address = getStringInput("Address: ");
        String contactNum = getStringInput("Contact Number: ");
        System.out.println(Colors.BLUE + "\n   -- Emergency Info --" + Colors.RESET);
        String emConName = getStringInput("Emergency Contact Name: ");
        String emConNum = getStringInput("Emergency Contact Number: ");
        System.out.println(Colors.BLUE + "\n   -- Medical Profile --" + Colors.RESET);
        String insurance = getStringInput("Insurance: ");
        String allergies = getStringInput("Allergies: ");
        String height = getStringInput("Height (m): ");
        String weight = getStringInput("Weight (kg): ");

        String patientID = generateNewMrn();
        System.out.println(Colors.CYAN + "\n>> Generating MRN... " + Colors.WHITE_BOLD + patientID + Colors.RESET);

        Patient patient = new Patient(name, dob, sex, contactNum, patientID, address, emConName, emConNum, insurance, allergies, height, weight);

        System.out.println(Colors.BLUE + "\n-- Medical History Questionnaire --" + Colors.RESET);
        patient.setChronicIllnesses(getYesNoDetails("Any chronic illnesses? (yes/no): "));
        patient.setPastSurgeries(getYesNoDetails("Any surgeries in the past? (yes/no): "));
        patient.setCurrentMedications(getYesNoDetails("Currently taking any medications? (yes/no): "));
        patient.setFamilyHistory(getYesNoDetails("Any family history of diseases? (yes/no): "));
        patient.setDisability(getYesNoDetails("Any disabilities? (yes/no): "));

        patientMap.put(patientID, patient); //add mrn and patient together to map
        dataManager.savePatient(patient); //save patient using data manager
        System.out.println("\n   " + Colors.GREEN_BOLD + ">> Success! Patient record created." + Colors.RESET);
    }

    public void searchPatientMenu(){
        printHeader("──── SEARCH PATIENT RECORDS ────");
        System.out.println("   " + Colors.WHITE_BOLD + "[1]" + Colors.RESET + " Search by Name");
        System.out.println("   " + Colors.WHITE_BOLD + "[2]" + Colors.RESET + " Search by MRN");
        System.out.println("   " + Colors.WHITE_BOLD + "[0]" + Colors.RESET + " Return");

        System.out.println(Colors.BLUE + "   ─────────────────────────" + Colors.RESET);
        System.out.print(Colors.BLUE_BOLD + "   >> Select: " + Colors.RESET);
        int choice = Main.getIntegerInput(0, 2);
        Patient patient = null;

        switch (choice){
            case 1: patient = searchPatientByName(); break;
            case 2: patient = searchPatientByMRN(); break;
            case 0: return;
        }

        if (patient != null){
            Main.clearScreen();
            System.out.println("\n─── Displaying Full Patient Record ───");
            if (patient.isArchived()) {
                System.out.println(Colors.RED_BOLD + "\n   !!! ARCHIVED RECORD - READ ONLY !!!" + Colors.RESET);
            }
            System.out.println(patient.getDetails());
        }
    }

    public void updateExistingPatient(){
        printHeader("──── UPDATE PATIENT INFORMATION ────");
        Patient patient = searchPatientByName();
        if (patient == null) return;

        if (patient.isArchived()) {
            System.out.println(Colors.RED + "   >> ERROR: Cannot update an Archived patient." + Colors.RESET);
            return;
        }

        System.out.println(Colors.CYAN + "\n   Updating: " + Colors.WHITE_BOLD + patient.getName() + Colors.RESET);
        System.out.println(Colors.WHITE + "   (Press Enter to skip a field)" + Colors.RESET);
        updateStringField("Name", patient.getName(), patient::setName);
        updateStringField("Date of Birth", patient.getDOB(), patient::setDOB);
        updateStringField("Address", patient.getAddress(), patient::setAddress);
        updateStringField("Contact Number", patient.getContactNumber(), patient::setContactNumber);
        updateStringField("Emergency Contact Name", patient.getEmergencyContactName(), patient::setEmergencyContactName);
        updateStringField("Emergency Contact Number", patient.getEmergencyContactNumber(), patient::setEmergencyContactNumber);
        updateStringField("Insurance", patient.getInsurance(), patient::setInsurance);
        updateStringField("Allergies", patient.getAllergies(), patient::setAllergies);
        updateStringField("Height", patient.getHeight(), patient::setHeight);
        updateStringField("Weight", patient.getWeight(), patient::setWeight);

        dataManager.savePatient(patient);
        System.out.println("\n   " + Colors.GREEN_BOLD + ">> Update Complete." + Colors.RESET);
    }

    public void archivePatientRecord() {
        printHeader("──── ARCHIVE PATIENT RECORD ────");
        Patient patient = searchPatientByName();
        if (patient == null) return;

        if (patient.isArchived()) {
            System.out.println(Colors.YELLOW + "   >> This patient is already archived." + Colors.RESET);
            return;
        }

        Main.clearScreen();
        System.out.println(Colors.RED + "\n   WARNING: Archiving " + patient.getName() + " (MRN: " + patient.getPatientID() + ")." + Colors.RESET);
        System.out.println("   Archived patients cannot be modified.");
        String confirm = getValidatedStringInput("   Are you sure? (yes/no): ", new String[]{"yes", "no"});

        if (confirm.equalsIgnoreCase("yes")) {
            // Archive Logic
            dataManager.archivePatient(patient);
            System.out.println("\n   " + Colors.GREEN_BOLD + ">> Patient archived successfully." + Colors.RESET);
        } else {
            Main.clearScreen();
            System.out.println(Colors.YELLOW + "   >> Operation cancelled." + Colors.RESET);
        }
    }

    public void scheduleNewAppointment() {
        Main.clearScreen();
        System.out.println("--- Schedule New Appointment ---");
        Patient patient = searchPatientByName();
        Main.clearScreen();

        if (patient == null) return;

        if (patient.isArchived()) {
            System.out.println(Colors.RED + "   >> ERROR: Cannot schedule for Archived patient." + Colors.RESET);
            return;
        }

        System.out.println(Colors.CYAN + "   Patient: " + Colors.WHITE_BOLD + patient.getName() + Colors.RESET);

        // 1. Get Concern
        System.out.println("   \nCategories of Concern:");
        System.out.println("   1. Physical Health");
        System.out.println("   2. Mental Health");
        System.out.println("   3. Women's Health");
        System.out.println("   4. Men's Health");
        System.out.println("   5. Child Health");
        System.out.print(Colors.BLUE_BOLD + "   >> Choice: " + Colors.RESET);
        int categoryChoice = Main.getIntegerInput(1, 5);
        String concerns = getStringInput("Briefly describe the concern: ");

        // 2. Select Doctor
        Doctor selectedDoctor = selectDoctor(categoryChoice);

        // 3. Display Doctor's Existing Schedule
        displayDoctorSchedule(selectedDoctor);

        // 4. Get Date and Time (With Validation)
        LocalDateTime validDateTime = getValidAppointmentDateTime(selectedDoctor);

        // 5. Save
        Appointment appointment = new Appointment(patient, selectedDoctor, validDateTime.toLocalDate(), validDateTime.toLocalTime(), concerns);
        patient.addAppointment(appointment);
        dataManager.savePatient(patient);
        Main.clearScreen();
        System.out.println("\n   " + Colors.GREEN_BOLD + ">> Appointment Confirmed!" + Colors.RESET);
        System.out.println("   Patient: " + patient.getName());
        System.out.println("   Doctor: " + selectedDoctor.getName());
        System.out.println("   Date: " + validDateTime.format(DATE_FMT));
        System.out.println("   Time: " + validDateTime.format(TIME_FMT));
    }

    private void displayDoctorSchedule(Doctor doctor) {
        System.out.println("\n   Checking schedule for " + Colors.WHITE_BOLD + doctor.getName() + Colors.RESET + "...");
        System.out.println("   Doctor's Shift: " + SHIFT_START.format(TIME_FMT) + " - " + SHIFT_END.format(TIME_FMT));

        List<Appointment> doctorApps = new ArrayList<>();

        // Search ALL patients to find appointments for this doctor
        for (Patient p : patientMap.values()) {
            for (Appointment app : p.getAppointments()) {
                // Check if appointment is for this doctor and is in the future
                if (app.getDoctor().getName().equals(doctor.getName()) &&
                        (app.getAppointmentDate().isAfter(LocalDate.now()) || app.getAppointmentDate().isEqual(LocalDate.now()))) {
                    doctorApps.add(app);
                }
            }
        }

        if (doctorApps.isEmpty()) {
            System.out.println(Colors.GREEN + "   >> No upcoming appointments found. Status: AVAILABLE" + Colors.RESET);
        } else {
            // Sort by date/time
            doctorApps.sort(Comparator.comparing(Appointment::getAppointmentDate).thenComparing(Appointment::getAppointmentTime));

            System.out.println(Colors.YELLOW + "   >> Busy Slots:" + Colors.RESET);
            for (Appointment app : doctorApps) {
                System.out.println(app.getAppointmentDate().format(DATE_FMT) + " at " +
                        app.getAppointmentTime().format(TIME_FMT) + " -- [OCCUPIED]");
            }
            System.out.println("───────────────────────────");
        }
    }

    private LocalDateTime getValidAppointmentDateTime(Doctor doctor) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");

        while (true) {
            try {
                // Get Date
                System.out.println("\n   " + Colors.BLUE_BOLD + ">> Enter Date" + Colors.RESET + " (MM/dd/yy): ");
                System.out.print("   Input: ");
                String dateStr = scanner.nextLine();
                LocalDate date = LocalDate.parse(dateStr, dateFormatter);

                if (date.isBefore(LocalDate.now())) {
                    System.out.println(Colors.RED + "   >> Error: Date must be in the future." + Colors.RESET);
                    continue;
                }

                // Get Time
                System.out.println("\n   " + Colors.BLUE_BOLD + ">> Enter Time" + Colors.RESET + " (e.g. 9:00 AM, 2:30 PM): ");
                System.out.println("   Note: 30-min intervals only (9:00, 9:30...)");
                System.out.print("   Input: ");
                String timeStr = scanner.nextLine();
                LocalTime time = LocalTime.parse(timeStr.toUpperCase(), timeFormatter);

                // 1. Shift Check
                if (time.isBefore(SHIFT_START) || time.isAfter(SHIFT_END)) {
                    System.out.println(Colors.RED + "   >> Error: Doctor is off duty." + Colors.RESET);
                    continue;
                }

                // 2. Interval Check
                if (time.getMinute() != 0 && time.getMinute() != 30) {
                    System.out.println(Colors.RED + "   >> Error: Appointments must be on the hour or half-hour (e.g. 9:00 or 9:30)." + Colors.RESET);
                    continue;
                }

                // 3. Conflict Check
                boolean conflict = false;
                for (Patient p : patientMap.values()) {
                    for (Appointment app : p.getAppointments()) {
                        if (app.getDoctor().getName().equals(doctor.getName()) &&
                                app.getAppointmentDate().equals(date) &&
                                app.getAppointmentTime().equals(time)) {
                            conflict = true;
                            break;
                        }
                    }
                }

                if (conflict) {
                    System.out.println(Colors.RED + "   >> Error: Slot occupied." + Colors.RESET);
                    continue;
                }

                return LocalDateTime.of(date, time);

            } catch (DateTimeParseException e) {
                System.out.println(Colors.RED + "   >> Invalid format. Try again." + Colors.RESET);
            }
        }
    }



    //helper methods
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private String generateNewMrn(){
        String mrn;
        do { 
            int n = (int)(Math.random() * 900000) + 100000; //generate random 6-digit number
            mrn = String.valueOf(n);
        } while (patientMap.containsKey(mrn)); //while patientMap has the same mrn
        return mrn;
    }

    private String getValidatedStringInput(String prompt, String[] validOptions) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            for (String option : validOptions) { //for each option in options
                if (input.equals(option.toLowerCase())) return input;
            }
            System.out.println(Colors.RED + "   >> Invalid input." + Colors.RESET);
        }
    }

    private String getYesNoDetails(String prompt) {
        String response = getValidatedStringInput(prompt, new String[]{"yes", "no"});
        if (response.equalsIgnoreCase("yes")) return getStringInput("   Please specify: ");
        return "None";
    }

    private Patient searchPatientByName(){
        System.out.println("Enter Patient Name to search: ");
        String searchInput = scanner.nextLine().trim();

        List<Patient> matchedPatients = new ArrayList<>();
        for (Patient p : patientMap.values()){
            if (p.getName().toLowerCase().contains(searchInput.toLowerCase())){
                matchedPatients.add(p); //add matched patients to list
            }
        }

        if (matchedPatients.isEmpty()){
            System.out.println(Colors.YELLOW + "   >> No patients found with the name containing: " + Colors.WHITE_BOLD + searchInput + Colors.RESET);
            return null;
        }
        Main.clearScreen();
        System.out.println("\n   " + Colors.CYAN + "─── Search Results ───" + Colors.RESET);
        System.out.printf("   %-5s %-10s %-25s %-15s %-10s\n", "ID", "MRN", "Name", "DOB", "Status");
        System.out.println("   ──────────────────────────────────────────────────────────────────────");

        for (int i = 0; i < matchedPatients.size(); i++){
            Patient p = matchedPatients.get(i);
            String status = p.isArchived() ? "Archived" : "Active";
            System.out.printf("   %-5d %-10s %-25s %-15s %-10s\n", i + 1, 
            p.getPatientID(), p.getName(), p.getDOB(), status);
        }
        System.out.println("   ──────────────────────────────────────────────────────────────────────");

        System.out.println("\n   " + Colors.WHITE + "Enter ID number of the patient or 0 to cancel: " + Colors.RESET);
        System.out.print(Colors.BLUE_BOLD + "   >> Choice: " + Colors.RESET);
        int choice = Main.getIntegerInput(0, matchedPatients.size());

        if (choice == 0) {
            return null; //cancel
        } 
        return matchedPatients.get(choice - 1); //return selected patient

    }
    private Patient searchPatientByMRN(){
        String patientID = getStringInput("Enter Patient's MRN: ");
        if (patientMap.containsKey(patientID)){
            return patientMap.get(patientID);
        } else {
            System.out.println(Colors.RED + "   >> Error: No patient found with MRN: " + Colors.RESET + patientID);
            return null;
        }

    }

    private void updateStringField(String fieldName, String currentValue, Consumer<String> setter) {
        System.out.print(Colors.BLUE + "   " + fieldName + Colors.RESET + " [" + currentValue + "]: ");
        String input = scanner.nextLine();
        if (!input.trim().isEmpty()) setter.accept(input); 
    }

    private Doctor selectDoctor(int category) {
        List<Doctor> availableDoctors = new ArrayList<>();
        switch (category) {
            case 1:
                availableDoctors.add(doctorList.get(0));
                availableDoctors.add(doctorList.get(1));
                availableDoctors.add(doctorList.get(2));
                break;
            case 2:
                availableDoctors.add(doctorList.get(3));
                availableDoctors.add(doctorList.get(4));
                availableDoctors.add(doctorList.get(5));
                break;
            case 3:
                availableDoctors.add(doctorList.get(6));
                availableDoctors.add(doctorList.get(7));
                break;
            case 4:
                availableDoctors.add(doctorList.get(7));
                availableDoctors.add(doctorList.get(8));
                break;
            case 5:
                availableDoctors.add(doctorList.get(9));
                break;
        }

        System.out.println("\n" + Colors.WHITE_BOLD + "Available Doctors:" + Colors.RESET);
        for (int i = 0; i < availableDoctors.size(); i++) {
            Doctor doc = availableDoctors.get(i);
            System.out.println("   "+(i + 1) + ". " + doc.getName() + " (" + doc.getSpecialty() + ")");
        }
        System.out.print(Colors.BLUE_BOLD + "   >> Select Doctor: " + Colors.RESET);
        int choice = Main.getIntegerInput(1, availableDoctors.size());
        return availableDoctors.get(choice - 1);
    }
}