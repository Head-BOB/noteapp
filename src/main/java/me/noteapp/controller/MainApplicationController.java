package me.noteapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.noteapp.model.Note;
import java.io.IOException;

public class MainApplicationController {

    @FXML
    private VBox noteListContainer;
    @FXML
    private VBox noteEditorContainer;

    private NoteListController noteListController;
    private NoteEditorController noteEditorController;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    public void initialize() {
        System.out.println("MainApplication controller initialized..");
        loadNoteList();
        loadNoteEditor();
    }

  private void loadNoteList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/NoteListPane.fxml"));
            AnchorPane noteListPane = loader.load();
            noteListController = loader.getController();
            noteListController.setMainController(this);
            noteListContainer.getChildren().add(noteListPane);
            VBox.setVgrow(noteListPane, Priority.ALWAYS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadNoteEditor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/NoteEditorPane.fxml"));
            AnchorPane noteEditorPane = loader.load();
            noteEditorController = loader.getController();
            noteEditorController.setMainController(this);
            noteEditorContainer.getChildren().add(noteEditorPane);
            VBox.setVgrow(noteEditorPane, Priority.ALWAYS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onNoteSelected(Note note) {
        if (noteEditorController != null) {
            noteEditorController.displayNoteDetails(note);
        }
    }

    public void refreshNoteList() {
        if (noteListController != null) {
            noteListController.refreshNotes();
        }
    }

    @FXML
    void onMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }
    @FXML
    void onMouseDragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }
    @FXML
    void handleMinimize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    void handleMaximize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setMaximized(!stage.isMaximized());
    }
    @FXML
    void handleClose(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    void handleNewNoteAction(ActionEvent event) {
        if (noteEditorController != null) {
            noteEditorController.clearEditor();
        }
    }
}