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
        this.firstName = firstName;
        this.lastName = lastName;
        setNPI(NPI);
        setDEA(DEA);
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.ZIP = ZIP;
        this.country = country;
    }

    public static boolean isValidNPI(String npi) {
        if (npi == null || npi.length() != 10) return false;
        String firstNine = npi.substring(0, 9);
        String fullNPI = "80840" + firstNine;
        int checkDigit = Character.getNumericValue(npi.charAt(9));
        int sum = 0;
        for (int i = 0; i < fullNPI.length(); i++) {
            char nums = fullNPI.charAt(i);
            int digit = Character.getNumericValue(nums);
            if (i % 2 != 0) {
                digit *= 2;
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }
            sum += digit;


        }
        sum += checkDigit;
        return sum % 10 == 0;
    }   

    public static boolean isValidDEA(String DEA) {
        if(DEA == null || DEA.isBlank()) return true;

        if(DEA.length() != 9) {
            return false;
        }

        String deaNumbers = DEA.substring(2, 8); // digits 3–8 (7 digits)
        int evenSum = 0;
        int oddSum = 0;

        for (int i = 0; i < deaNumbers.length(); i++) {
            int digit = Character.getNumericValue(deaNumbers.charAt(i));

            if (i % 2 == 0) {
                evenSum += digit;     // positions 1,3,5 AKA indices 0,2,4
            } else {
                oddSum += digit;      // positions 2,4,6 AKA indices 1,3,5
            }
        }

        oddSum *= 2; // double the ODD indices

        int totalSum = oddSum + evenSum;
        int lastDigit = totalSum % 10;

        int checkDigit = Character.getNumericValue(DEA.charAt(8));

        return lastDigit == checkDigit;

    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
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
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
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
        this.ZIP = ZIP;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
