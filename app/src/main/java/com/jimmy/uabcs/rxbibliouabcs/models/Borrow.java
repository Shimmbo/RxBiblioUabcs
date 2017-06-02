package com.jimmy.uabcs.rxbibliouabcs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Borrow {

    //region Fields
    @Expose
    @SerializedName("id")
    public Long _id;
    @Expose
    private int IdBorrow;
    @Expose
    private Date Borrowed;
    @Expose
    private Date Returned;
    @Expose
    private int Prolongations;
    @Expose
    private String IdUser;
    @Expose
    private boolean State;
    //endregion

    //region Getters & Setters
    public int getIdBorrow() {
        return IdBorrow;
    }

    public void setIdBorrow(int idBorrow) {
        IdBorrow = idBorrow;
    }

    public Date getBorrowed() {
        return Borrowed;
    }

    public void setBorrowed(Date borrowed) {
        Borrowed = borrowed;
    }

    public Date getReturned() {
        return Returned;
    }

    public void setReturned(Date returned) {
        Returned = returned;
    }

    public int getProlongations() {
        return Prolongations;
    }

    public void setProlongations(int prolongations) {
        Prolongations = prolongations;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public boolean isState() {
        return State;
    }

    public void setState(boolean state) {
        State = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Borrow borrow = (Borrow) o;

        return _id.equals(borrow._id);

    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }

    //endregion
}
