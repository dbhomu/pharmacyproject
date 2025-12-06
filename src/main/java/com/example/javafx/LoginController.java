package com.example.javafx;

import com.example.javafx.SceneManager;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.DatabaseManager;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginBtn;

    @FXML
    private Text titleText;

    private Timeline cursorBlinkTimeline;

    public void typeText(Text textNode, String text, int speed) {
        Timeline timeline = new Timeline();
        textNode.setText("");

        for (int i = 0; i < text.length(); i++) {
            final int index = i;

            KeyFrame frame = new KeyFrame(
                    Duration.millis(speed * i),
                    e -> textNode.setText(text.substring(0, index + 1) + "_")
            );

            timeline.getKeyFrames().add(frame);
        }

        timeline.setOnFinished(e -> startCursorBlink(textNode));

        timeline.play();
    }

    private void startCursorBlink(Text textNode) {
        cursorBlinkTimeline = new Timeline(
                new KeyFrame(Duration.millis(0), e -> textNode.setText(textNode.getText().replace("_", ""))),
                new KeyFrame(Duration.millis(500), e -> textNode.setText(textNode.getText() + "_"))
        );

        cursorBlinkTimeline.setCycleCount(Timeline.INDEFINITE);
        cursorBlinkTimeline.play();
    }

    @FXML
    public void initialize() {
        typeText(titleText, "Pharmacy\nProject", 40);
    }



    @FXML
    private void loginBtn(ActionEvent event) throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty()) {
            System.out.println("Username is required");
            return; // stops login
        }

        if (password.isEmpty()) {
            System.out.println("Password is required");
            return; // stops login
        }

        if (DatabaseManager.validateLogin(username, password)) {
            System.out.println("Login successful!");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            SceneManager.switchScene(stage, "/com/example/javafx/menu.fxml");
        } else {
            System.out.println("Invalid credentials");
        }
    }




    @FXML
    private void handleEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            loginBtn.fire(); // simulates clicking the login button
        }

    }

}
