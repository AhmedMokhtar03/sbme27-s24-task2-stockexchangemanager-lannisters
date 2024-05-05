module org.example.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires annotations;

    opens org.example.main to javafx.fxml;
    exports org.example.main;
    exports main.java;
    opens main.java to javafx.fxml;
    exports main;
    opens main to javafx.fxml;


}