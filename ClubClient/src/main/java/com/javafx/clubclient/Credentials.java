package com.javafx.clubclient;

import java.io.Serializable;
import java.util.Objects;

public class Credentials implements Serializable {
    private String username;
    private String password;

    //private static final String OUTPUT_FILE_NAME = "F:\\java\\Player Database\\src\\main\\resources\\com\\example\\player_database\\players.txt";

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//
//        if (obj.getClass() != this.getClass()) {
//            return false;
//        }
//
//        final Credentials other = (Credentials) obj;
//        if (!Objects.equals(this.username, other.username)) {
//            return false;
//        }
//
//        return this.password.equals(other.password);
//    }

    // Override equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Credentials that = (Credentials) obj;
        return Objects.equals(username, that.username); // Use username to identify uniqueness
    }

    // Override hashCode
    @Override
    public int hashCode() {
        return Objects.hash(username); // Use the same field as in equals
    }

}
