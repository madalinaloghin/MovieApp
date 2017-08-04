package com.madalinaloghin.movieapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.util.object.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by madalina.loghin on 8/4/2017.
 */

public class AdapterFavsMovies extends RecyclerView.Adapter<AdapterFavsMovies.ViewHolder> {

    private List<Movie> mData;
    private OnItemClickedListener mListener;

    public AdapterFavsMovies(OnItemClickedListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favs_rv_movie_series, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    public void setItems(ArrayList<Movie> data) {
        if (mData == null) {
            mData = new ArrayList<>();
        } else {
            mData.clear();
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<Movie> data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        int count = mData.size();
        mData.addAll(data);
        notifyItemRangeInserted(count, mData.size());
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image_fav_rv)
        ImageView favImage;

        @BindView(R.id.tv_title_favs)
        TextView titleFavs;

        private OnItemClickedListener listener;

        public ViewHolder(View itemView, OnItemClickedListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
        }

        public void bind(final Movie movie) {
            Glide.with(itemView.getContext()).load(movie.getImageUrl()).into(favImage);
            titleFavs.setText(movie.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(movie);
                }
            });

        }
    }

    public interface OnItemClickedListener {
        void onItemClicked(Movie movie);
    }


}
