package com.example.javafx;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;


import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/javafx/main-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);

        stage.setScene(scene);
        stage.show();
    }

}
