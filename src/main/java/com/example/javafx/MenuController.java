package com.example.javafx;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.*;

import java.io.IOException;
import java.io.InputStream;

import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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


    @FXML
    private TableColumn<Prescription, String> rxNumberCol;
    @FXML
    private TableColumn<Prescription, LocalDate> fillDateCol;
    @FXML
    private TableColumn<Prescription, String> dispensedProductCol;
    @FXML
    private TableColumn<Prescription, String> ndcCol;
    @FXML
    private TableColumn<Prescription, Integer> qtyCol;
    @FXML
    private TableColumn<Prescription, String> lastNameCol;
    @FXML
    private TableColumn<Prescription, String> firstNameCol;
    @FXML
    private TableColumn<Prescription, String> refillsCol;
    @FXML
    private TableColumn<Prescription, String> autoFillCol;
    @FXML
    private TableColumn<Prescription, String> sigCol;
    @FXML
    private TableColumn<Prescription, String> statusCol;

    @FXML
    private TableView<Prescription> queueTable;
    @FXML
    private TableColumn<Prescription, String> queueFirstNameCol;
    @FXML
    private TableColumn<Prescription, String> queueLastNameCol;
    @FXML
    private TableColumn<Prescription, String> timeCol;
    @FXML
    private TableColumn<Prescription, String> drugCol;

    private Patient currentPatient;

   private final ObservableList<Prescription> queueList = FXCollections.observableArrayList();
    // ----------- Initialization -----------
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupProfileImage();
        setupPrescriptionTable();

        queueTable.setItems(queueList);
        queueLastNameCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPatient().getLastName())
        );
        queueFirstNameCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPatient().getFirstName())
        );

        drugCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDrug().getDrugName())
        );

        timeCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )
        );

        startHttpServerInBackground();


    }

    // ----------- Methods -----------
    public void startHttpServerInBackground() {
        new Thread(() -> {
            try {
                startHttpServer(); // your existing HTTP server code
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void startHttpServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/prescriptions", this::handlePrescription);
        server.start();
        System.out.println("Server running on http://localhost:8080");
    }

    private void handlePrescription(HttpExchange exchange) throws IOException {
        try {
            // 1. Read the data
            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            // 2. Parse JSON (Make sure your GSON matches the incoming JSON structure)
            Gson gson = new Gson();
            PrescriptionJson json = gson.fromJson(body, PrescriptionJson.class);

            // 3. Convert to your Model objects
            // (Assuming you have a constructor that matches these)
            Drug drug = new Drug(json.drug.drugName, json.drug.drugStrength);

            // Use a default date if your string parsing is failing for now
            LocalDate dob = LocalDate.now();
            try {
                dob = LocalDate.parse(json.patient.DOB, DateTimeFormatter.ofPattern("MMddyyyy"));
            } catch (Exception e) {
                System.err.println("DOB Parse failed, using default.");
            }

            Patient patient = new Patient(json.patient.firstName, json.patient.lastName, dob, json.patient.gender, json.patient.phoneNumber, json.patient.street1, json.patient.street2, json.patient.city, json.patient.state, json.patient.ZIP, json.patient.country);


            Prescription newRx = new Prescription(patient, null, drug, null, json.sig, String.valueOf(json.refills), null, null, null, 0, null, null, json.prescriptionType, json.isElectronic);

            // 4. THE MAGIC FIX: Update the UI on the correct thread
            Platform.runLater(() -> {
                try {
                    // Add to the ObservableList, NOT the table directly
                    queueList.add(newRx);
                    System.out.println("Success! Queue size: " + queueList.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // 5. Send success back to Postman/Client
            String response = "Prescription Received!";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());

        } catch (Exception e) {
            System.err.println("CRITICAL ERROR IN PARSING:");
            e.printStackTrace(); // This will tell you exactly which line is failing
            exchange.sendResponseHeaders(500, 0);
        } finally {
            exchange.close();
        }
    }

    // JSON helper classes (only String fields)
    public static class PrescriptionJson {
        public PatientJson patient;
        public PrescriberJson prescriber;
        public DrugJson drug;
        public String sig;
        public Integer refills;
        public String prescriptionType;
        public boolean isElectronic;
    }

    public static class PatientJson {
        public String firstName;
        public String lastName;
        public String DOB; // string from JSON
        public String gender;
        public String phoneNumber;
        public String street1;
        public String street2;
        public String city;
        public String state;
        public String ZIP;
        public String country;
        public String allergies;
    }

    public static class PrescriberJson {
        public String firstName;
        public String lastName;
        public String NPI;
        public String DEA;
        public String phoneNumber;
        public String faxNumber;
        public String street1;
        public String street2;
        public String city;
        public String state;
        public String ZIP;
        public String country;
    }

    public static class DrugJson {
        public String drugName;
        public String drugStrength;
    }





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


    @FXML
    private Button modifyPatientBtn;

    @FXML
    private void openModifyPatientPopup(ActionEvent event) throws IOException {
        // 1. Load the FXML

        if (currentPatient == null) {
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








