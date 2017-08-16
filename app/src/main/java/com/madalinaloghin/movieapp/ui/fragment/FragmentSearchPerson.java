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

    public static final String QUERY = "query";

    public static FragmentSearchPerson newInstance(String query) {
        FragmentSearchPerson fragment = new FragmentSearchPerson();
        Bundle bundle = new Bundle();
        bundle.putString(QUERY, query);
        fragment.setArguments(bundle);
        return fragment;
    }


    public FragmentSearchPerson() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_person, container, false);
    }

}
