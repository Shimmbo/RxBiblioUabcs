package com.jimmy.uabcs.rxbibliouabcs.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
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
import com.jimmy.uabcs.rxbibliouabcs.android.BookFragment;
import com.jimmy.uabcs.rxbibliouabcs.viewmodels.BookAdapterViewModel;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.DEFAULT_IMAGE;
import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.GSON;
import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.URL;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<BookAdapterViewModel> mItems;
    private List<BookAdapterViewModel> itemsCopy;
    private Context context;

    public BookAdapter(Context context) {
        super();
        mItems = new ArrayList<BookAdapterViewModel>();
        itemsCopy = new ArrayList<BookAdapterViewModel>();
        this.context = context;
    }

    public void setBooks(List<BookAdapterViewModel> books) {
        mItems = books;
        itemsCopy = books;
        notifyDataSetChanged();
    }
    public void addBook(BookAdapterViewModel book) {
        mItems.add(book);
        itemsCopy.add(book);
        notifyDataSetChanged();;
    }
    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public void filter(String text) {
        if (text.isEmpty()) {
            mItems.clear();
            mItems.addAll(itemsCopy);
        } else {
            ArrayList<BookAdapterViewModel> result = new ArrayList<>();
            text = text.toLowerCase();
            for (BookAdapterViewModel item : itemsCopy) {
                if (item.getName().toLowerCase().contains(text)) {
                    result.add(item);
                }
            }
            mItems.clear();
            mItems.addAll(result);
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.books, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        BookAdapterViewModel mBook = mItems.get(i);
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = formatter.format(mBook.getYear());
        viewHolder.name.setText(mBook.getName());

        String genre = mBook.getGenre() == null || mBook.getGenre() == "" ?
                context.getString(R.string.no_genre) : mBook.getGenre();

        viewHolder.genres.setText(context.getString(R.string.genre_param, genre));

        String author = mBook.getAuthor() == null || mBook.getAuthor() == "" ?
                context.getString(R.string.annonymous) : mBook.getAuthor();

        viewHolder.authors.setText(context.getString(R.string.author_param, author));
        ImageLoader mImageLoader = VolleySingleton.getInstance().getImageLoader();
        viewHolder.mImageView.setDefaultImageResId(R.drawable.no_book_image);

        String imagePath = mBook.getImage();
        if (imagePath != null && imagePath != "")
            viewHolder.mImageView.setImageUrl(URL + imagePath, mImageLoader);
        else
            viewHolder.mImageView.setImageUrl(URL + DEFAULT_IMAGE, mImageLoader);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView genres;
        public TextView authors;
        public NetworkImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.bookName);
            genres = (TextView) itemView.findViewById(R.id.genres);
            authors = (TextView) itemView.findViewById(R.id.authors);
            mImageView = (NetworkImageView) itemView.findViewById(R.id.book_photo);
            genres.setOnClickListener(this);
            name.setOnClickListener(this);
            authors.setOnClickListener(this);
            mImageView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition();
            int id = mItems.get(pos).getId();
            Fragment bookFragment = BookFragment.newInstance(id);
            FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
            Utils.startFragment(manager, bookFragment);
        }
    }
}
