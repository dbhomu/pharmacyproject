package models;

import java.time.LocalDate;

public class Patient {
    private String patientID;
    private String firstName;
    private String lastName;
    private LocalDate DOB;
    private String gender;
    private String phoneNumber;
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String ZIP;
    private String country;
    private String allergies;

    public Patient(String firstName, String lastName, LocalDate DOB) {
        setFirstName(firstName);
        setLastName(lastName);
        setDOB(DOB);
    }
    public Patient(String firstName, String lastName, LocalDate DOB, String gender, String phoneNumber, String street1, String street2, String city, String state, String ZIP, String country, String allergies) {

        setFirstName(firstName);
        setLastName(lastName);
        setDOB(DOB);
        setGender(gender);
        setPhoneNumber(phoneNumber);
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        setZIP(ZIP);
        this.country = country;
        this.allergies = allergies;



    }

    public Patient(
            String patientID,
            String firstName,
            String lastName,
            LocalDate dob,
            String gender,
            String phoneNumber,
            String street1,
            String street2,
            String city,
            String state,
            String zip,
            String country,
            String allergies
    ) {
        this(firstName, lastName, dob, gender, phoneNumber, street1, street2, city, state, zip, country, allergies);
        this.patientID = patientID;
    }

    // Constructor for search (no gender, no allergies)
    public Patient(String patientID, String firstName, String lastName, LocalDate dobValue,
                   String phoneNumber, String street1, String street2,
                   String city, String state, String zip, String country) {
        this(patientID, firstName, lastName, dobValue,
                null, // gender
                phoneNumber, street1, street2, city, state, zip, country,
                null); // allergies
    }




    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if(firstName.isEmpty()) {
            throw new RuntimeException("First Name Required");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(lastName.isEmpty()) {
            throw new RuntimeException("Last Name Required");
        }
        this.lastName = lastName;
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public void setDOB(LocalDate DOB) {
        if (DOB == null) {
            throw new RuntimeException("Date of birth Required!");
        }

        this.DOB = DOB;
    }



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;


    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber.length() != 10) {
            throw new RuntimeException("Invalid Phone Number length!");
        }
        this.phoneNumber = phoneNumber;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {

        this.street1 = street1;
    }


    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {

        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZIP() {
        return ZIP;
    }

    public void setZIP(String ZIP) {
        if (!(ZIP.length() == 5 || ZIP.length() == 9)) {
            throw new RuntimeException("Invalid ZIP!");
        }
        this.ZIP = ZIP;

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }
    public Patient () {

    }

}

