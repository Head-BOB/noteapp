package me.noteapp.controller;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import me.noteapp.service.UserService;

import java.sql.SQLException;

public class RegistrationController {
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private final UserService userService = new UserService();

    @FXML
    public void onMousePressed(MouseEvent event) {

        xOffset = event.getSceneX();
        yOffset = event.getSceneY();

    }

    @FXML
    public void onMouseDragged(MouseEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);

    }

    @FXML
    public void handleClose(ActionEvent event){

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }
    @FXML
    public void initialize() {

        System.out.println("Registration controller initialized..");
    }

    @FXML
    public void handleRegisterButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if(username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR,"Sign Up Error","Please enter correct username and password");
            return;
        }

        try{
            userService.registerUser(username, password, email);
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User "+ username +"has been registered!");

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed!", "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void showAlert(Alert.AlertType alertType, String title, String message) {

        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
