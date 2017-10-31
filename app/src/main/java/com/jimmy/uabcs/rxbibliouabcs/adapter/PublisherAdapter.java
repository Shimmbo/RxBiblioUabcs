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
import com.jimmy.uabcs.rxbibliouabcs.android.PublisherFragment;
import com.jimmy.uabcs.rxbibliouabcs.models.Publisher;
import com.jimmy.uabcs.rxbibliouabcs.utils.Utils;
import com.jimmy.uabcs.rxbibliouabcs.utils.VolleySingleton;
import com.jimmy.uabcs.rxbibliouabcs.android.BooksFragment;
import com.jimmy.uabcs.rxbibliouabcs.viewmodels.PublisherAdapterViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.GSON;
import static com.jimmy.uabcs.rxbibliouabcs.utils.Constants.URL;

public class PublisherAdapter extends RecyclerView.Adapter<PublisherAdapter.ViewHolder> {
    private List<PublisherAdapterViewModel> mItems;
    private Context context;
    private List<PublisherAdapterViewModel> itemsCopy;

    public PublisherAdapter(Context context) {
        super();
        mItems = new ArrayList<PublisherAdapterViewModel>();
        itemsCopy = new ArrayList<PublisherAdapterViewModel>();
        this.context = context;
    }

    public void setPublishers(List<PublisherAdapterViewModel> publishers) {
        mItems = publishers;
        itemsCopy = publishers;
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
            ArrayList<PublisherAdapterViewModel> result = new ArrayList<>();
            text = text.toLowerCase();
            for (PublisherAdapterViewModel item : itemsCopy) {
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
                .inflate(R.layout.publishers, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        PublisherAdapterViewModel mPublisher = mItems.get(i);
        viewHolder.name.setText(mPublisher.getName());
        String message = App.getContext().getString(R.string.books, mPublisher.getBook().size());
        viewHolder.counts.setText(message);

        ImageLoader mImageLoader = VolleySingleton.getInstance().getImageLoader();
        viewHolder.mImageView.setDefaultImageResId(R.drawable.no_publisher_image);
        String imagePath = mPublisher.getImage();
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
            name = (TextView) itemView.findViewById(R.id.publisherName);
            name.setOnClickListener(this);

            counts = (TextView) itemView.findViewById(R.id.publisherBooksCount);
            counts.setOnClickListener(this);

            mImageView = (NetworkImageView) itemView.findViewById(R.id.publisher_photo);
            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition();
            Fragment publisherFragment = PublisherFragment.newInstance(mItems.get(pos).getIdAuthor());
            FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
            Utils.startFragment(manager, publisherFragment);
        }
    }
}
