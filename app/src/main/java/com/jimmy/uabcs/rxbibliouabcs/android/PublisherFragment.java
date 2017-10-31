package com.jimmy.uabcs.rxbibliouabcs.android;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.jimmy.uabcs.rxbibliouabcs.R;
import com.jimmy.uabcs.rxbibliouabcs.adapter.BookAdapter;
import com.jimmy.uabcs.rxbibliouabcs.models.Book;
import com.jimmy.uabcs.rxbibliouabcs.utils.Utils;
import com.jimmy.uabcs.rxbibliouabcs.utils.VolleySingleton;
import com.jimmy.uabcs.rxbibliouabcs.viewmodels.AuthorAdapterViewModel;
import com.jimmy.uabcs.rxbibliouabcs.viewmodels.BookAdapterViewModel;
import com.jimmy.uabcs.rxbibliouabcs.viewmodels.LibraryViewModel;
import com.jimmy.uabcs.rxbibliouabcs.viewmodels.PublisherAdapterViewModel;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;

import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.DEFAULT_IMAGE;
import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.GSON;
import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.URL;

public class PublisherFragment extends RxFragment {


    private static final String ARG_PARAM1 = "PUBLISHER_ID";
    private RecyclerView mRecyclerView;
    private BookAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private LibraryViewModel mLibraryViewModel;
    private TextView emptyView;
    private int id;
    private ProgressDialog mProgressDialog;
    public PublisherFragment() {

    }

    public static PublisherFragment newInstance(int param1) {
        PublisherFragment fragment = new PublisherFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_publisher2, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.bookList);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);
        mAdapter = new BookAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mLibraryViewModel = LibraryViewModel.getInstance();
        mLibraryViewModel.isLoadingObservable()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showLoadingDialog);
        mLibraryViewModel.publisher()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::DisplayPublisher);
        mLibraryViewModel.getPublisher(id).subscribe(h -> {
                },
                e -> Utils.showToast(getActivity(), e.getMessage())
        );
        return rootView;
    }

    public void DisplayPublisher(PublisherAdapterViewModel publisher) {
        if (getView() == null) return;

        String imaegPath = publisher.getImage();
        NetworkImageView img = (NetworkImageView) getView().findViewById(R.id.thumbnail);
        img.setDefaultImageResId(R.drawable.no_publisher_image);
        ImageLoader mImageLoader = VolleySingleton.getInstance().getImageLoader();
        if (imaegPath == null || imaegPath == "")
            img.setImageUrl(URL + DEFAULT_IMAGE, mImageLoader);
        else
            img.setImageUrl(URL + imaegPath, mImageLoader);

        TextView title = (TextView) getView().findViewById(R.id.title);
        title.setText(publisher.getName());

        TextView totalbooks = (TextView) getView().findViewById(R.id.totalbooks);
        totalbooks.setText(getString(R.string.books, publisher.getBook().size()));
        ArrayList<BookAdapterViewModel> books = new ArrayList<>();
        for (Book book : publisher.getBook()) {
            books.add(new BookAdapterViewModel(book));
        }
        mAdapter.setBooks(books);
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
