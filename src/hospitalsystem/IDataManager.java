package hospitalsystem;
import java.util.List;

/**
 * IDataManager.java
 * Interface for data management
 */

public interface IDataManager {
    void savePatient(Patient patient);
    
    Patient loadPatient(String patientID);

    void archivePatient(Patient patient);
    
    boolean patientExists(Patient patientID);

    List<Patient> loadAllPatients();
}