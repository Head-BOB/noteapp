package me.noteapp;

//main class
import java.sql.Connection;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.noteapp.db.DatabaseInitializer;
import me.noteapp.db.DbConnectionManager;

import static javafx.application.Application.launch;

public class App extends Application {

    public static void main (String[] args) {

        System.out.println("Note APP started..!");

        //Database initialization

        try{

            DatabaseInitializer.initialize();
            System.out.println("Database initialized successfully");

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());

        }
            try (Connection conn = DbConnectionManager.getConnection()) {
                if (conn != null) {
                    System.out.println(" Connection successful!");
                }
            } catch (SQLException e) {
                System.err.println(" Connection failed: " + e.getMessage());
            }

        launch(args);

    }

    @Override
    public void start(Stage primaryStage)throws Exception{

        System.out.println("Java app starting..");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/LoginView.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");

        primaryStage.show();;


    }
}
