module com.example.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.base;
    requires com.fasterxml.jackson.databind;
    requires jdk.httpserver;
    requires com.google.gson;
    requires java.dotenv;

    opens com.example.javafx to javafx.fxml;
    opens models to com.google.gson;

    exports com.example.javafx;
    exports models;
}
