package com.madalinaloghin.movieapp.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.movieapp.api.RequestManager;
import com.madalinaloghin.movieapp.api.response.ResponsePopularMovies;
import com.madalinaloghin.movieapp.api.response.ResponsePopularPeople;
import com.madalinaloghin.movieapp.api.response.ResponsePopularTvSeries;
import com.madalinaloghin.movieapp.ui.adapter.AdapterPopularMovieList;
import com.madalinaloghin.movieapp.ui.adapter.AdapterPopularPeopleList;
import com.madalinaloghin.movieapp.ui.adapter.AdapterPopularTvSeriesList;
import com.madalinaloghin.util.object.Movie;
import com.madalinaloghin.util.object.Person;
import com.madalinaloghin.util.object.TvSeries;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BottomNavigationBaseActivity {

    @BindView(R.id.rv_actors_popular)
    RecyclerView rvActorsPopular;

    @BindView(R.id.rv_movie_popular)
    RecyclerView rvMoviesPopular;

    @BindView(R.id.rv_tv_series_popular)
    RecyclerView rvTvSeriesPopular;


    private AdapterPopularMovieList adapterMovieList;
    private AdapterPopularPeopleList adapterPeopleList;
    private AdapterPopularTvSeriesList adapterTvSeriesList;


    private LinearLayoutManager mLayoutManager;
    private boolean mIsLoading = false;

    private int mCurrentPageMovies = 0;
    private int mCurrentPagePersons = 0;
    private int mCurrentPageTvSeries = 0;


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        setupRecycleViewMovies();
        setupRecycleViewMTvSeries();
        setupRecycleViewActors();

    }

    private void setupRecycleViewMovies() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvMoviesPopular.setLayoutManager(mLayoutManager);

        adapterMovieList = new AdapterPopularMovieList(new AdapterPopularMovieList.OnItemClickedListener() {
            @Override
            public void onItemClick(Movie movie) {

            }
        });

        rvMoviesPopular.setAdapter(adapterMovieList);
        rvMoviesPopular.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mIsLoading || dy <= 0) {
                    return;
                }
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 3) {
                    getPopularMovies();
                }
            }
        });
    }

    private void setupRecycleViewMTvSeries() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvTvSeriesPopular.setLayoutManager(mLayoutManager);
        adapterTvSeriesList = new AdapterPopularTvSeriesList(new AdapterPopularTvSeriesList.OnItemClickedListener() {
            @Override
            public void onItemClick(TvSeries tvSeries) {

            }
        });
        rvTvSeriesPopular.setAdapter(adapterTvSeriesList);
        rvTvSeriesPopular.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mIsLoading || dy <= 0) {
                    return;
                }
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 3) {
                    getPopularTvSeries();
                }
            }
        });


    }

    private void setupRecycleViewActors() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvActorsPopular.setLayoutManager(mLayoutManager);
        adapterPeopleList = new AdapterPopularPeopleList(new AdapterPopularPeopleList.OnItemClickedListener() {
            @Override
            public void onItemClick(Person person) {

            }
        });
        rvActorsPopular.setAdapter(adapterPeopleList);
        rvActorsPopular.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mIsLoading || dy <= 0) {
                    return;
                }
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 3) {
                    getPopularPersons();
                }
            }
        });

    }

    private void getPopularMovies() {
        mCurrentPageMovies++;
        RequestManager.getInstance(this).queryPopularMovies(
                mCurrentPageMovies,
                new Callback<ResponsePopularMovies>() {
                    @Override
                    public void onResponse(Call<ResponsePopularMovies> call, Response<ResponsePopularMovies> response) {
                        adapterMovieList.setItems(response.body().getResultsList());
                        mIsLoading = false;
                    }

                    @Override
                    public void onFailure(Call<ResponsePopularMovies> call, Throwable t) {
                        mIsLoading = false;
                    }
                }

        );
    }

    private void getPopularTvSeries() {
        mCurrentPageTvSeries++;
        RequestManager.getInstance(this).queryPopularTvSeries(
                mCurrentPageTvSeries,
                new Callback<ResponsePopularTvSeries>() {
                    @Override
                    public void onResponse(Call<ResponsePopularTvSeries> call, Response<ResponsePopularTvSeries> response) {
                        adapterTvSeriesList.setItems(response.body().getResultsList());
                        mIsLoading = false;
                    }

                    @Override
                    public void onFailure(Call<ResponsePopularTvSeries> call, Throwable t) {
                        mIsLoading = false;
                    }
                }
        );

    }

    private void getPopularPersons() {
        mCurrentPagePersons++;
        RequestManager.getInstance(this).queryPopularPeople(
                mCurrentPagePersons,
                new Callback<ResponsePopularPeople>() {
                    @Override
                    public void onResponse(Call<ResponsePopularPeople> call, Response<ResponsePopularPeople> response) {
                        adapterPeopleList.setItems(response.body().getResultsList());
                        mIsLoading = false;
                    }

                    @Override
                    public void onFailure(Call<ResponsePopularPeople> call, Throwable t) {
                        mIsLoading = false;
                    }
                }
        );
    }


    @Override
    protected BottomNavActivityType getType() {
        return BottomNavActivityType.HOME;
    }
}
