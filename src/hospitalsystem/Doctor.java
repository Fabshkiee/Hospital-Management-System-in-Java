package hospitalsystem;

/**
 * Doctor.java
 * Inherits from Person class to represent a Doctor 
 */
public class Doctor extends Person {
    private String specialty; //for specialty of the doctor e.g. Physical Therapist

    //constructor
    public Doctor(String name, String dateOfBirth, String sex, String contactNumber, String specialty){
        super(name, dateOfBirth, sex, contactNumber); //call parent constructor
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
