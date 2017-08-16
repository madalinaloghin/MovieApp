package com.madalinaloghin.movieapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.util.object.TvSeries;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by madalina.loghin on 8/1/2017.
 */

public class AdapterSimilarTvSeriesList extends RecyclerView.Adapter<AdapterSimilarTvSeriesList.ViewHolder> {

    private List<TvSeries> mDataTvSeries;
    private OnItemClickedListener mListener;


    public AdapterSimilarTvSeriesList(OnItemClickedListener listener) {
        mListener = listener;
    }

    public void setItems(@NonNull List<TvSeries> data) {
        if (mDataTvSeries == null) {
            mDataTvSeries = new ArrayList<>();
        } else{
            mDataTvSeries.clear();
        }
        mDataTvSeries.addAll(data);
        notifyDataSetChanged();
    }

    public void addItems(@NonNull final List<TvSeries> data) {
        if (mDataTvSeries == null) {
            mDataTvSeries = new ArrayList<>();
        }
        int count = mDataTvSeries.size();
        mDataTvSeries.addAll(data);
        notifyItemRangeInserted(count, data.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_similars, parent, false);
        return new ViewHolder(view, mListener);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mDataTvSeries.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataTvSeries == null ? 0 : mDataTvSeries.size();
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

        public void bind(final TvSeries tvseries) {
            Glide.with(itemView.getContext()).load(tvseries.getPosterUrl()).into(image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(tvseries);
                }
            });

        }
    }

    public interface OnItemClickedListener {
        void onItemClick(TvSeries tvSeries);
    }

}
