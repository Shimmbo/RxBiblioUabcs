package com.jimmy.uabcs.rxbibliouabcs.android;


import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jimmy.uabcs.rxbibliouabcs.R;
import com.jimmy.uabcs.rxbibliouabcs.adapter.BookAdapter;
import com.jimmy.uabcs.rxbibliouabcs.utils.Utils;
import com.jimmy.uabcs.rxbibliouabcs.viewmodels.BookAdapterViewModel;
import com.jimmy.uabcs.rxbibliouabcs.viewmodels.LibraryViewModel;
import com.trello.rxlifecycle.components.support.RxFragment;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BooksFragment extends RxFragment {
    private static final String BOOKS_PARAM = "books";
    private RecyclerView mRecyclerView;
    private BookAdapter mAdapter;
    private LibraryViewModel mLibraryViewModel;
    private TextView emptyView;
    private LinearLayoutManager layoutManager;

    public BooksFragment() {
    }

    public static BooksFragment newInstance(String param1) {
        BooksFragment fragment = new BooksFragment();
        Bundle args = new Bundle();
        args.putString(BOOKS_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_books, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.bookList);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);
        mAdapter = new BookAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mLibraryViewModel = LibraryViewModel.getInstance();
        mLibraryViewModel.books()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mAdapter::setBooks);
        mLibraryViewModel.book()
                .compose(bindToLifecycle())
                .observeOn(Schedulers.newThread())
                .subscribe(mAdapter::addBook);
        mLibraryViewModel.getBooks().subscribe(h -> {
                },
                e -> Utils.showToast(getActivity(), e.getMessage())
        );
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.base, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filter(newText);
                return true;
            }
        });
    }

    public void getBooks() {
        for(BookAdapterViewModel book : mLibraryViewModel.booksData()) {
            mLibraryViewModel.getBook(book.getId())
                    .subscribe(x -> {});
        }
    }

}
