package com.madalinaloghin.movieapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.util.object.UserList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by madalina.loghin on 8/21/2017.
 */

public class AdapterListsAddToList extends RecyclerView.Adapter<AdapterListsAddToList.ViewHolder> {
    private List<UserList> mData;
    int itemId;

    public AdapterListsAddToList(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_to_list_checkbox, parent, false);
        return new ViewHolder(view);
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

        @BindView(R.id.cb_check_box_add_to_list_item)
        CheckBox mCheckBox;

        public boolean isInList = false;


        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    // Toast.makeText(itemView.getContext(), String.valueOf(b), Toast.LENGTH_SHORT).show();

                }
            });

        }

        public void bind(final UserList userList) {
            mCheckBox.setText(userList.getName().toString());
        }
    }

}
