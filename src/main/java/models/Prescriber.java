package models;

public class Prescriber {
    private String firstName;
    private String lastName;
    private String NPI;
    private String DEA;
    private String phoneNumber;
    private String faxNumber;
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String ZIP;
    private String country;

    public Prescriber(String firstName, String lastName, String NPI, String DEA, String phoneNumber, String faxNumber, String street1, String street2, String city, String state, String ZIP, String country) {
        setFirstName(firstName);
        setLastName(lastName);
        setNPI(NPI);
        setDEA(DEA);
        setPhoneNumber(phoneNumber);
        setFaxNumber(faxNumber);
        setStreet1(street1);
        setStreet2(street2);
        setCity(city);
        setState(state);
        setZIP(ZIP);
        setCountry(country);
    }

    public Prescriber() {

    }

    public Prescriber(String firstName, String lastName) {
        this.firstName = firstName;
    }

    // Main combined check
    public static boolean isValidNPI(String npi) {
        //1043171796 is a test case
        int lastDigitOfNPI = Character.getNumericValue(npi.charAt(npi.length() - 1));
        String firstNine = npi.substring(0,9);
        // first nine digits of the NPI is 104317179, 6 is the checksum

        String testNPI = "80840" + firstNine;
        int evenSum = 0;
        int oddSum = 0;

        // 80840104317179 is our testNPI
        for(int i = 0; i < testNPI.length(); i++) {
            // All even indexes need to be multiplied by 1
            // Odd indexes need to be multiplied by 2
            int digit = testNPI.charAt(i) - '0';
            if(i % 2 == 0) {
                evenSum += digit;
            }
            else {
                int doubled = digit * 2;
                if (doubled > 9) {
                    doubled = (doubled / 10) + (doubled % 10);
                }
                oddSum += doubled;
                }
            }

        int sum = evenSum + oddSum;
        int checkDigit = (10 - (sum % 10)) % 10;
        return checkDigit == lastDigitOfNPI;


    }

    public static boolean isValidDEA(String DEA) {
        return true;
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if(!firstName.matches("[A-Za-z]+")) {
            throw new RuntimeException("Invalid first name!");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(!lastName.matches("[A-Za-z]+")) {
            throw new RuntimeException("Invalid last name!");
        }
        this.lastName = lastName;
    }

    public String getNPI() {
        return NPI;
    }

    public void setNPI(String NPI) {
        if(!isValidNPI(NPI)) {
            throw new RuntimeException("Invalid NPI!");

        }
        this.NPI = NPI;
    }

    public String getDEA() {
        return DEA;
    }

    public void setDEA(String DEA) {
        if(!isValidDEA(DEA)) {
            throw new RuntimeException("Invalid DEA!");
        }
        this.DEA = DEA;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber.length() != 10 || phoneNumber.isBlank()) {
            throw new RuntimeException("Invalid Phone Number length!");
        }
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        if(faxNumber.length() != 10 || faxNumber.isBlank()) {
            throw new RuntimeException("Invalid Fax Number Length!");
        }
        this.faxNumber = faxNumber;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        if(street1.isEmpty()) {
            throw new RuntimeException("Address cannot be blank!");
        }
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
        if(city.isEmpty()) {
            throw new RuntimeException("City cannot be blank!");
        }
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
       if(state.isBlank()) {
           throw new RuntimeException("Invalid State!");
       }
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
        if(country.isEmpty()) {
            throw new RuntimeException("Country cannot be blank");
        }
        this.country = country;
    }
}
