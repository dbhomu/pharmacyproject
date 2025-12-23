package com.example.javafx;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    // ----------- UI Elements -----------
    @FXML
    private Label username;

    @FXML
    private Circle profileCircle;

    @FXML
    private TextField searchBar;


    @FXML private TableColumn<Prescription, String> rxNumberCol;
    @FXML private TableColumn<Prescription, LocalDate> fillDateCol;
    @FXML private TableColumn<Prescription, String> dispensedProductCol;
    @FXML private TableColumn<Prescription, String> ndcCol;
    @FXML private TableColumn<Prescription, Integer> qtyCol;
    @FXML private TableColumn<Prescription, String> lastNameCol;
    @FXML private TableColumn<Prescription, String> firstNameCol;
    @FXML private TableColumn<Prescription, String> refillsCol;
    @FXML private TableColumn<Prescription, String> autoFillCol;
    @FXML private TableColumn<Prescription, String> sigCol;
    @FXML private TableColumn<Prescription, String> statusCol;
    private Patient currentPatient;
    // ----------- Initialization -----------
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupProfileImage();
        setupPrescriptionTable();



    }

    // ----------- Methods -----------

    private void setupProfileImage() {
        // Load local image
        Image img = new Image("file:C:/Users/idrta/Downloads/usericon.jpg");
        profileCircle.setFill(new ImagePattern(img));
        profileCircle.setVisible(true);
    }

    private void setupPrescriptionTable() {
        // 1. Direct fields (names match exactly)
        rxNumberCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRxNumber()));
        qtyCol.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getPrescribedQuantity())
        );
        sigCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSIG()));
        fillDateCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFillDate()));
        // 2. The nested Drug fields (Fixes the "Dispensed Product" and "NDC" columns)
        dispensedProductCol.setCellValueFactory(cellData -> {
            Drug d = cellData.getValue().getDrug();
            return new SimpleStringProperty(d != null ? d.getDrugName() : "");
        });
        ndcCol.setCellValueFactory(cellData -> {
            Drug d = cellData.getValue().getDrug();
            return new SimpleStringProperty(d != null ? d.getDrugNDC() : "");
        });

        // 3. The nested Prescriber fields (Fixes the "firstName" and "lastName" error)
        firstNameCol.setCellValueFactory(cellData -> {
            Prescriber p = cellData.getValue().getPrescriber();
            return new SimpleStringProperty(p != null ? p.getFirstName() : "");
        });
        lastNameCol.setCellValueFactory(cellData -> {
            Prescriber p = cellData.getValue().getPrescriber();
            return new SimpleStringProperty(p != null ? p.getLastName() : "");
        });

        // 4. Mismatched names (Fixes the "Status" column)
        statusCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isActive() ? "Active" : "Completed"));

        // 5. Number fields
        refillsCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getRefills())));

        prescriptionsTable.getItems().clear();
    }

    public void setUsernameLabel(String user) {
        username.setText(user);
    }

    @FXML
    private TableView<Prescription> prescriptionsTable; // optional, if you want to show patients

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleSearch(ActionEvent event) throws IOException {
        String searchText = searchBar.getText().trim();
        if (searchText.isEmpty()) return;

        ArrayList<Patient> results = PatientSearchUtil.search(searchText);

        if (results.isEmpty()) {
            showAlert("No patient found.");
            return;
        }

        // Load RefineParameters popup
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafx/refine parameters.fxml"));
        Parent root = loader.load();
        RefineParametersController controller = loader.getController();

        // Set the search input fields & results in the popup
        controller.setSearchFields(searchText);
        controller.populateResults(results);


        // Listen for patient selection
        controller.setPatientSelectedListener(selectedPatient -> {
            // Pull prescriptions for the patient and populate table
            ArrayList<Prescription> prescriptions = DatabaseManager.getPrescriptionsForPatient(selectedPatient.getPatientID());
            prescriptionsTable.getItems().setAll(prescriptions);
            currentPatient = selectedPatient;
            prescriptionsTable.getItems().setAll(prescriptions);
        });

        // Now create and show the stage OUTSIDE the listener
        Stage stage = new Stage();
        stage.setTitle("Refine Patient Search");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
        searchBar.setText(currentPatient.getFirstName() + " " + currentPatient.getLastName());
    }



    @FXML private Button modifyPatientBtn;

    @FXML
    private void openModifyPatientPopup(ActionEvent event) throws IOException {
        // 1. Load the FXML

        if(currentPatient == null) {
            showAlert("Please select a patient first.");
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafx/modify patient.fxml"));
        Parent root = loader.load();
        ModifyPatientController controller = loader.getController();

        //GETTING THE PATIENT ID
        controller.setPatient(currentPatient);
        // 2. Create a NEW Stage (Window)
        Stage popupStage = new Stage();
        popupStage.setTitle("Modify Patient Details");

        // 3. Set the scene on the NEW stage
        popupStage.setScene(new Scene(root));

        // 4. Set Modality (Crucial for Popups)
        // This blocks the user from clicking the main window until the popup is closed
        popupStage.initModality(Modality.APPLICATION_MODAL);

        // 5. Show it
        popupStage.showAndWait();
    }








}


