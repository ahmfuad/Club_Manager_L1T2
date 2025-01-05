package com.javafx.clubclient;

import java.io.Serializable;

public class Message implements Serializable {
    private String type;
    private Object obj;
    private Credentials clientProfile;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Message(Object obj, String type) {
        this.obj = obj;
        this.type = type;
    }

    public Message(Object obj, Credentials clientProfile, String type) {
        this.obj = obj;
        this.clientProfile = clientProfile;
        this.type = type;
    }



    public Credentials getClientProfile() {
        return clientProfile;
    }

    public void setClientProfile(Credentials clientProfile) {
        this.clientProfile = clientProfile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public Message(String type, Credentials clientProfile) {
        this.type = type;
        this.clientProfile = clientProfile;
        this.obj = null;
    }


}