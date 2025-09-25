module org {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop; // Pour Scanner



    opens org to javafx.fxml;
    exports org;
}