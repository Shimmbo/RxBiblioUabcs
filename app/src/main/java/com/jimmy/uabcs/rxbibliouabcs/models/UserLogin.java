package com.jimmy.uabcs.rxbibliouabcs.models;

import com.google.gson.annotations.Expose;

public class UserLogin {
    //region Fields
    @Expose
    private String username;
    @Expose
    private String password;
    @Expose
    private String grant_type;
    //endregion

    //region Getters & Setters

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

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
    //endregion
}
