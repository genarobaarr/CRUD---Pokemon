package com.pokemon.model;

import java.io.Serializable;

public class Login implements Serializable{
    private int id;
    private String user;
    private String password;

    public Login() {
    }

    public Login(int id, String user, String password) {
        this.id = id;
        this.password = password;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
