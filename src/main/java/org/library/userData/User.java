package org.library.userData;

public class User {

    private static String username;

    public User(String username) {
        User.username = username;
    }

    public User() {
    }

    public static String getUsername() {
        return username;
    }
}
