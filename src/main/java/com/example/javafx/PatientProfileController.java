package com.example.javafx;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.DatabaseManager;
import models.Patient;
import models.Prescription;

import java.time.LocalDate;
import java.util.ArrayList;

public class PatientProfileController {
    @FXML
    private TableView<Prescription> prescriptionTable;

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

    private DatabaseManager db = new DatabaseManager();

    public void loadPrescriptionsInto(Patient patient) {
        // Set up columns (once ideally in initialize())
        rxNumberCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRxNumber()));
        fillDateCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFillDate()));
        dispensedProductCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDrug().getDrugName()));
        ndcCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDrug().getDrugNDC()));
        qtyCol.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getPrescribedQuantity()));
        lastNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrescriber().getLastName()));
        firstNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrescriber().getFirstName()));
        refillsCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRefills())));
        autoFillCol.setCellValueFactory(cellData -> new SimpleStringProperty("Yes/No")); // placeholder
        sigCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSIG()));
        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isActive() ? "Active" : "Completed"));

        // Load prescriptions from database
        ArrayList<Prescription> prescriptions = db.getPrescriptionsForPatient(patient.getPatientID());
        prescriptionTable.setItems(FXCollections.observableArrayList(prescriptions));
    }


}