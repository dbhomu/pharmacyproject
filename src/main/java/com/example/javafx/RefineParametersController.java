package com.example.javafx;

import com.dlsc.formsfx.model.structure.Field;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.DatabaseManager;
import models.Patient;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class RefineParametersController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private  TextField dobField;
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
    private void initialize() {
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("firstName")
        );

        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("lastName")
        );

        dobCol.setCellValueFactory(
                new PropertyValueFactory<>("DOB")
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
                new PropertyValueFactory<>("ZIP")
        );

        countryCol.setCellValueFactory(
                new PropertyValueFactory<>("country")
        );


        resultsTable.getItems().clear();


        refinePane.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                searchPatientBtn.fire(); // automatically triggers the search
                event.consume(); // prevent ENTER from doing other things (like inserting a newline)
            }
        });

        resultsTable.setRowFactory(tv -> {
            TableRow<Patient> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    Patient selectedPatient = row.getItem();
                    // Callback to MenuController
                    if (patientSelectedListener != null) {
                        patientSelectedListener.onPatientSelected(selectedPatient);
                    }
                    // Close popup
                    Stage stage = (Stage) resultsTable.getScene().getWindow();
                    stage.close();
                }
            });
            return row;
        });

    }
    public interface PatientSelectedListener {
        void onPatientSelected(Patient patient);
    }

    private PatientSelectedListener patientSelectedListener;

    public void setPatientSelectedListener(PatientSelectedListener listener) {
        this.patientSelectedListener = listener;
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


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
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
                dobField.getText(),
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
        } else {
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

    public void setSearchFields(String searchText) {
        // Split the search string by commas
        String[] parts = searchText.split(",");
        for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();

        String firstName = "";
        String lastName = "";
        LocalDate dob = null;
        String phone = "";

        for (String part : parts) {
            if (part.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                dob = LocalDate.parse(part, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            } else if (part.matches("\\d+")) {
                phone = part;
            } else {
                if (lastName.isEmpty()) lastName = part;
                else if (firstName.isEmpty()) firstName = part;
            }
        }

        // Fill your FXML text fields
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        dobField.setText(dob != null ? dob.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) : "");
        phoneNumberField.setText(phone);
    }

    public void populateResults(ArrayList<Patient> results) {
        // Fill your table with the patients returned from the DB
        resultsTable.getItems().setAll(results);
    }




}

