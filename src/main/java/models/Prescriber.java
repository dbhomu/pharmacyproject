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

    // Main combined check
    public static boolean isValidNPI(String npi) {
        if (npi == null || npi.length() != 10) return false;

        // 1️⃣ Modern NPI: 80840 + first 9 digits
        if (passes80840Luhn(npi)) return true;

        // 2️⃣ Legacy/alternate: plain 10-digit Luhn
        if (passesPlainLuhn(npi)) return true;

        return false; // fails both
    }

    // Modern 80840 + Luhn check
    private static boolean passes80840Luhn(String npi) {
        String firstNine = npi.substring(0, 9);
        String fullNPI = "80840" + firstNine; // 15 digits
        int sum = 0;

        // Process from RIGHT to LEFT
        for (int i = fullNPI.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(fullNPI.charAt(i));
            int positionFromRight = fullNPI.length() - 1 - i;

            if (positionFromRight % 2 == 0) { // double every second digit starting from right
                digit *= 2;
                if (digit > 9) digit = (digit / 10) + (digit % 10);
            }
            sum += digit;
        }

        int checkDigit = sum % 10;
        return checkDigit == Character.getNumericValue(npi.charAt(9));
    }

    // Legacy/simple 10-digit Luhn
    private static boolean passesPlainLuhn(String npi) {
        int sum = 0;
        for (int i = npi.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(npi.charAt(i));
            int positionFromRight = npi.length() - 1 - i;

            if (positionFromRight % 2 == 1) { // standard Luhn doubling pattern
                digit *= 2;
                if (digit > 9) digit = (digit / 10) + (digit % 10);
            }
            sum += digit;
        }

        return sum % 10 == 0;
    }




    public static boolean isValidDEA(String DEA) {
        if (DEA == null || DEA.isBlank()) return false;

        if (DEA.length() != 9) return false;

        // get digits 3–8 (indices 2–7)
        String digits = DEA.substring(2, 8);

        int sumOdd = 0;   // D1 + D3 + D5
        int sumEven = 0;  // D2 + D4 + D6

        for (int i = 0; i < 6; i++) {
            int digit = Character.getNumericValue(digits.charAt(i));
            if (i % 2 == 0) {
                sumOdd += digit;
            } else {
                sumEven += digit;
            }
        }

        sumEven *= 2;

        int total = sumOdd + sumEven;
        int checkDigit = total % 10;

        int lastDigit = Character.getNumericValue(DEA.charAt(8)); // D7

        return checkDigit == lastDigit;
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if(!firstName.matches("[A-Za-z]+")) {
            throw new RuntimeException("Invalid first name!");
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(!lastName.matches("[A-Za-z]+")) {
            throw new RuntimeException("Invalid last name!");
        }
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
        if(ZIP.length() != 5 || ZIP.length() != 9 || ZIP.isEmpty()) {
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
