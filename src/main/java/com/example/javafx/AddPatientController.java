package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import models.DatabaseManager;
import models.Patient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

    public class AddPatientController {

        @FXML
        private TextField firstNameField;
        @FXML
        private TextField lastNameField;
        @FXML
        private TextField dobField;
        @FXML
        private TextField phoneNumberField;
        @FXML
        private TextField street1Field;
        @FXML
        private TextField street2Field;
        @FXML
        private TextField cityField;
        @FXML
        private TextField stateField;
        @FXML
        private TextField zipField;
        @FXML
        private TextField countryField;
        @FXML
        private TextField allergiesField;

        @FXML
        private TableView<Patient> resultsTable;

        @FXML
        private TableColumn<Patient, String> firstNameCol;
        @FXML
        private TableColumn<Patient, String> lastNameCol;
        @FXML
        private TableColumn<Patient, String> dobCol;
        @FXML
        private TableColumn<Patient, String> phoneNumberCol;
        @FXML
        private TableColumn<Patient, String> street1Col;
        @FXML
        private TableColumn<Patient, String> street2Col;
        @FXML
        private TableColumn<Patient, String> cityCol;
        @FXML
        private TableColumn<Patient, String> stateCol;
        @FXML
        private TableColumn<Patient, String> zipCol;
        @FXML
        private TableColumn<Patient, String> countryCol;
        @FXML
        private BorderPane refinePane;
        @FXML
        private Button searchPatientBtn;

        DatabaseManager db = new DatabaseManager();


        @FXML
        private void addPatientButton(ActionEvent event) {
            if (dobField.getText().isEmpty()) {
                showAlert("Date of birth is required!");
                return;
            }

            // Still validate the date here! This ensures the String is a REAL date
            // before we save it as a String.
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            try {
                LocalDate.parse(dobField.getText(), formatter);
            } catch (DateTimeParseException e) {
                showAlert("Invalid date format! Use MM/DD/YYYY");
                return;
            }

            try {
                // This call has 11 arguments. We need a matching constructor in Patient.java
                Patient patient = new Patient(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        dobField.getText(),    // String DOB
                        null,                  // gender
                        phoneNumberField.getText(),
                        street1Field.getText(),
                        street2Field.getText(),
                        cityField.getText(),
                        stateField.getText(),
                        zipField.getText(),
                        countryField.getText()
                );

                db.addPatient(patient);
                showAlert("Patient added successfully!");
                clearFields();
            } catch (RuntimeException e) {
                showAlert(e.getMessage());
            }
        }
        // We need to make this showAlert method in legit every class because the showAlert thing won't work if it's not here LOL
        private void showAlert(String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(message);
            alert.showAndWait();
        }

        private void clearFields() {
            firstNameField.clear();
            lastNameField.clear();
            dobField.clear();
            phoneNumberField.clear();
            street1Field.clear();
            street2Field.clear();
            cityField.clear();
            stateField.clear();
            zipField.clear();
            countryField.clear();
        }

    }
