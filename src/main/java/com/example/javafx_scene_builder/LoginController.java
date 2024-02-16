
package com.example.javafx_scene_builder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    Connection connection = null;
    @FXML
    public Label loginMassage;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField usernameTextfield;
    @FXML
    private TextField passwordTextfield;


        //main login fucntion
    @FXML
    void goingToLogin(ActionEvent event) {

           if(areFieldsBlank(usernameTextfield,passwordTextfield)){
               loginMassage.setText("Fill in the blanks");
           }else {
               try {
                   String username = usernameTextfield.getText();
                   String password = passwordTextfield.getText();
                   userlogin(username,password,event);
               } catch (SQLException e) {
                   throw new RuntimeException(e);
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
           }

    }

        void duplicateConnection(){
        connection = Main.connection;
        }



        void userlogin(String username, String password, ActionEvent event) throws SQLException, IOException {
            duplicateConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT username, password FROM users WHERE username = '" + username + "';");

            boolean loginSuccessful = false;

            while (resultSet.next()) {
                String SQLpassword = resultSet.getNString("password");

                if (password.equals(SQLpassword)) {
                    loginSuccessful = true;
                    break;
                }
            }

            if (loginSuccessful) {
                loginMassage.setText("Successful Login");
                scenechanger("mainPage.fxml",event);
            } else {
                loginMassage.setText("Incorrect password or username");
            }
        }
        public void scenechanger(String fxmlInfo, ActionEvent event) throws IOException {
            Parent registerLayout = FXMLLoader.load(getClass().getResource(fxmlInfo));
            Scene scene = new Scene(registerLayout);
            // Get the stage from the event source
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
        }
//method which changes scene

    public void changeSceneRegister(ActionEvent event) throws IOException {
        scenechanger("register.fxml",event);
    }

    public void cancelButtonAvtion(ActionEvent actionEvent) {
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
    boolean areFieldsBlank(TextField field1,TextField field2){
        return field1.getText().isBlank() || field2.getText().isBlank();
    }

}
