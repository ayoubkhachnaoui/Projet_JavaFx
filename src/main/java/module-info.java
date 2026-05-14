module org.example.projet_java_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires java.prefs;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens org.example.projet_java_fx to javafx.fxml;
    opens org.example.projet_java_fx.controllers to javafx.fxml;
    opens org.example.projet_java_fx.models to javafx.base;
    
    exports org.example.projet_java_fx;
    exports org.example.projet_java_fx.controllers;
    exports org.example.projet_java_fx.models;
}