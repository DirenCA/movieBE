package de.htwberlin.webtech.moviediary.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userName) {
        super("Der Benutzer " + userName + " konnte nicht gefunden werden.");
    }
}
