package com.example.javafx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.DatabaseManager;
import models.Patient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ModifyPatientController {

    DatabaseManager db = new DatabaseManager();
    @FXML
    private ComboBox<String> stateComboBox;
    @FXML
    private ComboBox<String> genderComboBox;
    private String patientID;






    public void initialize() {
        ObservableList<String> states = FXCollections.observableArrayList(
                "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
                "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
                "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
                "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
                "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"
        );

        ObservableList<String> genders = FXCollections.observableArrayList(
                "Male","Female","Unknown"
        );

        stateComboBox.setItems(states);
        genderComboBox.setItems(genders);

    }

    @FXML private Button updateBtn;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField dobField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField street1Field;
    @FXML private TextField street2Field;
    @FXML private TextField cityField;
    @FXML private TextField zipField;
    @FXML private TextField countryField;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");


    @FXML
    private void handleUpdatePatient(ActionEvent event) {
        try {

            String dateText = dobField.getText();
            // 1. Create the 'package' of data
            // We use the setters to fill the object with current UI values
            Patient p = new Patient();
            p.setFirstName(firstNameField.getText());
            p.setLastName(lastNameField.getText());
            p.setDOB(LocalDate.parse(dateText, dateTimeFormatter));
            p.setGender(genderComboBox.getValue());
            p.setPhoneNumber(phoneNumberField.getText());
            p.setStreet1(street1Field.getText());
            p.setStreet2(street2Field.getText());
            p.setCity(cityField.getText());
            p.setState(stateComboBox.getValue()); // Your dropdown selection
            p.setZIP(zipField.getText());
            p.setCountry(countryField.getText());

            // 2. Send it to the DatabaseManager
            // 'this.patientID' is the String ID you stored when the scene opened
            db.updatePatient(this.patientID, p);

            showAlert("Updated patient successfully!");
        } catch (Exception e) {
            // If anything fails (like a null value), show the error
            showAlert(e.getMessage());
        }
    }
    public void setPatient(Patient patient) {
        this.patientID = patient.getPatientID();  // ← identity locked in
        populateFields(patient);                  // ← UI filled
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void populateFields(Patient patient) {

        firstNameField.setText(patient.getFirstName());
        lastNameField.setText(patient.getLastName());

        if (patient.getDOB() != null) {
            dobField.setText(patient.getDOB().format(dateTimeFormatter));
        } else {
            dobField.clear();
        }

        genderComboBox.setValue(patient.getGender());
        phoneNumberField.setText(patient.getPhoneNumber());

        street1Field.setText(patient.getStreet1());
        street2Field.setText(patient.getStreet2());
        cityField.setText(patient.getCity());

        stateComboBox.setValue(patient.getState());
        zipField.setText(patient.getZIP());
        countryField.setText(patient.getCountry());
        System.out.println("Items in ComboBox: " + genderComboBox.getItems());
        System.out.println("Value from Patient: [" + patient.getGender() + "]");
    }


}
