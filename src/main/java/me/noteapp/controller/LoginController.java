package me.noteapp.controller;

import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

public class LoginController {

    private double xOffset = 0;
    private double yOffset = 0;

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

    /*@FXML
    public void handleMinimize(ActionEvent event){

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }*/

    @FXML
    public void handleClose(ActionEvent event){

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    @FXML
    public void handleLoginButtonAction(){
        System.out.println("Button test!");
    }



    @FXML
    public void initialize() {

        System.out.println("Login controller initialized..");
    }
}
