package com.jimmy.uabcs.rxbibliouabcs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Booking {

    //region Fields
    @Expose
    @SerializedName("id")
    public Long _id;
    @Expose
    private int IdBooking;
    @Expose
    private Date Date;
    @Expose
    private byte State;
    @Expose
    private String IdUser;
    @Expose
    private int IdBook;
    //endregion

    //region Getters & Setters
    public int getIdBooking() {
        return IdBooking;
    }

    public void setIdBooking(int idBooking) {
        IdBooking = idBooking;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public byte getState() {
        return State;
    }

    public void setState(byte state) {
        State = state;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public int getIdBook() {
        return IdBook;
    }

    public void setIdBook(int idBook) {
        IdBook = idBook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;

        return _id.equals(booking._id);

    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }

    //endregion
}
