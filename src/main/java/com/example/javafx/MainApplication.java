package com.example.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Getting the fxml file we want to load
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/javafx/login.fxml"));
        // Setting the size of our fxml file GUI to 800 x 600
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setResizable(false);
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
