package me.noteapp.controller;

import javafx.fxml.FXML;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import me.noteapp.model.User;
import me.noteapp.service.UserService;
import me.noteapp.util.SceneSwitcher;
import me.noteapp.util.SessonManager;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final UserService userService = new UserService();
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    public void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Username and password cannot be empty");
            return;
        }

        try{
            User user = userService.loginUser(username, password);

            if(user != null) {
                SessonManager.login(user.getId());

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

                double width = screenBounds.getWidth() * 0.7;
                double height = screenBounds.getHeight() * 0.75;

                currentStage.setWidth(width);
                currentStage.setHeight(height);

                currentStage.centerOnScreen();
               SceneSwitcher.switchScene(currentStage, "/ui/MainApplicationView.fxml", "Note App"); //
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
            }
        } catch (SQLException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "Application Error", "An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

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
    /*public void handleMinimize(ActionEvent event){

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }*/

    @FXML
    public void handleClose(ActionEvent event){

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }



    @FXML
    public void initialize() {

        System.out.println("Login controller initialized..");
    }
}
