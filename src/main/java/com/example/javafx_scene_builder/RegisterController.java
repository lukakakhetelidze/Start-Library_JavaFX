package com.example.javafx_scene_builder;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class RegisterController {
    public Label usernameWarning;
    public TextField usernameTextfField;
    public TextField passwordTextfField1;
    public TextField passwordTextfField2;
    Connection connection = null;
    public void changeSceneLogin(ActionEvent actionEvent) throws IOException {
        scenechanger("hello-view.fxml", actionEvent);
    }

    void insertIntoDatabase(String username, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username,password ) VALUES (?,?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.executeUpdate();
        System.out.println("saved");
    }

    void duplicateConnection(){
        connection = Main.connection;
    }
    boolean arefieldsBlank(TextField field1, TextField field2,TextField field3){
        return !field1.getText().isBlank() && !field2.getText().isBlank() && !field3.getText().isBlank();
    }

    public void submitButtonAction(ActionEvent actionEvent) throws SQLException {
        duplicateConnection();

        String username = usernameTextfField.getText();
        String password1 = passwordTextfField1.getText();
        String password2 = passwordTextfField2.getText();

        if (!arefieldsBlank(usernameTextfField, passwordTextfField1, passwordTextfField2)) {
            usernameWarning.setText("Fill all the blanks");
            return;
        }


        PreparedStatement usernameCheckStatement = connection.prepareStatement("SELECT username FROM users WHERE username = ?");
        usernameCheckStatement.setString(1, username);
        ResultSet usernameCheckResult = usernameCheckStatement.executeQuery();

        if (usernameCheckResult.next()) {
            usernameWarning.setText("Username already used");
            return;
        }

        if (!Objects.equals(password1, password2)) {
            usernameWarning.setText("Passwords do not match");
            return;
        }


        insertIntoDatabase(username, password1);
        usernameWarning.setText("Successful registration");
    }
    public void scenechanger(String fxmlInfo, ActionEvent event) throws IOException {
        Parent registerLayout = FXMLLoader.load(getClass().getResource(fxmlInfo));
        Scene scene = new Scene(registerLayout);
        // Get the stage from the event source
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
    }

}
