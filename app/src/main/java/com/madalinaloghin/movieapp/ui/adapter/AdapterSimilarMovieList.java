package com.madalinaloghin.movieapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.util.object.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by madalina.loghin on 8/1/2017.
 */

public class AdapterSimilarMovieList extends RecyclerView.Adapter<AdapterSimilarMovieList.ViewHolder> {


    private List<Movie> mDataMovies;
    private OnItemClickedListener mListener;

    public AdapterSimilarMovieList(OnItemClickedListener listener) {
        mListener = listener;
    }

    public void setItems(@NonNull List<Movie> data) {
        if (mDataMovies == null) {
            mDataMovies = new ArrayList<>();
        } else {
            mDataMovies.clear();
        }
        mDataMovies.addAll(data);
        notifyDataSetChanged();
    }

    public void addItems(@NonNull final List<Movie> data) {
        if (mDataMovies == null) {
            mDataMovies = new ArrayList<>();
        }
        int count = mDataMovies.size();
        mDataMovies.addAll(data);
        notifyItemRangeInserted(count, data.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_similars, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mDataMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataMovies == null ? 0 : mDataMovies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image_similar)
        ImageView image;

        private OnItemClickedListener mListener;

        public ViewHolder(View itemView, OnItemClickedListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mListener = listener;
        }

        public void bind(final Movie movie) {
            Glide.with(itemView.getContext()).load(movie.getPosterUrl()).into(image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(movie);
                }
            });

        }
    }

    public interface OnItemClickedListener {
        void onItemClick(Movie movie);
    }
}
