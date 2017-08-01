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
import com.madalinaloghin.util.object.Movie;
import com.madalinaloghin.util.object.Person;
import com.madalinaloghin.util.object.TvSeries;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by madalina.loghin on 8/1/2017.
 */

public class AdapterPopularPeopleList extends RecyclerView.Adapter<AdapterPopularPeopleList.ViewHolder> {


    private List<Person> mDataPerson;
    private OnItemClickedListener mListener;

    public AdapterPopularPeopleList(OnItemClickedListener listener) {
        mListener = listener;
    }


    public void setItems(@NonNull List<Person> data) {
        if (mDataPerson == null) {
            mDataPerson = new ArrayList<>();
        }
        int count = mDataPerson.size();
        mDataPerson.addAll(data);
        notifyItemRangeInserted(count, data.size());
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_tv_show_popular, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mDataPerson.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataPerson == null ? 0 : mDataPerson.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image_popular)
        ImageView image;

        @BindView(R.id.tv_title_name)
        TextView title;

        private OnItemClickedListener mListener;

        public ViewHolder(View itemView, OnItemClickedListener listener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.mListener = listener;
        }

        public void bind(final Person person) {
            Glide.with(itemView.getContext()).load(person.getImagePath()).into(image);
            title.setText(person.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(person);
                }
            });

        }

    }

    public interface OnItemClickedListener {
        void onItemClick(Person person);
    }
}
