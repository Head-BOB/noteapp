package me.noteapp.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    public static void switchScene(Stage stage, String fxmlPath, String title)throws IOException {

        FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlPath));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();

    }
}