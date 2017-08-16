package com.madalinaloghin.movieapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.util.object.UserListDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by madalina.loghin on 8/7/2017.
 */

public class AdapterUserListDetails extends RecyclerView.Adapter<AdapterUserListDetails.ViewHolder> {

    private List<UserListDetail> mData;
    OnItemClickedListener mListener;

    public AdapterUserListDetails(OnItemClickedListener listener) {
        this.mListener = listener;
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

    public void setItems(ArrayList<UserListDetail> data) {
        if (mData == null) {
            mData = new ArrayList<>();
        } else {
            mData.clear();
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.iv_image_fav_rv)
        ImageView ivImage;

        @BindView(R.id.tv_title_favs)
        TextView tvTitle;

        private OnItemClickedListener listener;

        public ViewHolder(View itemView, OnItemClickedListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
        }

        public void bind(final UserListDetail userListDetail) {
            Glide.with(itemView.getContext()).load(userListDetail.getImageUrl()).into(ivImage);
            if (userListDetail.getTitle() != null) {
                tvTitle.setText(String.valueOf(userListDetail.getTitle()));
            } else {
                tvTitle.setText(String.valueOf(userListDetail.getTitleSeries()));
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(userListDetail);
                }
            });
        }
    }

    public interface OnItemClickedListener {
        void onItemClicked(UserListDetail userListDetail);
    }
}
