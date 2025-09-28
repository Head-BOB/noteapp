package me.noteapp;

//main class

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import me.noteapp.db.DatabaseInitializer;

public class App {

    public static void main (String[] args) {

        System.out.println("Note APP started..!");

        //Database initialization

        try{

            DatabaseInitializer.initialize();
            System.out.println("Database initialized successfully");

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());

        }

        //for running the ui in main thread by default ayi main method vere thread il an run akunath so ith vach main method run akan force cheyum ui work akanel main method run akanam
        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Note App");
            frame.setSize(400,300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

        });

    }
}
