package com.example.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.DatabaseManager;
import models.Patient;

import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class PatientController {

    private final DatabaseManager db = new DatabaseManager();

    @FXML TextField firstNameField;
    @FXML TextField lastNameField;
    @FXML TextField dobField;
    @FXML TextField phoneNumberField;
    @FXML private TextField street1Field;
    @FXML private TextField street2Field;
    @FXML private TextField cityField;
    @FXML private TextField stateField;
    @FXML private TextField zipField;
    @FXML private TextField countryField;
    @FXML private TextField allergiesField;




    @FXML
    public void initialize() {

        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("firstName")
        );

        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lastName")
        );

        dobCol.setCellValueFactory(
                new PropertyValueFactory<>("DOB") // lowercase property
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


        //RETURNS PATIENT ID UPON CLICK
        resultsTable.setRowFactory(tv-> {
            TableRow<Patient> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Patient patient = row.getItem();
                    System.out.println("Clicked patient: " + patient.getPatientID());
                }
            });

            return row;
        });

        refinePane.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                searchPatientBtn.fire(); // automatically triggers the search
                event.consume(); // prevent ENTER from doing other things (like inserting a newline)
            }
        });


    }

    @FXML private BorderPane refinePane;
    @FXML private Button addPatientBtn;
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
                    null,
                    phoneNumberField.getText(),
                    street1Field.getText(),
                    street2Field.getText(),
                    cityField.getText(),
                    stateField.getText(),
                    zipField.getText(),
                    countryField.getText()
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
        phoneNumberField.clear();
        street1Field.clear();
        street2Field.clear();
        cityField.clear();
        stateField.clear();
        zipField.clear();
        countryField.clear();
    }

    @FXML
    void searchPatientButton(ActionEvent event) {

        LocalDate dob = null;

        if (!dobField.getText().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            try {
                dob = LocalDate.parse(dobField.getText(), formatter);
            } catch (Exception e) {
                showAlert("Invalid date format! Use MM/DD/YYYY");
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
        else {
            resultsTable.getItems().setAll(results);
        }







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
    @FXML private Button searchPatientBtn;

    @FXML
    private void openAddPatient() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/javafx/addPatientPopup.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Patient");
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPatientSearchData(String firstName, String lastName, LocalDate dob, String phone) {
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        dobField.setText(dob != null ? dob.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) : "");
        phoneNumberField.setText(phone);
    }




}
