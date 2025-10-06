package me.noteapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import me.noteapp.model.Note;
import me.noteapp.service.NoteService;
import me.noteapp.util.SessonManager;

import java.sql.SQLException;
import java.util.List;

public class NoteListController {

    @FXML
    private ChoiceBox<Note> notesChoiceBox;

    @FXML
    private MainApplicationController mainController;

    private final NoteService noteService = new NoteService();

    @FXML
    public void setMainController(MainApplicationController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {

        System.out.println("NoteList controller initialized..");
        loadNotes();

        notesChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && mainController != null) {
                mainController.onNoteSelected(newValue.getTitle());
            }
        });
    }

    private void loadNotes() {

        int currentUsersId = SessonManager.getCurrentUserId();

        if(SessonManager.isLogged()) {
            try{

                List<Note> userNotes = noteService.getNotesForUser(currentUsersId);
                notesChoiceBox.setItems(FXCollections.observableArrayList(userNotes));
                notesChoiceBox.setValue(null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
