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

    public AdapterPersonImages() {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person_pictures, parent, false);
        return new ViewHolder(view);
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

        @BindView(R.id.iv_images_person)
        ImageView imgPerson;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Images image) {
            Glide.with(itemView.getContext()).load(image.getFilePath()).into(imgPerson);
        }
    }

}
