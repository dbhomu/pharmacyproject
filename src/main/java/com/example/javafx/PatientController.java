package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.DatabaseManager;
import models.Patient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class PatientController {

    private final DatabaseManager db = new DatabaseManager();

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField dobField;
    @FXML private TextField genderField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField street1Field;
    @FXML private TextField street2Field;
    @FXML private TextField cityField;
    @FXML private TextField stateField;
    @FXML private TextField zipField;
    @FXML private TextField countryField;
    @FXML private TextField allergiesField;


    @FXML
    private void addPatientButton(ActionEvent event) {
        // Validate DOB
        if (dobField.getText().isEmpty()) {
            showAlert("Date of birth is required!");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate dob;

        try {
            dob = LocalDate.parse(dobField.getText(), formatter);
        } catch (DateTimeParseException e) {
            showAlert("Invalid date format! Use MM/DD/YYYY");
            return;
        }

        try {

            Patient patient = new Patient(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    dob,
                    genderField.getText(),
                    phoneNumberField.getText(),
                    street1Field.getText(),
                    street2Field.getText(),
                    cityField.getText(),
                    stateField.getText(),
                    zipField.getText(),
                    countryField.getText(),
                    allergiesField.getText()
            );

            db.addPatient(patient);
        } catch (RuntimeException e) {
            showAlert(e.getMessage());
        }
        showAlert("Patient added successfully!");
        clearFields();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        dobField.clear();
        genderField.clear();
        phoneNumberField.clear();
        street1Field.clear();
        street2Field.clear();
        cityField.clear();
        stateField.clear();
        zipField.clear();
        countryField.clear();
        allergiesField.clear();
    }

    @FXML
    private void searchPatientButton(ActionEvent event) {

        LocalDate dob = null;

        if (!dobField.getText().isEmpty()) {
            try {
                dob = LocalDate.parse(dobField.getText());
            } catch (Exception e) {
                showAlert("Invalid date format.");
                return;
            }
        }

        ArrayList<Patient> results = db.searchPatient(
                firstNameField.getText(),
                lastNameField.getText(),
                dob,
                phoneNumberField.getText(),
                street1Field.getText(),
                street2Field.getText(),
                cityField.getText(),
                stateField.getText(),
                zipField.getText(),
                countryField.getText()
        );

        if (results.isEmpty()) {
            showAlert("No patient found.");
            return;
        }

        Patient p = results.get(0); // assume 1 match

        firstNameField.setText(p.getFirstName());
        lastNameField.setText(p.getLastName());
        dobField.setText(p.getDOB().toString());
        genderField.setText(p.getGender());
        phoneNumberField.setText(p.getPhoneNumber());
        street1Field.setText(p.getStreet1());
        street2Field.setText(p.getStreet2());
        cityField.setText(p.getCity());
        stateField.setText(p.getState());
        zipField.setText(p.getZIP());
        countryField.setText(p.getCountry());
        allergiesField.setText(p.getAllergies());
    }
    @FXML
    private Button cancelBtn;

    @FXML
    private void handleCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
    @FXML
    private TableView<Patient> resultsTable;
    @FXML private TableColumn<Patient, String> firstNameCol;
    @FXML private TableColumn<Patient, String> lastNameCol;
    @FXML private TableColumn<Patient, LocalDate> dobCol;
    @FXML private TableColumn<Patient, String> phoneNumberCol;
    @FXML private TableColumn<Patient, String> street1Col;
    @FXML private TableColumn<Patient, String> street2Col;
    @FXML private TableColumn<Patient, String> cityCol;
    @FXML private TableColumn<Patient, String> stateCol;
    @FXML private TableColumn<Patient, String> zipCol;
    @FXML private TableColumn<Patient, String> countryCol;
    @FXML
    private void initialize() {

        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("firstName")
        );

        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lastName")
        );

        dobCol.setCellValueFactory(
                new PropertyValueFactory<>("dob") // lowercase property
        );

        phoneNumberCol.setCellValueFactory(
                new PropertyValueFactory<>("phoneNumber")
        );

        street1Col.setCellValueFactory(
                new PropertyValueFactory<>("street1")
        );

        street2Col.setCellValueFactory(
                new PropertyValueFactory<>("street2")
        );

        cityCol.setCellValueFactory(
                new PropertyValueFactory<>("city")
        );

        stateCol.setCellValueFactory(
                new PropertyValueFactory<>("state")
        );

        zipCol.setCellValueFactory(
                new PropertyValueFactory<>("zip") // lowercase property
        );

        countryCol.setCellValueFactory(
                new PropertyValueFactory<>("country")
        );


        resultsTable.getItems().clear();
    }


}
