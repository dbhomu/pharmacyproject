package com.example.javafx;

import models.DatabaseManager;
import models.Patient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PatientSearchUtil {

    private static final DatabaseManager db = new DatabaseManager();

    public static ArrayList<Patient> search(String searchText) {
        String[] parts = searchText.split(",");
        for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();

        String firstName = "";
        String lastName = "";
        String DOB = ""; // Changed from null to empty String
        String phoneNumber = "";

        for (String part : parts) {
            // This regex looks for MM/dd/yyyy
            if (part.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                // No need to parse it into a LocalDate object here!
                // Just save the String as it is.
                DOB = part;
            } else if (part.matches("\\d+")) {
                phoneNumber = part;
            } else {
                // If it's not a date or phone number, assume it's a name
                if (lastName.isEmpty()) lastName = part;
                else if (firstName.isEmpty()) firstName = part;
            }
        }

        // Now passing the String DOB into your updated db method
        return db.searchPatient(firstName, lastName, DOB, phoneNumber, "", "", "", "", "", "");
    }
}
