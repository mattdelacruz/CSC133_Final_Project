module com.project.a3 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.project.a3 to javafx.fxml;
    exports com.project.a3;
}
