package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    private void goToPatient(ActionEvent event) throws IOException {
        // Get the current stage from the button click
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Call your SceneManager method
        SceneManager.switchScene(stage, "/com/example/javafx/addPatient.fxml");
    }

    @FXML
    private void goToPrescriber(ActionEvent event) throws IOException {
        // Get the current stage from the button click
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Call your SceneManager method
        SceneManager.switchScene(stage, "/com/example/javafx/addPrescriber.fxml");
    }



}
