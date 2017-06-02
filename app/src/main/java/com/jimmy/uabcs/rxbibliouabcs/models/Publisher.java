package com.jimmy.uabcs.rxbibliouabcs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Publisher {
    //region Fields
    private String Name;
    private int IdPublisher;
    private List<Book> Book;
    private String ImagePath;
    //endregion

    //region Getters & Setters


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getIdPublisher() {
        return IdPublisher;
    }

    public void setIdPublisher(int idPublisher) {
        IdPublisher = idPublisher;
    }

    public List<com.jimmy.uabcs.rxbibliouabcs.models.Book> getBook() {
        return Book;
    }

    public void setBook(List<com.jimmy.uabcs.rxbibliouabcs.models.Book> book) {
        Book = book;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    //endregion
}
