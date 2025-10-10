package me.noteapp.controller;

import java.awt.Desktop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.noteapp.model.Attachment;
import me.noteapp.model.Note;
import me.noteapp.service.AttachmentService;
import me.noteapp.service.NoteService;
import me.noteapp.util.SessonManager;

import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.scene.control.Label;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class NoteEditorController {

    @FXML
    private Label createdAtLabel;

    @FXML
    private Label updatedAtLabel;

    @FXML
    private TextField titleField;
    @FXML
    private TextArea contentArea;
    @FXML
    private Button saveButton;
    @FXML
    private Button attachFileButton;
    @FXML
    private VBox attachmentContainer;
    @FXML
    private Button categoryButton;
    @FXML
    private HBox timestampContainer;
    @FXML
    private Button deleteButton;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' hh:mm a");

    private final AttachmentService attachmentService = new AttachmentService();


    private MainApplicationController mainController;
    private final NoteService noteService = new NoteService();
    private Note currentlySelectedNote;

    public void setMainController(MainApplicationController mainController) {

        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        System.out.println("NoteEditor controller initialized..");
    }

    public void displayNoteDetails(Note note) {
        if (note != null) {
            this.currentlySelectedNote = note;
            titleField.setText(note.getTitle());
            contentArea.setText(note.getContent());

            createdAtLabel.setText("Created: " + note.getCreatedAt().format(formatter));
            updatedAtLabel.setText("Modified: " + note.getUpdatedAt().format(formatter));

            loadAndDisplayAttachments(note);
        } else {
            clearEditor();
        }
    }


    public void clearEditor() {
        this.currentlySelectedNote = null;
        titleField.clear();
        contentArea.clear();
        titleField.setPromptText("New Note Title...");
        attachmentContainer.getChildren().clear();
        createdAtLabel.setText("");
        updatedAtLabel.setText("");
    }

    @FXML
    void handleAttachFileAction(ActionEvent event) {
        if (currentlySelectedNote == null) {
            showAlert(Alert.AlertType.INFORMATION, "Save Note First", "Please save this note before adding an attachment.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Attach a File");
        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (file != null) {
            try {
                attachmentService.addAttachment(currentlySelectedNote, file);
                loadAndDisplayAttachments(currentlySelectedNote);
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add attachment: " + e.getMessage());
            }
        }
    }


    private void loadAndDisplayAttachments(Note note) {
        attachmentContainer.getChildren().clear();
        try {
            List<Attachment> attachments = attachmentService.getAttachmentsForNote(note.getId());
            boolean hasAttachments = !attachments.isEmpty();

            if (hasAttachments) {

                attachmentContainer.setVisible(true);
                attachmentContainer.setManaged(true);
                attachmentContainer.setMinHeight(VBox.USE_COMPUTED_SIZE);
                attachmentContainer.setPrefHeight(VBox.USE_COMPUTED_SIZE);

                Label title = new Label("Attachments:");
                title.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
                attachmentContainer.getChildren().add(title);

                for (Attachment attachment : attachments) {
                    Label fileLabel = new Label(attachment.getFilename());
                    fileLabel.setStyle("-fx-text-fill: #98C379; -fx-cursor: hand;");
                    fileLabel.setOnMouseClicked(e -> {
                        try {
                            Desktop.getDesktop().open(new File(attachment.getFilepath()));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    });
                    attachmentContainer.getChildren().add(fileLabel);
                }
            } else {
                attachmentContainer.setVisible(false);
                attachmentContainer.setManaged(false);
                attachmentContainer.setMinHeight(0);
                attachmentContainer.setPrefHeight(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void handleSaveButtonAction(ActionEvent event) {
        String title = titleField.getText();
        String content = contentArea.getText();
        int userId = SessonManager.getCurrentUserId();

        if (title.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Title cannot be empty.");
            return;
        }

        try {
            if (currentlySelectedNote == null) {
                noteService.addNote(title, content, userId);
            } else {
                currentlySelectedNote.setTitle(title);
                currentlySelectedNote.setContent(content);
                noteService.updateNote(currentlySelectedNote);
            }
            showAlert(Alert.AlertType.INFORMATION, "Success", "Note was saved successfully!");

            if (mainController != null) {
                mainController.refreshNoteList();
            }

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save the note.");
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
    void handleCategoryButtonAction(ActionEvent event) {
        if (currentlySelectedNote == null) {
            showAlert(Alert.AlertType.INFORMATION, "Save Note First", "Please save this note before assigning categories.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/CategoryManagementView.fxml"));
            Parent page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Manage Categories");
            dialogStage.initStyle(StageStyle.TRANSPARENT);

            Scene scene = new Scene(page);
            scene.setFill(Color.TRANSPARENT);

            scene.getStylesheets().add(getClass().getResource("/css/list-styles.css").toExternalForm());

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            dialogStage.setScene(scene);

            CategoryManagementController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.loadData(currentlySelectedNote);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleDeleteButtonAction(ActionEvent event) {
        if (currentlySelectedNote == null) {
            showAlert(Alert.AlertType.WARNING, "No Note Selected", "There is no note selected to delete.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Note");
        confirmationAlert.setHeaderText("Are you sure you want to delete this note?");
        confirmationAlert.setContentText("This will permanently delete the note '" + currentlySelectedNote.getTitle() + "' and all of its attachments.");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                noteService.deleteNote(currentlySelectedNote);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Note was deleted successfully.");

                if (mainController != null) {
                    mainController.refreshNoteList();
                }
                clearEditor();

            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete the note.");
            }
        }
    }
}
