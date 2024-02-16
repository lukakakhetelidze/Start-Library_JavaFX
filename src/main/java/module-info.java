module com.example.javafx_scene_builder {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.javafx_scene_builder to javafx.fxml;
    exports com.example.javafx_scene_builder;
}