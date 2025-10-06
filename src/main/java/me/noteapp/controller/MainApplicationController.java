package me.noteapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainApplicationController {

    @FXML
    private AnchorPane noteListContainer;

    @FXML
    private AnchorPane noteEditorContainer;



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
