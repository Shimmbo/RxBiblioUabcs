package com.jimmy.uabcs.rxbibliouabcs.viewmodels;

import com.jimmy.uabcs.rxbibliouabcs.models.Author;
import com.jimmy.uabcs.rxbibliouabcs.models.Book;

import java.util.List;

/**
 * Created by Jimmy on 01/06/2017.
 */

public class AuthorAdapterViewModel {
    private Author author;

    public AuthorAdapterViewModel(Author author) {
        this.author = author;
    }
    public String getName() {
        return this.author.getName();
    }

    public String getImage() {
        return this.author.getImagePath();
    }

    public List<Book> getBook() {
        return this.author.getBook();
    }
}
