package com.jimmy.uabcs.rxbibliouabcs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Author {

    //region Fields
    private int IdAuthor;
    private String Name;
    private List<Book> Book;
    private String ImagePath;
    //endregion

    //region Getters & Setters

    public int getIdAuthor() {
        return IdAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        IdAuthor = idAuthor;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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
