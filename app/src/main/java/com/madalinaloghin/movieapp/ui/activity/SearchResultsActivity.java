package com.madalinaloghin.movieapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.madalinaloghin.movieapp.R;

public class SearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        String query = getIntent().getStringExtra("query");


    }

}
