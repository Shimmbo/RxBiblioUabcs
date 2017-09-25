package com.jimmy.uabcs.rxbibliouabcs.android;

import android.app.ProgressDialog;
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
import com.jimmy.uabcs.rxbibliouabcs.utils.Utils;
import com.jimmy.uabcs.rxbibliouabcs.utils.VolleySingleton;
import com.jimmy.uabcs.rxbibliouabcs.viewmodels.BookAdapterViewModel;
import com.jimmy.uabcs.rxbibliouabcs.viewmodels.LibraryViewModel;
import com.trello.rxlifecycle.components.support.RxFragment;

import rx.android.schedulers.AndroidSchedulers;

import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.DEFAULT_IMAGE;
import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.GSON;
import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.URL;

public class BookFragment extends RxFragment {


    private static final String ARG_PARAM1 = "param1";
    private LibraryViewModel mLibraryViewModel;
    private int id;
    private ProgressDialog mProgressDialog;
    public BookFragment() {

    }

    public static BookFragment newInstance(int param1) {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book, container, false);
        mLibraryViewModel = LibraryViewModel.getInstance();
        mLibraryViewModel.isLoadingObservable()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showLoadingDialog);
        mLibraryViewModel.book()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::DisplayBook);
        mLibraryViewModel.getBook(id).subscribe(h -> {
                },
                e -> Utils.showToast(getActivity(), e.getMessage())
        );
        return rootView;
    }

    public void DisplayBook(BookAdapterViewModel book) {
        if (getView() == null) return;
        TextView title = (TextView) getView().findViewById(R.id.title);
        title.setText(book.getName());
        String imaegPath = book.getImage();
        NetworkImageView img = (NetworkImageView) getView().findViewById(R.id.thumbnail);
        ImageLoader mImageLoader = VolleySingleton.getInstance().getImageLoader();
        if (imaegPath == null || imaegPath == "")
            img.setImageUrl(URL + DEFAULT_IMAGE, mImageLoader);
        else
            img.setImageUrl(URL + imaegPath, mImageLoader);

        TextView authors = (TextView) getView().findViewById(R.id.authors);
        authors.setText(book.getAuthor());

        TextView isbn = (TextView) getView().findViewById(R.id.isbn);
        isbn.setText(getString(R.string.isbn, book.getISBN()));

        TextView genres = (TextView) getView().findViewById(R.id.genre);
        genres.setText(book.getGenre());

        TextView publisher = (TextView) getView().findViewById(R.id.publisher);
        publisher.setText(book.getPublisherName());
    }

    public void showLoadingDialog(boolean isLoading) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle(getString(R.string.wait));
            mProgressDialog.setMessage(getString(R.string.loading));
        }
        if (isLoading)
            mProgressDialog.show();
        else
            mProgressDialog.dismiss();
    }
}
