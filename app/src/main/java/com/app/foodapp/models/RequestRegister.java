package com.app.foodapp.models;

public class RequestRegister {
    private String name;
    private String email;
    private String password;
    private String c_password;

    public RequestRegister(String name, String email, String password, String c_password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.c_password = c_password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getC_password() {
        return c_password;
    }

    public void setC_password(String c_password) {
        this.c_password = c_password;
    }
}
