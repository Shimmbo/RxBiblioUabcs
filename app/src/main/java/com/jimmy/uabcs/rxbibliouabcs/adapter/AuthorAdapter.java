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
import com.jimmy.uabcs.rxbibliouabcs.App;
import com.jimmy.uabcs.rxbibliouabcs.R;
import com.jimmy.uabcs.rxbibliouabcs.models.Author;
import com.jimmy.uabcs.rxbibliouabcs.utils.Utils;
import com.jimmy.uabcs.rxbibliouabcs.utils.VolleySingleton;
import com.jimmy.uabcs.rxbibliouabcs.android.BooksFragment;
import com.jimmy.uabcs.rxbibliouabcs.viewmodels.AuthorAdapterViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.GSON;
import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.URL;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.ViewHolder> {
    private List<AuthorAdapterViewModel> mItems;
    private List<AuthorAdapterViewModel> itemsCopy;
    private Context context;

    public AuthorAdapter(Context context) {
        super();
        mItems = new ArrayList<AuthorAdapterViewModel>();
        itemsCopy = new ArrayList<AuthorAdapterViewModel>();
        this.context = context;
    }

    public void setAuthors(List<AuthorAdapterViewModel> authors) {
        mItems = authors;
        itemsCopy = authors;
        notifyDataSetChanged();
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
            ArrayList<AuthorAdapterViewModel> result = new ArrayList<>();
            text = text.toLowerCase();
            for (AuthorAdapterViewModel item : itemsCopy) {
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
                .inflate(R.layout.authors, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        AuthorAdapterViewModel mAuthor = mItems.get(i);

        viewHolder.name.setText(mAuthor.getName());
        String message = App.getContext().getString(R.string.books, mAuthor.getBook().size());
        viewHolder.counts.setText(message);
        ImageLoader mImageLoader = VolleySingleton.getInstance().getImageLoader();
        viewHolder.mImageView.setDefaultImageResId(R.drawable.no_author_image);
        String imagePath = mAuthor.getImage();
        if (imagePath != null && imagePath != "")
            viewHolder.mImageView.setImageUrl(URL + imagePath, mImageLoader);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView counts;
        public NetworkImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.authorName);
            counts = (TextView) itemView.findViewById(R.id.booksCount);
            mImageView = (NetworkImageView) itemView.findViewById(R.id.author_photo);
            name.setOnClickListener(this);
            counts.setOnClickListener(this);
            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition();
            String jsonBooks = GSON.toJson(mItems.get(pos).getBook());
            Fragment booksFragment = BooksFragment.newInstance(jsonBooks);
            FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
            Utils.startFragment(manager, booksFragment);
        }
    }
}

