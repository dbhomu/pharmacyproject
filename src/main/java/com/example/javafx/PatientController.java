package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.DatabaseManager;
import models.Patient;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
    private void goToMenu(ActionEvent event) throws IOException {
        // Get the current stage from the button click
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Call your SceneManager method
        SceneManager.switchScene(stage, "/com/example/javafx/menu.fxml");
    }
    @FXML
    private void addPatientButton(ActionEvent event) {
        // Validate DOB
        if (dobField.getText().isEmpty()) {
            showAlert("Date of birth is required!");
            return;
        }

        LocalDate dob;
        try {
            dob = LocalDate.parse(dobField.getText());
        } catch (DateTimeParseException e) {
            showAlert("Invalid date format! Use yyyy-MM-dd.");
            return;
        }

        // Create patient and save
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


}
