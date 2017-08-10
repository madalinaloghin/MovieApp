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
public class FragmentSearchPerson extends Fragment {

    public static FragmentSearchPerson newInstance() {
        FragmentSearchPerson fragment = new FragmentSearchPerson();
        return fragment;
    }


    public FragmentSearchPerson() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_person, container, false);
    }

}
