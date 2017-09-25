package com.jimmy.uabcs.rxbibliouabcs.api;

import com.jimmy.uabcs.rxbibliouabcs.models.Author;
import com.jimmy.uabcs.rxbibliouabcs.models.Book;
import com.jimmy.uabcs.rxbibliouabcs.models.Borrow;
import com.jimmy.uabcs.rxbibliouabcs.models.BaseResponse;
import com.jimmy.uabcs.rxbibliouabcs.models.Genre;
import com.jimmy.uabcs.rxbibliouabcs.models.LoginResponse;
import com.jimmy.uabcs.rxbibliouabcs.models.Publisher;
import com.jimmy.uabcs.rxbibliouabcs.models.User;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface ApiLibrary {

    @FormUrlEncoded
    @POST("login")
    Observable<LoginResponse> login(@Field("username") String username,
                                    @Field("password") String password,
                                    @Field("grant_type") String grant_type);

    @POST("account/register")
    Observable<BaseResponse> register(@Body User mUser);

    @GET("authors/author/{id}")
    Observable<Author> getAuthor(@Path("id") int id);

    @GET("authors/authors")
    Observable<List<Author>> getAuthors();

    @GET("books/getBook/{id}")
    Observable<Book> getBook(@Path("id") int id);

    @GET("books/books")
    Observable<List<Book>> getBooks();

    @GET("books/genres")
    Observable<List<Genre>> getGenres();

    @GET("publishers/publisher/{id}")
    Observable<Publisher> getPublisher(@Path("id") int id);

    @GET("publishers/publishers")
    Observable<List<Publisher>> getPublishers();

    @GET("users/userInfo/{id}")
    Observable<User> getUserInfo(@Path("id") String id);

    @GET("users/users")
    Observable<List<User>> getUsers();

    @GET("users/userDetails/{id}")
    Observable<User> getUserDetails(@Path("id") String id);

    @GET("users/borrows/{id}")
    Observable<Borrow> getBorrow(@Path("id") int id);
}
