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
public class FragmentSearchMovies extends Fragment {


    public static FragmentSearchMovies newInstance() {
        FragmentSearchMovies fragment = new FragmentSearchMovies();
        return fragment;
    }


    public FragmentSearchMovies() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search_movies, container, false);
    }

}
