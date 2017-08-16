package com.madalinaloghin.movieapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.util.object.Images;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by madalina.loghin on 8/11/2017.
 */


public class AdapterPersonImages extends RecyclerView.Adapter<AdapterPersonImages.ViewHolder> {

    private List<Images> mDataImages;
    private OnItemClickedListener mListener;

    public AdapterPersonImages(OnItemClickedListener listener) {
        mListener = listener;
    }

    public void setItems(@NonNull List<Images> data) {
        if (mDataImages == null) {
            mDataImages = new ArrayList<>();
        } else {
            mDataImages.clear();
        }
        mDataImages.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_similars, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mDataImages.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataImages == null ? 0 : mDataImages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image_similar)
        ImageView imgPerson;

        private OnItemClickedListener mListener;

        public ViewHolder(View itemView, OnItemClickedListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mListener = listener;
        }

        public void bind(final Images image) {
            Glide.with(itemView.getContext()).load(image.getFilePath()).into(imgPerson);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(image);
                }
            });

        }
    }

    public interface OnItemClickedListener {
        void onItemClick(Images image);
    }


}
