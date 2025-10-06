package me.noteapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainApplicationController {
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private AnchorPane noteListContainer;

    @FXML
    private AnchorPane noteEditorContainer;



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
    public void handleMinimize(ActionEvent event){

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    @FXML
    public void handleClose(ActionEvent event){

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }
    @FXML
    public void initialize() {

        System.out.println("MainApplication controller initialized..");
        loadNoteList();
    }

    public void loadNoteList() {

        try{

            FXMLLoader loader =  new FXMLLoader(getClass().getResource("/ui/NoteListPane.fxml"));
            AnchorPane noteListPane = loader.load();

            noteListContainer.getChildren().add(noteListPane);

            AnchorPane.setTopAnchor(noteListPane, 0.0);
            AnchorPane.setBottomAnchor(noteListPane, 0.0);
            AnchorPane.setLeftAnchor(noteListPane, 0.0);
            AnchorPane.setRightAnchor(noteListPane, 0.0);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
