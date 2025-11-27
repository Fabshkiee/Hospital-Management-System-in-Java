package hospitalsystem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Person.java acts an abstract class
 * as a base for other child classes
 */

public abstract class Person { //abstract bcz we don't need this as an object but rather a template for other class

    //simple info a person needs also private for encapsulation
    private String name, dateOfBirth, sex, contactNumber;

    //constructor
    public Person(String name, String dateOfBirth, String sex, String contactNumber){
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.contactNumber = contactNumber;
    }

    //getter
    public String getName(){return name;}

    @JsonProperty("dateOfBirth") // Renames "dob" to "dateOfBirth" in JSON
    public String getDOB(){return dateOfBirth;}

    public String getSex(){return sex;}
    public String getContactNumber(){return contactNumber;}

    //setter
    public void setName(String name){this.name = name;}
    public void setDOB(String dateOfBirth){this.dateOfBirth = dateOfBirth;}
    public void setSex(String sex){this.sex = sex;}
    public void setContactNumber(String contactNumber){this.contactNumber = contactNumber;}

    //abstract method for subclasses to modify
    @JsonIgnore
    public abstract String getDetails();

}