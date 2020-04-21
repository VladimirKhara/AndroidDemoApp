package com.example.easyclusterapp;

public class User {

    private int id;
    private int ava;
    private String login;
    private String pass;

    public User(int id, int ava, String login, String pass) {
        this.id = id;
        this.ava = ava;
        this.login = login;
        this.pass = pass;
    }
    public User() {
        this.id = 0;
        this.ava = 0;
        this.login = null;
        this.pass = null;
    }

    public int getId() { return  this.id; }

    public void setId(int id) { this.id = id; }

    public int getAva() {
        return this.ava;
    }

    public void setAva(int ava) {
        this.ava = ava;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
