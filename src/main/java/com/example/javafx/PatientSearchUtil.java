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
        LocalDate DOB = null;
        String phoneNumber = "";

        for (String part : parts) {
            if (part.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                DOB = LocalDate.parse(part, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            } else if (part.matches("\\d+")) {
                phoneNumber = part;
            } else {
                if (lastName.isEmpty()) lastName = part;
                else if (firstName.isEmpty()) firstName = part;
            }
        }

        return db.searchPatient(firstName, lastName, DOB, phoneNumber,"","","","","","");
    }
}
