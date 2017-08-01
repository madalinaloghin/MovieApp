package com.madalinaloghin.movieapp.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.movieapp.ui.adapter.AdapterPopularMovieList;

import butterknife.BindView;

public class HomeActivity extends BottomNavigationBaseActivity {


    @BindView(R.id.rv_actors_popular)
    RecyclerView rvActorsPopular;

    @BindView(R.id.rv_movie_popular)
    RecyclerView rvMoviesPopular;

    @BindView(R.id.rv_tv_series_popular)
    RecyclerView rvTvSeriesPopular;


    private AdapterPopularMovieList mAdapter;
    private LinearLayoutManager mLayoutManager;




    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_home);

    }


    @Override
    protected BottomNavActivityType getType() {
        return BottomNavActivityType.HOME;
    }
}
