package me.noteapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import me.noteapp.model.Note;
import java.io.IOException;
import java.time.format.DateTimeFormatter;



public class NoteListCell extends ListCell<Note> {

    @FXML
    private Label titleLabel;
    @FXML
    private AnchorPane cellLayout;

    private FXMLLoader fxmlLoader;

    @FXML
    private Label dateLabel;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");



    @Override
    protected void updateItem(Note note, boolean empty) {
        super.updateItem(note, empty);
        if (empty || note == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/ui/NoteListCell.fxml"));
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            titleLabel.setText(note.getTitle());
            titleLabel.setTextFill(Color.WHITE);

            dateLabel.setText(note.getUpdatedAt().format(formatter));

            setGraphic(cellLayout);
        }
    }
}