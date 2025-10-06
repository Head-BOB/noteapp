package me.noteapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import me.noteapp.model.Note;
import me.noteapp.service.NoteService;
import me.noteapp.util.SessonManager;
import java.sql.SQLException;

public class NoteEditorController {

    @FXML
    private Text titleField;
    @FXML
    private TextArea contentArea;
    @FXML
    private Button saveButton;

    private final NoteService noteService = new NoteService();

    @FXML
    public void initialize() {
        System.out.println("NoteEditor controller initialized..");
    }

    public void displayNoteDetails(String noteTitle) {
        titleField.setText(noteTitle);
        contentArea.setText("Content for '" + noteTitle + "' goes heree...");
    }

    public void clearEditor() {

        titleField.setText("New Note");
        contentArea.clear();
    }

    @FXML
    void handleSaveButtonAction(ActionEvent event) {
        String title = titleField.getText();
        String content = contentArea.getText();
        int userId = SessonManager.getCurrentUserId();

        if (title.isEmpty() || content.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Title and content cannot be empty");
            return;
        }

        if (!SessonManager.isLogged()) {
            showAlert(Alert.AlertType.ERROR, "Authentication Error", "No user is logged in");
            return;
        }

        try {
            Note newNote = noteService.addNote(title, content, userId);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Note '" + newNote.getTitle() + "' saved successfully");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save the note");
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
}