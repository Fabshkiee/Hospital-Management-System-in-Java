package hospitalsystem;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; //import for dates and times
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * * Appointment.java
 * appointment information.
 * It demonstrates Encapsulation.
 */
public class Appointment {

    @JsonIgnore
    private Patient patient;

    @JsonIgnore
    private Doctor doctor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("date") // Renames to "date"
    private LocalDate appointmentDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @JsonProperty("time") // Renames to "time"
    private LocalTime appointmentTime;

    private String concerns;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeCreated; //timestamp when appointment created

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("hh:mm a");
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yy h:mm:ss a");

    public Appointment(Patient patient, Doctor doctor, LocalDate appointmentDate, LocalTime appointmentTime, String concerns) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.concerns = concerns;
        this.timeCreated = LocalDateTime.now(); // Set booking time to now
    }

    // Constructor used for JSON Deserialization (Loading from file)
    // We create a temporary Doctor object here to satisfy the class requirement
    @JsonCreator
    public Appointment(
            @JsonProperty("date") LocalDate date,
            @JsonProperty("time") LocalTime time,
            @JsonProperty("concerns") String concerns,
            @JsonProperty("doctorName") String doctorName,
            @JsonProperty("doctorSpecialty") String doctorSpecialty,
            @JsonProperty("timeCreated") LocalDateTime timeCreated) {

        this.appointmentDate = date;
        this.appointmentTime = time;
        this.concerns = concerns;
        this.timeCreated = timeCreated;

        // Reconstruct a placeholder Doctor object so the system doesn't crash on load
        this.doctor = new Doctor(doctorName, "N/A", "N/A", "N/A", doctorSpecialty);
    }

    // Getters and Setters

    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public LocalDateTime getTimeCreated() { return timeCreated; }

    public String getConcerns() { return concerns; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }
    public void setConcerns(String concerns) { this.concerns = concerns; }
    public void setTimeCreated(LocalDateTime timeCreated) { this.timeCreated = timeCreated; }

    // Custom Getters for JSON Output to flatten the Doctor object
    @JsonProperty("doctorName")
    public String getDoctorName() {
        return doctor != null ? doctor.getName() : "Unknown";
    }

    @JsonProperty("doctorSpecialty")
    public String getDoctorSpecialty() {
        return doctor != null ? doctor.getSpecialty() : "Unknown";
    }

    /*
     * output details of appointment
     */
    @JsonIgnore
    public String getDetails(){
        return "Time Booked: " + timeCreated.format(TIMESTAMP_FORMAT) + '\n' +
                "Doctor: " + (doctor != null ? doctor.getName() : "N/A") +
                "(" + (doctor != null ? doctor.getSpecialty() : "N/A") + ")\n" +
                "Appointment: " + appointmentDate.format(DATE_FORMAT) + " at " + appointmentTime.format(TIME_FORMAT) + '\n' +
                "Concern: " + concerns + '\n';

    }
}