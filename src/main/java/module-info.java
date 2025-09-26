module org {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop; // Pour Scanner

    opens org to javafx.fxml;
    opens model to javafx.base;
    opens dao to javafx.base;

    exports org;
    exports model;
    exports dao;
}