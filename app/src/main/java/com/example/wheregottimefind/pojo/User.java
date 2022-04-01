package com.example.wheregottimefind.pojo;

public class User {
    String name;
    int id;
    String temp_auth_token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTemp_auth_token() {
        return temp_auth_token;
    }

    public void setTemp_auth_token(String temp_auth_token) {
        this.temp_auth_token = temp_auth_token;
    }

    public User(String name, int id, String temp_auth_token) {
        this.name = name;
        this.id = id;
        this.temp_auth_token = temp_auth_token;
    }
}
