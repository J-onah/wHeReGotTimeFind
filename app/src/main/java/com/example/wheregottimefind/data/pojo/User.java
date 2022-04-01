package com.example.wheregottimefind.data.pojo;

public class User {
    String name;
    int id;
    String temp_auth_token;
    String login_error;
    String display_name;

    public User(String login_error) {
        this.login_error = login_error;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", temp_auth_token='" + temp_auth_token + '\'' +
                ", login_error='" + login_error + '\'' +
                '}';
    }

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

    public String getLogin_error() {
        return login_error;
    }

    public void setLogin_error(String login_error) {
        this.login_error = login_error;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getDisplay_name() {
        if (display_name == null && !name.isEmpty()) {
            int atIndex = name.indexOf('@');
            this.display_name = capitaliseName(name.substring(0, atIndex));
        }
        return display_name;
    }

    public User(String name, int id, String temp_auth_token, String login_error) {
        this.name = name;
        this.id = id;
        this.temp_auth_token = temp_auth_token;
        this.login_error = login_error;
    }

    private static String capitaliseName(String emailFront){
        String words[] = emailFront.split("_");
        String res = "";

        for (String w: words){
            String first = w.substring(0,1);
            String others = w.substring(1);
            res += first.toUpperCase() + others + " ";
        }
        return res.trim();
    }
}
