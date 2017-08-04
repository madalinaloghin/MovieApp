package com.madalinaloghin.movieapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.util.object.TvSeries;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by madalina.loghin on 8/4/2017.
 */

public class AdapterFavsTvSeries extends RecyclerView.Adapter<AdapterFavsTvSeries.ViewHolder> {

    private List<TvSeries> mData;

    private OnItemClickedListener mListener;


    public AdapterFavsTvSeries(OnItemClickedListener listener) {
        mListener = listener;
    }

    public void setItems(@NonNull ArrayList<TvSeries> data) {
        if (mData == null) {
            mData = new ArrayList<>();
        } else {
            mData.clear();
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addItems(@NonNull ArrayList<TvSeries> data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        int count = mData.size();
        mData.addAll(data);
        notifyItemRangeInserted(count, data.size());
    }


    @Override
    public AdapterFavsTvSeries.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favs_rv_movie_series, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(AdapterFavsTvSeries.ViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
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

        public void bind(final TvSeries tvSeries) {
            Glide.with(itemView.getContext()).load(tvSeries.getImageUrl()).into(favImage);
            titleFavs.setText(tvSeries.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(tvSeries);
                }
            });
        }
    }


    public interface OnItemClickedListener {
        void onItemClicked(TvSeries tvseries);
    }
}
