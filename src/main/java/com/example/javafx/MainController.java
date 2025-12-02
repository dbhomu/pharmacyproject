package com.example.javafx;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    private void printHello(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // info popup
        alert.setTitle("Hello");
        alert.setHeaderText(null); // no header
        alert.setContentText("Hello World!"); // main message
        alert.showAndWait(); // shows popup and waits for user to close
    }
}
