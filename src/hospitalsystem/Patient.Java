package hospitalsystem;
import java.util.ArrayList;
import java.util.List; 

/*
 * Patient.java
 * inherits from Person.java
 * stores patient-specific info
 */

public class Patient extends Person{
    // Patient-specific fields
    private String patientID; // This will be the MRN
    private String address;
    private String emergencyContactName;
    private String emergencyContactNumber;
    private String insurance;
    private String allergies;
    private String height;
    private String weight;

    // Medical History
    private String chronicIllnesses;
    private String pastSurgeries;
    private String currentMedications;
    private String familyHistory;
    private String disability;

    private List<Appointment> appointments; // Patient "has-a" list of Appointments

    //constructor
    public Patient(String name, String dateOfBirth, String gender, String patientID, String address,
                   String emergencyContactName, String emergencyContactNumber,
                   String insurance, String allergies, String height, String weight,
                   String chronicIllnesses, String pastSurgeries, String currentMedications,
                   String familyHistory, String disability) {
        super(name, dateOfBirth, gender); // Call Person constructor
        this.patientID = patientID;
        this.address = address;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactNumber = emergencyContactNumber;
        this.insurance = insurance;
        this.allergies = allergies;
        this.height = height;
        this.weight = weight;
        this.chronicIllnesses = "None";
        this.pastSurgeries = "None";
        this.currentMedications = "None";
        this.familyHistory = "None";
        this.disability = "None";

        this.appointments = new ArrayList<>(); // Initialize empty list of appointments            
    }
                
    //getters
    public String getPatientID() { return patientID; }
    public String getAddress() { return address; }
    public String getEmergencyContactName() { return emergencyContactName; }
    public String getEmergencyContactNumber() { return emergencyContactNumber; }
    public String getInsurance() { return insurance; }
    public String getAllergies() { return allergies; }
    public String getHeight() { return height; }
    public String getWeight() { return weight; }
    public String getChronicIllnesses() { return chronicIllnesses; }
    public String getPastSurgeries() { return pastSurgeries; }
    public String getCurrentMedications() { return currentMedications; }
    public String getFamilyHistory() { return familyHistory; }
    public String getDisability() { return disability; }
    public List<Appointment> getAppointments() { return appointments; }

    //setters
    public void setPatientID(String patientID) { this.patientID = patientID; }
    public void setAddress(String address) { this.address = address; }
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }
    public void setEmergencyContactNumber(String emergencyContactNumber) { this.emergencyContactNumber = emergencyContactNumber; }
    public void setInsurance(String insurance) { this.insurance = insurance; }
    public void setAllergies(String allergies) { this.allergies = allergies; }
    public void setHeight(String height) { this.height = height; }
    public void setWeight(String weight) { this.weight = weight; }
    public void setChronicIllnesses(String chronicIllnesses) { this.chronicIllnesses = chronicIllnesses; }
    public void setPastSurgeries(String pastSurgeries) { this.pastSurgeries = pastSurgeries; }
    public void setCurrentMedications(String currentMedications) { this.currentMedications = currentMedications; }
    public void setFamilyHistory(String familyHistory) { this.familyHistory = familyHistory; }
    public void setDisability(String disability) { this.disability = disability; }
    public void addAppointment(Appointment appointment) { this.appointments.add(appointment); }

    @Override
    public String getDetails() {
        Stringbuilder sb = new Stringbuilder(); //much for effient string chaining
        sb.append("************-- ").append(getName()).append("'s Medical Information --************\n");
        sb.append("MRN: ").append(patientID).append("\n");
        sb.append("1. Name: ").append(getName()).append("\n");
        sb.append("2. Date of Birth: ").append(getDateOfBirth()).append("\n"); // Changed from age
        sb.append("3. Sex: ").append(getSex()).append("\n");
        sb.append("4. Address: ").append(address).append("\n");
        sb.append("5. Height: ").append(height).append("\n");
        sb.append("6. Weight: ").append(weight).append("\n");
        sb.append("7. Contact Number: ").append(getContactNumber()).append("\n");
        sb.append("8. Emergency Contact: ").append(emergencyContactName).append("\n");
        sb.append("9. Emergency Contact's Number: ").append(emergencyContactNumber).append("\n");
        sb.append("10. Insurance: ").append(insurance).append("\n");
        sb.append("11. Allergies: ").append(allergies).append("\n");

        sb.append("\n|| Health Information ||\n");
        sb.append("Chronic Illnesses: ").append(chronicIllnesses).append("\n");
        sb.append("Past Surgeries: ").append(pastSurgeries).append("\n");
        sb.append("Current Medications: ").append(currentMedications).append("\n");
        sb.append("Family History: ").append(familyHistory).append("\n");
        sb.append("Disabilities: ").append(disability).append("\n");
        
        sb.append("|| Appointment History ||\n");
        if (appointments.isEmpty()){
            sb.append("No appointments found.\n");
        } else {
            for (Appointment app : appointments) { //loop through appointments
                sb.append("--------------------------------------------------\n");
                sb.append(app.getDetails()); // get appointment details from appointment.java
                sb.append("--------------------------------------------------\n");
            }
        }
        sb.append("***********************************************************\n");
        return sb.toString();
    }
}
