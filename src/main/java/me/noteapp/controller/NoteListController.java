package me.noteapp.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import me.noteapp.model.Note;
import me.noteapp.service.NoteService;
import me.noteapp.util.SessonManager;

import java.sql.SQLException;
import java.util.List;

public class NoteListController {

    @FXML
    private ListView<Note> noteListView;

    private MainApplicationController mainController;
    private final NoteService noteService = new NoteService();

    public void setMainController(MainApplicationController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        System.out.println("NoteList controller initialized..");
        noteListView.setStyle("-fx-background-color: transparent;");
        refreshNotes();

        noteListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && mainController != null) {
                mainController.onNoteSelected(newValue);
            }
        });
    }

    public void refreshNotes() {
        if (SessonManager.isLogged()) {
            try {
                List<Note> userNotes = noteService.getNotesForUser(SessonManager.getCurrentUserId());
                noteListView.setItems(FXCollections.observableArrayList(userNotes));
                System.out.println("Note list refreshed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}