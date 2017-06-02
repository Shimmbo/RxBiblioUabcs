package com.jimmy.uabcs.rxbibliouabcs.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.jimmy.uabcs.rxbibliouabcs.R;
import com.jimmy.uabcs.rxbibliouabcs.models.Book;
import com.jimmy.uabcs.rxbibliouabcs.utils.VolleySingleton;
import com.jimmy.uabcs.rxbibliouabcs.viewmodels.BookAdapterViewModel;

import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.DEFAULT_IMAGE;
import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.GSON;
import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.URL;

public class BookFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";

    private BookAdapterViewModel mBook;

    public BookFragment() {

    }

    public static BookFragment newInstance(String param1) {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Book _book = GSON.fromJson(getArguments().getString(ARG_PARAM1), Book.class);
            mBook = new BookAdapterViewModel(_book);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book, container, false);
        TextView title = (TextView) rootView.findViewById(R.id.title);
        title.setText(mBook.getName());
        String imaegPath = mBook.getImage();
        NetworkImageView img = (NetworkImageView) rootView.findViewById(R.id.thumbnail);
        ImageLoader mImageLoader = VolleySingleton.getInstance().getImageLoader();
        if (imaegPath == null || imaegPath == "")
            img.setImageUrl(URL + DEFAULT_IMAGE, mImageLoader);
        else
            img.setImageUrl(URL + imaegPath, mImageLoader);

        TextView authors = (TextView) rootView.findViewById(R.id.authors);
        authors.setText(mBook.getAuthor());

        TextView isbn = (TextView) rootView.findViewById(R.id.isbn);
        isbn.setText(getString(R.string.isbn, mBook.getISBN()));

        TextView genres = (TextView) rootView.findViewById(R.id.genre);
        genres.setText(mBook.getGenre());

        TextView publisher = (TextView) rootView.findViewById(R.id.publisher);
        publisher.setText(mBook.getPublisherName());
        return rootView;
    }
}
