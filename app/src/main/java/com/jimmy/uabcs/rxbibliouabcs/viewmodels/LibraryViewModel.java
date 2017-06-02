package com.jimmy.uabcs.rxbibliouabcs.viewmodels;

import com.jimmy.uabcs.rxbibliouabcs.models.Author;
import com.jimmy.uabcs.rxbibliouabcs.models.Book;
import com.jimmy.uabcs.rxbibliouabcs.models.GeneralResponse;
import com.jimmy.uabcs.rxbibliouabcs.models.LoginResponse;
import com.jimmy.uabcs.rxbibliouabcs.models.Publisher;
import com.jimmy.uabcs.rxbibliouabcs.models.User;
import com.jimmy.uabcs.rxbibliouabcs.models.UserLogin;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import static com.jimmy.uabcs.rxbibliouabcs.App.getApi;

/**
 * Created by Jimmy on 01/06/2017.
 */

public class LibraryViewModel {
    private BehaviorSubject<ArrayList<BookAdapterViewModel>> booksSubject =
            BehaviorSubject.create(new ArrayList<BookAdapterViewModel>());
    private BehaviorSubject<ArrayList<PublisherAdapterViewModel>> publishersSubject =
            BehaviorSubject.create(new ArrayList<PublisherAdapterViewModel>());
    private BehaviorSubject<ArrayList<AuthorAdapterViewModel>> authorsSubject =
            BehaviorSubject.create(new ArrayList<AuthorAdapterViewModel>());
    private BehaviorSubject<BookAdapterViewModel> bookSubject = BehaviorSubject.create();
    private BehaviorSubject<Boolean> isLoadingSubject = BehaviorSubject.create(false);

    private static LibraryViewModel mInstance;

    public LibraryViewModel() {

    }

    public static LibraryViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new LibraryViewModel();
        }
        return mInstance;
    }
    public Observable<List<BookAdapterViewModel>> getBooks() {
        if (isLoadingSubject.getValue()) {
            return Observable.empty();
        }

        isLoadingSubject.onNext(true);
        return getApi()
                .getBooks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMapIterable(list -> list)
                .map(book -> new BookAdapterViewModel((Book) book))
                .toList()
                .doOnNext(list -> {
                    ArrayList<BookAdapterViewModel> fullList = new ArrayList<BookAdapterViewModel>();
                    fullList.addAll(list);
                    booksSubject.onNext(fullList);
                })
                .doOnTerminate(() -> isLoadingSubject.onNext(false));
    }

    public Observable<List<PublisherAdapterViewModel>> getPublishers() {
        if (isLoadingSubject.getValue()) {
            return Observable.empty();
        }

        isLoadingSubject.onNext(true);
        return getApi()
                .getPublishers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMapIterable(list -> list)
                .map(publisher -> new PublisherAdapterViewModel((Publisher) publisher))
                .toList()
                .doOnNext(list -> {
                    ArrayList<PublisherAdapterViewModel> fullList = new ArrayList<PublisherAdapterViewModel>();
                    fullList.addAll(list);
                    publishersSubject.onNext(fullList);
                })
                .doOnTerminate(() -> isLoadingSubject.onNext(false));
    }

    public Observable<List<AuthorAdapterViewModel>> getAuthors() {
        if (isLoadingSubject.getValue()) {
            return Observable.empty();
        }

        isLoadingSubject.onNext(true);
        return getApi()
                .getAuthors()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMapIterable(list -> list)
                .map(author -> new AuthorAdapterViewModel((Author) author))
                .toList()
                .doOnNext(list -> {
                    ArrayList<AuthorAdapterViewModel> fullList = new ArrayList<AuthorAdapterViewModel>();
                    fullList.addAll(list);
                    authorsSubject.onNext(fullList);
                })
                .doOnTerminate(() -> isLoadingSubject.onNext(false));
    }

    public Observable<BookAdapterViewModel> getBook(int id) {
        if (isLoadingSubject.getValue()) {
            return Observable.empty();
        }

        isLoadingSubject.onNext(true);
        return getApi()
                .getBook(id)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .map(book -> new BookAdapterViewModel(book))
                .doOnNext(book -> {
                    bookSubject.onNext(book);
                })
                .doOnTerminate(() -> isLoadingSubject.onNext(false));
    }

    public Observable<LoginResponse> login(UserLogin login) {
        isLoadingSubject.onNext(true);
        return getApi()
                .login(login.getUsername(), login.getPassword(), login.getGrant_type());
    }

    public Observable<GeneralResponse> register(User user) {
        isLoadingSubject.onNext(true);
        return getApi()
                .register(user);
    }

    public Observable<ArrayList<PublisherAdapterViewModel>> publishers() {
        return publishersSubject.asObservable();
    }

    public Observable<ArrayList<BookAdapterViewModel>> books() {
        return booksSubject.asObservable();
    }
    public ArrayList<BookAdapterViewModel> booksData() {
        return booksSubject.getValue();
    }
    public Observable<BookAdapterViewModel> book() {
        return bookSubject.asObservable();
    }
    public Observable<ArrayList<AuthorAdapterViewModel>> authors() {
        return authorsSubject.asObservable();
    }
    public Observable<Boolean> isLoadingObservable() {
        return isLoadingSubject.asObservable();
    }

    public void setIsLoadingSubject(boolean isLoading) {
        isLoadingSubject.onNext(isLoading);
    }
}
