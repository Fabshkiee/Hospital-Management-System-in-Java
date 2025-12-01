package hospitalsystem;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    private boolean isArchived = false;

    public Patient(){
        super("", "", "", "");
        this.appointments = new ArrayList<>();
    }


    //constructor
    @JsonCreator
    public Patient(@JsonProperty("name") String name,
                   @JsonProperty("dateOfBirth") String dateOfBirth,
                   @JsonProperty("sex") String sex,
                   @JsonProperty("contactNumber") String contactNumber,
                   @JsonProperty("patientID") String patientID,
                   @JsonProperty("address") String address,
                   @JsonProperty("emergencyContactName") String emergencyContactName,
                   @JsonProperty("emergencyContactNumber") String emergencyContactNumber,
                   @JsonProperty("insurance") String insurance,
                   @JsonProperty("allergies") String allergies,
                   @JsonProperty("height") String height,
                   @JsonProperty("weight") String weight) {

        super(name, dateOfBirth, sex, contactNumber);
        this.patientID = patientID;
        this.address = address;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactNumber = emergencyContactNumber;
        this.insurance = insurance;
        this.allergies = allergies;
        this.height = height;
        this.weight = weight;
        this.appointments = new ArrayList<>();

        this.chronicIllnesses = "None";
        this.pastSurgeries = "None";
        this.currentMedications = "None";
        this.familyHistory = "None";
        this.disability = "None";
        this.isArchived = false;
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

    @JsonProperty("isArchived") // Forces the key to be "isArchived"
    public boolean isArchived() { return isArchived; }

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
    public void setArchived(boolean archived) { isArchived = archived; }
    public void addAppointment(Appointment appointment) { this.appointments.add(appointment); }

    @Override
    @JsonIgnore
    public String getDetails() {
        StringBuilder sb = new StringBuilder();
        
        // Define formatters for Appointment dates
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("hh:mm a");

        // Prepare safe strings (handle nulls)
        String mrn = (patientID != null) ? patientID : "N/A";
        String pName = getName();
        String pDob = getDOB();
        String pSex = getSex();
        String pAddr = (address != null && !address.isEmpty()) ? address : "N/A";
        String pH = (height != null) ? height : "N/A";
        String pW = (weight != null) ? weight : "N/A";
        String pContact = getContactNumber();
        String pEmName = (emergencyContactName != null) ? emergencyContactName : "N/A";
        String pIns = (insurance != null) ? insurance : "N/A";
        String pAlg = (allergies != null) ? allergies : "None";

        // Truncate long strings to fit table if necessary
        if (pAddr.length() > 37) pAddr = pAddr.substring(0, 34) + "...";
        if (pAlg.length() > 57) pAlg = pAlg.substring(0, 54) + "...";
        if (pIns.length() > 57) pIns = pIns.substring(0, 54) + "...";
        if (pEmName.length() > 57) pEmName = pEmName.substring(0, 54) + "...";

        // HEADER
        sb.append("+=============================================================================+\n");
        sb.append(String.format("|                       PATIENT MEDICAL RECORD: %-29s |\n", mrn));
        sb.append("+=============================================================================+\n");

        // DEMOGRAPHICS & VITALS SECTION
        sb.append("| PERSONAL DETAILS                                     | VITALS               |\n");
        sb.append("|------------------------------------------------------+----------------------|\n");
        sb.append(String.format("| Name:      %-41s | Height: %-12s |\n", pName, pH));
        sb.append(String.format("| DOB:       %-41s | Weight: %-12s |\n", pDob, pW));
        sb.append(String.format("| Sex:       %-41s | Phone:  %-12s |\n", pSex, pContact));
        sb.append(String.format("| Address:   %-41s |                      |\n", pAddr));
        sb.append("|------------------------------------------------------+----------------------|\n");

        // HEALTH INFO SECTION
        sb.append("| HEALTH INFORMATION                                                          |\n");
        sb.append("|-----------------------------------------------------------------------------|\n");
        sb.append(String.format("| Allergies:        %-57s |\n", pAlg));
        sb.append(String.format("| Insurance:        %-57s |\n", pIns));
        sb.append(String.format("| Emergency:        %-57s |\n", pEmName));
        sb.append("+=============================================================================+\n\n");

        // APPOINTMENTS HEADER
        sb.append("+-----------------------------------------------------------------------------+\n");
        sb.append("| APPOINTMENT HISTORY                                                         |\n");
        sb.append("+-----------------------------------------------------------------------------+\n");
        sb.append("| Date       | Time     | Doctor                | Concern                     |\n");
        sb.append("|------------|----------|-----------------------|-----------------------------|\n");

        // APPOINTMENTS LOOP
        if (appointments == null || appointments.isEmpty()) {
             sb.append(String.format("| %-75s |\n", "No appointment history found."));
        } else {
            for (Appointment app : appointments) {
                // 1. Prepare raw data
                String dateStr = app.getAppointmentDate().format(dateFmt);
                String timeStr = app.getAppointmentTime().format(timeFmt);
                
                String docName = (app.getDoctor() != null) ? app.getDoctor().getName() : "Unknown";
                String docSpec = (app.getDoctor() != null) ? app.getDoctor().getSpecialty() : "";
                String docFull = docName + (!docSpec.equals("N/A") ? " (" + docSpec + ")" : "");
                String concernFull = (app.getConcerns() != null) ? app.getConcerns() : "";

                // 2. Wrap text for Doctor (width 21) and Concern (width 27)
                List<String> docLines = wrapText(docFull, 21);
                List<String> concernLines = wrapText(concernFull, 27);

                // 3. Determine height of this row (max lines needed)
                int maxLines = Math.max(docLines.size(), concernLines.size());

                // 4. Print the row line by line
                for (int i = 0; i < maxLines; i++) {
                    String d = (i == 0) ? dateStr : ""; // Date only on first line
                    String t = (i == 0) ? timeStr : ""; // Time only on first line
                    String doc = (i < docLines.size()) ? docLines.get(i) : "";
                    String con = (i < concernLines.size()) ? concernLines.get(i) : "";

                    sb.append(String.format("| %-10s | %-8s | %-21s | %-27s |\n", d, t, doc, con));
                }
                
                sb.append("|            |          |                       |                             |\n"); 
            }
        }
        sb.append("+-----------------------------------------------------------------------------+\n");

        return sb.toString();
    }

    private List<String> wrapText(String text, int width) {
        List<String> lines = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            lines.add("");
            return lines;
        }

        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            // If adding the next word exceeds the width...
            if (currentLine.length() + word.length() + (currentLine.length() > 0 ? 1 : 0) > width) {
                // Add the current line to the list
                lines.add(currentLine.toString());
                // Start a new line
                currentLine = new StringBuilder();
            }
            // Add space if it's not the start of the line
            if (currentLine.length() > 0) {
                currentLine.append(" ");
            }
            currentLine.append(word);
        }
        // Add the last line
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }
        return lines;
    }
}