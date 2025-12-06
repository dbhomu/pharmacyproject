package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.DatabaseManager;
import models.Prescriber;
import models.Prescription;

import java.io.IOException;
import java.util.ArrayList;

public class PrescriberController {

    private final DatabaseManager db = new DatabaseManager();

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField npiField;
    @FXML private TextField deaField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField faxNumberField;
    @FXML private TextField street1Field;
    @FXML private TextField street2Field;
    @FXML private TextField cityField;
    @FXML private TextField stateField;
    @FXML private TextField zipField;
    @FXML private TextField countryField;

    @FXML

    private void goToMenu(ActionEvent event) throws IOException {
        // Get the current stage from the button click
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Call your SceneManager method
        SceneManager.switchScene(stage, "/com/example/javafx/menu.fxml");
    }
        @FXML
    private void addPrescriberButton(ActionEvent event) {
        try {
            Prescriber prescriber = new Prescriber(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    npiField.getText(),
                    deaField.getText(),
                    phoneNumberField.getText(),
                    faxNumberField.getText(),
                    street1Field.getText(),
                    street2Field.getText(),
                    cityField.getText(),
                    stateField.getText(),
                    zipField.getText(),
                    countryField.getText()
            );

            db.addPrescriber(prescriber);
            showAlert("Prescriber added successfully!");
            clearFields();
        } catch (RuntimeException e) {
            showAlert(e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        npiField.clear();
        deaField.clear();
        phoneNumberField.clear();
        faxNumberField.clear();
        street1Field.clear();
        street2Field.clear();
        cityField.clear();
        stateField.clear();
        zipField.clear();
        countryField.clear();
    }

    @FXML
    private void searchPrescriberButton(ActionEvent actionEvent) {
        ArrayList<Prescriber> results = db.searchPrescriber(
                firstNameField.getText(),
                lastNameField.getText(),
                npiField.getText(),
                deaField.getText(),
                phoneNumberField.getText(),
                faxNumberField.getText(),
                street1Field.getText(),
                street2Field.getText(),
                cityField.getText(),
                stateField.getText(),
                zipField.getText(),
                countryField.getText()




        );
        if(results.isEmpty()) {
            showAlert("No Prescriber found");
            return;
        }

        Prescriber prescriber = results.get(0);

        firstNameField.setText(prescriber.getFirstName());
        lastNameField.setText(prescriber.getLastName());
        npiField.setText(prescriber.getNPI());
        deaField.setText(prescriber.getDEA());
        phoneNumberField.setText(prescriber.getPhoneNumber());
        faxNumberField.setText(prescriber.getFaxNumber());
        street1Field.setText(prescriber.getStreet1());
        street2Field.setText(prescriber.getStreet2());
        cityField.setText(prescriber.getCity());
        stateField.setText(prescriber.getState());
        zipField.setText(prescriber.getZIP());
        countryField.setText(prescriber.getCountry());


    }


}
