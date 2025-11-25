package hospitalsystem;
import java.io.File; //for file handling
import java.io.FileWriter; //for writing to files
import java.io.IOException; //io exception handling
import java.io.PrintWriter; //format text output
import java.nio.file.Files; //file operations like move or copy
import java.nio.file.Path; //file path representation
import java.nio.file.Paths; //path object
import java.nio.file.StandardCopyOption;  //for replace_existing
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; //for time
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Implementation of IDataManager that handles patient data using file storage.
 */
public class FilePatientManager implements IDataManager {
    private final String patientDirectory;
    private final String archiveDirectory;

    //time formats
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("h:mm a");
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yy h:mm:ss a");

    //constructor
    public FilePatientManager(String directory){
        this.patientDirectory = directory;
        this.archiveDirectory = directory + File.separator + "_archive";

        try {
            Files.createDirectories(Paths.get(patientDirectory));
            Files.createDirectories(Paths.get(archiveDirectory));
        } catch (IOException e){
            System.out.println("Error creating directories: " + e.getMessage());
        }
    }

    @Override
    public void savePatient(Patient patient){
        String folder = patient.isArchived() ? archiveDirectory : patientDirectory; //if archived save to archive folder

        String fileName = patient.getPatientID() + ".txt"; //file name based on mrn
        Path filePath = Paths.get(folder, fileName);

        try (FileWriter fw = new FileWriter(filePath.toFile(), false); //locate and open file for writing
            PrintWriter pw = new PrintWriter(fw)) { //wrap file writer in print writer for formatted output
                pw.println(patient.getPatientID());
                pw.println(patient.getName());
                pw.println(patient.getDOB());
                pw.println(patient.getSex());
                pw.println(patient.getAddress());
                pw.println(patient.getContactNumber());
                pw.println(patient.getEmergencyContactName());
                pw.println(patient.getEmergencyContactNumber());
                pw.println(patient.getInsurance());
                pw.println(patient.getAllergies());
                pw.println(patient.getHeight());
                pw.println(patient.getWeight());
                pw.println(patient.getChronicIllnesses());
                pw.println(patient.getPastSurgeries());
                pw.println(patient.getCurrentMedications());
                pw.println(patient.getFamilyHistory());
                pw.println(patient.getDisability());

                pw.print("---APPOINTMENTS---");
                for (Appointment app : patient.getAppointments()) {
                    pw.println(app.toFileString()); // Save each appointment
                }
            } catch (IOException e) {
                System.out.println("Error saving patient data: " + e.getMessage());
            }
    }

    @Override
    public void archivePatient(Patient patient){
        String fileName = patient.getPatientID() + ".txt";
        Path sourcePath = Paths.get(patientDirectory, fileName);
        Path targetPath = Paths.get(archiveDirectory, fileName);

        try{
            if(Files.exists(sourcePath)){
                Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING); //move ang file to archive and replace if same exist
            }
        } catch (IOException e){
            System.out.println("Error archiving patient file: " + e.getMessage());
        }
    }


    @Override
    public Patient loadPatient(String patientID){
        String fileName = patientID + ".txt";

        // Try active folder first
        Path filePath = Paths.get(patientDirectory, fileName);
        boolean isArchived = false;
        
        // If not in active, try archive folder
        if (!Files.exists(filePath)) {
            filePath = Paths.get(archiveDirectory, fileName);
            isArchived = true;
        }

        if (!Files.exists(filePath)) {
            return null; // Not found anywhere
        }

        try (Scanner fileScanner = new Scanner(filePath.toFile())){ //scans file
            String id = fileScanner.nextLine(); //read lines in order
            String name = fileScanner.nextLine();
            String dob = fileScanner.nextLine();
            String sex = fileScanner.nextLine();
            String address = fileScanner.nextLine();
            String contactNum = fileScanner.nextLine();
            String emConName = fileScanner.nextLine();
            String emConNum = fileScanner.nextLine();
            String insurance = fileScanner.nextLine();
            String allergies = fileScanner.nextLine();
            String height = fileScanner.nextLine();
            String weight = fileScanner.nextLine();

            Patient patient = new Patient(name, dob, sex, contactNum,
                                          id, address,
                                          emConName, emConNum,
                                          insurance, allergies, height, weight); //create patient object
            patient.setArchived(isArchived); //set archived status

            patient.setChronicIllnesses(fileScanner.nextLine());
            patient.setPastSurgeries(fileScanner.nextLine());
            patient.setCurrentMedications(fileScanner.nextLine());
            patient.setFamilyHistory(fileScanner.nextLine());
            patient.setDisability(fileScanner.nextLine());

            if (fileScanner.hasNextLine()) fileScanner.nextLine(); // Skip appointments header

            while (fileScanner.hasNextLine()){
                String appLine = fileScanner.nextLine();
                Appointment app = parseAppointmentFromString(patient, appLine);
                if (app != null) patient.addAppointment(app); 
            }

            return patient; //output the patient object

        } catch (IOException e){
            System.out.println("Error loading patient file: " +  fileName + ": " + e.getMessage());
            return null;
        }
    }













}   
