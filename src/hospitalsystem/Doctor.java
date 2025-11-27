package hospitalsystem;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Doctor.java
 * Inherits from Person class to represent a Doctor 
 */
public class Doctor extends Person {
    private String specialty; //for specialty of the doctor e.g. Physical Therapist

    //constructor
    @JsonCreator
    public Doctor(@JsonProperty("name") String name,
                  @JsonProperty("dateOfBirth") String dateOfBirth,
                  @JsonProperty("sex") String sex,
                  @JsonProperty("contactNumber") String contactNumber,
                  @JsonProperty("specialty") String specialty) {
        super(name, dateOfBirth, sex, contactNumber);
        this.specialty = specialty;
    }

    //setter and getter
    public String getSpecialty(){return specialty;}
    public void setSpecialty(String specialty){this.specialty = specialty;}

    //override getDetails to specific doctor
    @Override
    public String getDetails(){
        return "Dr. " + getName() + ", Specialty: " + specialty;
    }

}
