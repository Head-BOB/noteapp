package me.noteapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.noteapp.controller.NoteListController;
import me.noteapp.controller.NoteEditorController;

public class MainApplicationController {
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private VBox noteListContainer;

    @FXML
    private VBox noteEditorContainer;

    private NoteListController noteListController;
    private NoteEditorController noteEditorController;



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

        System.out.println("MainApplication controller initialized..");
        loadNoteList();
        loadNoteEditor();
    }

    @FXML
    void handleNewNoteAction(ActionEvent event) {
        System.out.println("+ Notes button clicked");
        if (noteEditorController != null) {
            noteEditorController.clearEditor();
        }
    }

    private void loadNoteList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/NoteListPane.fxml"));
            AnchorPane noteListPane = loader.load();
            noteListController = loader.getController();
            noteListController.setMainController(this);
            noteListContainer.getChildren().add(noteListPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadNoteEditor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/NoteEditorPane.fxml"));
            AnchorPane noteEditorPane = loader.load();
            noteEditorController = loader.getController();
            noteEditorContainer.getChildren().add(noteEditorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onNoteSelected(String noteTitle) {
        System.out.println("Main controller test " + noteTitle);
        if (noteEditorController != null) {
            noteEditorController.displayNoteDetails(noteTitle);
        }
    }





}
