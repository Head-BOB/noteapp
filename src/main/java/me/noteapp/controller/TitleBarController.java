package me.noteapp.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TitleBarController {

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    void handleClose(ActionEvent event) {
        Stage stage = getStage(event);
        if (stage != null) {
            stage.close();
        }
    }

    @FXML
    void handleMaximize(ActionEvent event) {
        Stage stage = getStage(event);
        if (stage != null) {
            stage.setMaximized(!stage.isMaximized());
        }
    }

    @FXML
    void handleMinimize(ActionEvent event) {
        Stage stage = getStage(event);
        if (stage != null) {
            stage.setIconified(true);
        }
    }

    @FXML
    void onMouseDragged(MouseEvent event) {
        Stage stage = getStage(event);
        if (stage != null) {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        }
    }

    @FXML
    void onMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private Stage getStage(Event event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }
}