package me.noteapp.util;

public class SessonManager {

    public static int currentUserId = -1;

    public static void login(int userId) {
        currentUserId = userId;
        System.out.println("User" +userId+" loggedin[sessonmanager]");
    }

    public static void logout() {
        currentUserId = -1;
        System.out.println("User loggedout [sessonmanager]");
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public  static boolean isLogged() {
        return currentUserId != -1;
    }
}
