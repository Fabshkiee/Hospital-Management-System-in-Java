package hospitalsystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

// Jackson Imports
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class FilePatientManager implements IDataManager {

    private final String patientDirectory;
    private final String archiveDirectory;
    private final ObjectMapper mapper; // The Jackson worker

    public FilePatientManager(String directory) {
        this.patientDirectory = directory;
        this.archiveDirectory = directory + "_archive";

        // Initialize Jackson
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule()); // Fix for Dates
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT); // Pretty printing

        try {
            Files.createDirectories(Paths.get(patientDirectory));
            Files.createDirectories(Paths.get(archiveDirectory));
        } catch (IOException e) {
            System.err.println("Error creating directories: " + e.getMessage());
        }
    }

    @Override
    public void savePatient(Patient patient) {
        String folder = patient.isArchived() ? archiveDirectory : patientDirectory;
        File file = Paths.get(folder, patient.getPatientID() + ".json").toFile();

        try {
            // Write the object directly to JSON
            mapper.writeValue(file, patient);
        } catch (IOException e) {
            System.err.println("Error saving patient: " + e.getMessage());
        }
    }

    @Override
    public Patient loadPatient(String patientID) {
        String fileName = patientID + ".json";
        Path path = Paths.get(patientDirectory, fileName);

        // Check active folder first, then archive
        if (!Files.exists(path)) {
            path = Paths.get(archiveDirectory, fileName);
        }

        if (!Files.exists(path)) return null;

        try {
            // Read JSON file directly into a Patient object
            return mapper.readValue(path.toFile(), Patient.class);
        } catch (IOException e) {
            System.err.println("Error loading patient: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void archivePatient(Patient patient) {
        String fileName = patient.getPatientID() + ".json";
        Path source = Paths.get(patientDirectory, fileName);
        Path target = Paths.get(archiveDirectory, fileName);

        try {
            if (Files.exists(source)) {
                // Move file
                Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);

                // Update object state and re-save (so the JSON file says "isArchived": true)
                patient.setArchived(true);
                savePatient(patient);
            }
        } catch (IOException e) {
            System.err.println("Error archiving: " + e.getMessage());
        }
    }

    @Override
    public boolean patientExists(String patientID) {
        return Files.exists(Paths.get(patientDirectory, patientID + ".json")) ||
                Files.exists(Paths.get(archiveDirectory, patientID + ".json"));
    }

    @Override
    public List<Patient> loadAllPatients() {
        List<Patient> list = new ArrayList<>();
        loadFromDir(patientDirectory, list);
        loadFromDir(archiveDirectory, list);
        return list;
    }

    private void loadFromDir(String dir, List<Patient> list) {
        File folder = new File(dir);
        // Only look for .json files
        File[] files = folder.listFiles((d, name) -> name.endsWith(".json"));

        if (files != null) {
            for (File f : files) {
                String id = f.getName().replace(".json", "");
                Patient p = loadPatient(id);
                if (p != null) list.add(p);
            }
        }
    }
}