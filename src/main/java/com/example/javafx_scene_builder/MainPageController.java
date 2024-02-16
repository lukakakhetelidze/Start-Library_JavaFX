package com.example.javafx_scene_builder;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController {
    public void logoutAction(ActionEvent actionEvent) throws IOException {
     scenechanger("hello-view.fxml", actionEvent);
    }
    public void scenechanger(String fxmlInfo, ActionEvent event) throws IOException {
        Parent registerLayout = FXMLLoader.load(getClass().getResource(fxmlInfo));
        Scene scene = new Scene(registerLayout);
        // Get the stage from the event source
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
    }
}
