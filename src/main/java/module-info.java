module maplab.com.lab7 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens Controllers to javafx.fxml;
    opens Domain to javafx.fxml, javafx.base;
    exports maplab.com.lab7;
}