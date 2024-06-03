package de.htwberlin.webtech.moviediary.web.Rapi;

public class User {

    private long id;
    private String userName;
    private long password;

    public User(long id, long password, String userName) {
        this.id = id;
        this.password = password;
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getPassword() {
        return password;
    }

    public void setPassword(long password) {
        this.password = password;
    }
}
