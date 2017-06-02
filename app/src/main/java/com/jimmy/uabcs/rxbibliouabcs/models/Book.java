package com.jimmy.uabcs.rxbibliouabcs.models;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Book {

    //region Fields
    private int IdBook;
    private int IdPublisher;
    private String Name;
    private Date Year;
    private int Pages;
    private int Quantity;
    private int Edition;
    private String Place;
    private String ISBN;
    private String ISSN;
    private String Path;
    private String ImagePath;
    private List<Author> Author;
    private List<Genre> Genre;
    private Publisher Publisher;
    //endregion

    //region Getters & Setters

    public int getIdBook() {
        return IdBook;
    }

    public void setIdBook(int idBook) {
        IdBook = idBook;
    }

    public int getIdPublisher() {
        return IdPublisher;
    }

    public void setIdPublisher(int idPublisher) {
        IdPublisher = idPublisher;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getYear() {
        return Year;
    }

    public void setYear(Date year) {
        Year = year;
    }

    public int getPages() {
        return Pages;
    }

    public void setPages(int pages) {
        Pages = pages;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getEdition() {
        return Edition;
    }

    public void setEdition(int edition) {
        Edition = edition;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getISSN() {
        return ISSN;
    }

    public void setISSN(String ISSN) {
        this.ISSN = ISSN;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public List<com.jimmy.uabcs.rxbibliouabcs.models.Author> getAuthor() {
        return Author;
    }

    public void setAuthor(List<com.jimmy.uabcs.rxbibliouabcs.models.Author> author) {
        Author = author;
    }

    public List<com.jimmy.uabcs.rxbibliouabcs.models.Genre> getGenre() {
        return Genre;
    }

    public void setGenre(List<com.jimmy.uabcs.rxbibliouabcs.models.Genre> genre) {
        Genre = genre;
    }

    public com.jimmy.uabcs.rxbibliouabcs.models.Publisher getPublisher() {
        return Publisher;
    }

    public void setPublisher(com.jimmy.uabcs.rxbibliouabcs.models.Publisher publisher) {
        Publisher = publisher;
    }
    //endregion
}
