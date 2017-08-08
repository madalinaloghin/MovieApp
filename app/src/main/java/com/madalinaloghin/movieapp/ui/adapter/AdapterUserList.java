package com.madalinaloghin.movieapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.util.object.UserList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by madalina.loghin on 8/7/2017.
 */

public class AdapterUserList extends RecyclerView.Adapter<AdapterUserList.ViewHolder> {

    private List<UserList> mData;
    OnItemClickedListener listener;

    public AdapterUserList(OnItemClickedListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lists, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setItems(ArrayList<UserList> data) {
        if (mData == null) {
            mData = new ArrayList<>();
        } else {
            mData.clear();
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<UserList> data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        int count = mData.size();
        mData.addAll(data);
        notifyItemRangeInserted(count, mData.size());
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title_lists)
        TextView tvTitle;

        @BindView(R.id.tv_item_nr_lists)
        TextView tvItemsNr;


        private OnItemClickedListener listener;

        public ViewHolder(View itemView, OnItemClickedListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
        }

        public void bind(final UserList userList) {
            if (userList.getItems() == 1) {
                tvItemsNr.setText(String.valueOf(userList.getItems()) + " item ");
            } else {
                tvItemsNr.setText(String.valueOf(userList.getItems()) + " items ");
            }
            tvTitle.setText(userList.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicke(userList);
                }
            });

        }
    }

    public interface OnItemClickedListener {
        void onItemClicke(UserList userList);
    }
}
