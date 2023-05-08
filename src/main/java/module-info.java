module com.example.hotellkaks {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hotellkaks to javafx.fxml;
    exports com.example.hotellkaks;
}