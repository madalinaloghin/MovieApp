package com.madalinaloghin.movieapp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madalinaloghin.movieapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSearchTvSeries extends Fragment {


    public FragmentSearchTvSeries() {
        // Required empty public constructor
    }

    public static FragmentSearchTvSeries newInstance() {
        FragmentSearchTvSeries fragment = new FragmentSearchTvSeries();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_tv_series, container, false);
    }

}
