package com.jimmy.uabcs.rxbibliouabcs.viewmodels;

import com.jimmy.uabcs.rxbibliouabcs.models.Book;
import com.jimmy.uabcs.rxbibliouabcs.models.Publisher;

import java.util.List;

/**
 * Created by Jimmy on 01/06/2017.
 */

public class PublisherAdapterViewModel {
    private Publisher publisher;

    public PublisherAdapterViewModel(Publisher publisher) {
        this.publisher = publisher;
    }
    public String getName() {
        return this.publisher.getName();
    }

    public String getImage() {
        return this.publisher.getImagePath();
    }

    public List<Book> getBook() {
        return this.publisher.getBook();
    }
}
