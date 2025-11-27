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


public class HospitalSystem {
    private Map<String, Patient> patientMap;
    private List<Doctor> doctorList;
    private IDataManager dataManager;
    private Scanner scanner;

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
        System.out.println("Loaded " + patientMap.size() + " patient records (active & archived).");
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

    //crud operations
    public void createNewPatient(){
        System.out.println("--- Create New Patient Record ---");
        String name = getStringInput("Full Name: ");
        String dob = getStringInput("Date of Birth (e.g., YYYY-MM-DD): ");
        String sex = getValidatedStringInput("Sex (M or F): ", new String[]{"M", "F"}); //valid choices
        String address = getStringInput("Address: ");
        String contactNum = getStringInput("Contact Number: ");
        String emConName = getStringInput("Emergency Contact Name: ");
        String emConNum = getStringInput("Emergency Contact Number: ");
        String insurance = getStringInput("Insurance: ");
        String allergies = getStringInput("Allergies: ");
        String height = getStringInput("Height: ");
        String weight = getStringInput("Weight: ");

        String patientID = generateNewMrn();
        System.out.println("Generated " + name + "'s ID (MRN): " + patientID);

        Patient patient = new Patient(name, dob, sex, contactNum, patientID, address, emConName, emConNum, insurance, allergies, height, weight);

        patient.setChronicIllnesses(getYesNoDetails("Any chronic illnesses? (yes/no): "));
        patient.setPastSurgeries(getYesNoDetails("Any surgeries in the past? (yes/no): "));
        patient.setCurrentMedications(getYesNoDetails("Currently taking any medications? (yes/no): "));
        patient.setFamilyHistory(getYesNoDetails("Any family history of diseases? (yes/no): "));
        patient.setDisability(getYesNoDetails("Any disabilities? (yes/no): "));

        patientMap.put(patientID, patient); //add mrn and patient together to map
        dataManager.savePatient(patient); //save patient using data manager
        System.out.println("Patient record created successfully.");
    }

    public void searchPatientMenu(){
        System.out.println("""
        --- Search Patient Records ---
        1. Search by Name
        2. Search by Patient ID (MRN)
        0. Return to Main Menu
                """);
        int choice = Main.getIntegerInput(0, 2);
        Patient patient = null;

        switch (choice){
            case 1: patient = searchPatientByName(); break;
            case 2: patient = searchPatientByMRN(); break;
            case 0: return;
        }

        if (patient != null){
            System.out.println("\n--- Displaying Full Patient Record ---");
            if (patient.isArchived()) {
                System.out.println("!!! THIS PATIENT IS ARCHIVED - READ ONLY !!!");
            }
            System.out.println(patient.getDetails());
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
        } while (patientMap.containsKey(mrn)); //while patientmap has the same mrn
        return mrn;
    }

    private String getValidatedStringInput(String prompt, String[] validOptions) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            for (String option : validOptions) { //for each option in options
                if (input.equals(option.toLowerCase())) return input;
            }
            System.out.println("Invalid input. Please try again.");
        }
    }

    private String getYesNoDetails(String prompt) {
        String response = getValidatedStringInput(prompt, new String[]{"yes", "no"});
        if (response.equalsIgnoreCase("yes")) return getStringInput("Please specify: ");
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
            System.out.println("No patients found with the name containing: " + searchInput);
            return null;
        }

        System.out.println("\n--- Search Results ---");
        System.out.printf("%-5s %-10s %-25s %-15s %-10s\n", "ID", "MRN", "Name", "DOB", "Status");
        System.out.println("-----------------------------------------------------------------------");

        for (int i = 0; i < matchedPatients.size(); i++){
            Patient p = matchedPatients.get(i);
            String status = p.isArchived() ? "Archived" : "Active";
            System.out.printf("%-5d %-10s %-25s %-15s %-10s\n", i + 1, 
            p.getPatientID(), p.getName(), p.getDOB(), status);
        }
        System.out.println("-----------------------------------------------------------------------");

        System.out.println("Enter ID number of the patient to view full record, or 0 to cancel: ");
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
            System.out.println("Error: No patient found with MRN " + patientID);
            return null;
        }

    }

}

