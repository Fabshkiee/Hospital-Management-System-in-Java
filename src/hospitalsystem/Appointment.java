package hospitalsystem;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; //import for dates and times

/**
 * Appointment.java
 * appointment information.
 * It demonstrates Encapsulation.
 */
public class Appointment {
    private Patient patient; //patient object
    private Doctor doctor; //doctor object
    private LocalDate appointmentDate; //date and time of appointment
    private LocalTime appointmentTime;
    private String concerns;
    private LocalDateTime timeCreated; //timestamp when appointment created

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy"); //date format eg 12/31/2024
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("hh:mm a"); //time format eg 02:30 PM
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yy h:mm:ss a"); //timestamp format eg 12/31/24 2:30:45 PM
    
    public Appointment(Patient patient, Doctor doctor, LocalDate appointmentDate, LocalTime appointmentTime, String concerns) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.concerns = concerns;
        this.timeCreated = LocalDateTime.now(); // Set booking time to now
    }

    // Overloaded constructor for loading from a file
    public Appointment(Patient patient, Doctor doctor, LocalDate appointmentDate, LocalTime appointmentTime, String concerns, LocalDateTime timeCreated) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.concerns = concerns;
        this.timeCreated = timeCreated;
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

    /*
     * output details of appointment
     */
    public String getDetails(){
        return "Time Booked: " + timeCreated.format(TIMESTAMP_FORMAT) + '\n' +
               "Doctor: " + doctor.getName() + "(" + doctor.getSpecialty() + ")\n" +
               "Appointment: " + appointmentDate.format(DATE_FORMAT) + " at " + appointmentTime.format(TIME_FORMAT) + '\n' +
               "Concern: " + concerns + '\n';
        
    }

    /*
     * format appointment for file saving
     */
    public String toFileString(){
        return "APP" + "::" +
                appointmentDate.format(TIMESTAMP_FORMAT) + "::" +
                doctor.getName() + "::" +
                doctor.getSpecialty() + "::" +
                appointmentDate.format(DATE_FORMAT) + "::" +
                appointmentTime.format(TIME_FORMAT) + "::" +
                concerns;
    }

}
