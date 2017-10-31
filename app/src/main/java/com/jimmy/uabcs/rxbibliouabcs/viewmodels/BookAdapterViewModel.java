package com.jimmy.uabcs.rxbibliouabcs.viewmodels;

import android.text.TextUtils;

import com.jimmy.uabcs.rxbibliouabcs.models.Book;

import java.util.Date;

/**
 * Created by Jimmy on 01/06/2017.
 */

public class BookAdapterViewModel {
    private Book book;

    public BookAdapterViewModel(Book book) {
        this.book = book;
    }

    public String getGenre() {
        if (this.book.getGenre() == null) return "";
        String[] arrayNames = new String[this.book.getGenre().size()];
        for (int i = 0; i < this.book.getGenre().size(); i++) {
            arrayNames[i] = this.book.getGenre().get(i).getName();
        }
        return TextUtils.join(",", arrayNames);
    }

    public String getAuthor() {
        if(this.book.getAuthor() == null) return "";
        String[] arrayNames = new String[this.book.getAuthor().size()];
        for (int i = 0; i < this.book.getAuthor().size(); i++) {
            arrayNames[i] = this.book.getAuthor().get(i).getName();
        }
        return TextUtils.join(",", arrayNames);
    }

    public String getName() {
        return this.book.getName();
    }

    public String getImage() {
        return this.book.getImagePath();
    }

    public Date getYear() {
        return this.book.getYear();
    }

    public String getISBN() {
        return this.book.getISBN();
    }

    public String getPublisherName() {
        return this.book.getPublisher() != null ? this.book.getPublisher().getName() : "";
    }

    public int getId() {
        return this.book.getIdBook();
    }

    public String getDescription() {
        return this.book.getDescription();
    }
}
