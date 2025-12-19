package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.DatabaseManager;
import models.Patient;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static models.DatabaseManager.searchPatient;

public class MenuController implements Initializable {

    @FXML
    private Label username; // linked to fx:id in FXML

    @FXML
    private Circle profileCircle; // linked in FXML

    @FXML private TextField lastNameField;
    @FXML private TextField firstNameField;
    @FXML private TextField dobField;
    @FXML private TextField phoneNumberField;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Use 'file:' and double backslashes OR single forward slashes
        Image img = new Image("file:C:/Users/idrta/Downloads/usericon.jpg");

        // Fill the circle with the image
        profileCircle.setFill(new ImagePattern(img));

        // Make sure the circle is visible
        profileCircle.setVisible(true);

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

        resultsTable.setRowFactory(tv -> {
            TableRow<Patient> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Patient patient = row.getItem();
                    System.out.println("Clicked patient: " + patient.getPatientID());
                }
            });

            return row;
        });


    }




    public void setUsernameLabel(String user) {
        username.setText(user);
    }

    @FXML
    private TextField searchBar;

    @FXML
    private void search() {
        // Parse input
        String[] parts = searchBar.getText().split(",");
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafx/refine parameters.fxml"));
            Parent root = loader.load(); // load FXML first
            PatientController controller = loader.getController();

            // populate fields
            controller.setPatientSearchData(firstName, lastName, dob, phone);
            controller.searchPatientButton(null);
            Stage stage = new Stage();
            stage.setTitle("Refine Patient Search");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private void initializePatient(ActionEvent actionEvent) {
        resultsTable.setVisible(!resultsTable.isVisible());
        searchBar.setVisible(!searchBar.isVisible());
    }



}
