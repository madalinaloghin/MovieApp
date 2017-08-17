package com.madalinaloghin.movieapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.util.object.Person;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by madalina.loghin on 8/17/2017.
 */

public class AdapterSearchPerson extends RecyclerView.Adapter<AdapterSearchPerson.ViewHolder> {

    private List<Person> mData;
    private OnItemClickedListener mListener;

    public AdapterSearchPerson(OnItemClickedListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_search_person, parent, false);
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


    public void setItems(ArrayList<Person> data) {
        if (mData == null) {
            mData = new ArrayList<>();
        } else {
            mData.clear();
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<Person> data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        int count = mData.size();
        mData.addAll(data);
        notifyItemRangeInserted(count, mData.size());
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image_search_person)
        ImageView personImage;

        @BindView(R.id.tv_title_search_person)
        TextView namePerson;

        private OnItemClickedListener listener;

        public ViewHolder(View itemView, OnItemClickedListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
        }

        public void bind(final Person person) {
            Glide.with(itemView.getContext()).load(person.getImagePath()).into(personImage);
            namePerson.setText(person.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(person);
                }
            });

        }
    }

    public interface OnItemClickedListener {
        void onItemClicked(Person person);
    }

}
