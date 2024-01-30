module com.matin.homework1fxmatin.homework1fxmatin {
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

    opens com.matin.homework1fxmatin.homework1fxmatin to javafx.fxml;
    exports com.matin.homework1fxmatin.homework1fxmatin;
}