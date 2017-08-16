package com.madalinaloghin.movieapp.ui.activity;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.movieapp.api.RequestManager;
import com.madalinaloghin.movieapp.api.response.ResponseListMovies;
import com.madalinaloghin.movieapp.api.response.ResponseListPeople;
import com.madalinaloghin.movieapp.api.response.ResponseListTvSeries;
import com.madalinaloghin.movieapp.ui.adapter.AdapterPopularMovieList;
import com.madalinaloghin.movieapp.ui.adapter.AdapterPopularPeopleList;
import com.madalinaloghin.movieapp.ui.adapter.AdapterPopularTvSeriesList;
import com.madalinaloghin.util.Util;
import com.madalinaloghin.util.object.Movie;
import com.madalinaloghin.util.object.Person;
import com.madalinaloghin.util.object.TvSeries;
import com.madalinaloghin.util.object.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BottomNavigationBaseActivity {

    RecyclerView rvActorsPopular;
    RecyclerView rvMoviesPopular;
    RecyclerView rvTvSeriesPopular;


    private AdapterPopularMovieList adapterMovieList;
    private AdapterPopularPeopleList adapterPeopleList;
    private AdapterPopularTvSeriesList adapterTvSeriesList;


    private LinearLayoutManager mLayoutManagerMovies;
    private LinearLayoutManager mLayoutManagerTvSeries;
    private LinearLayoutManager mLayoutManagerPersons;

    private boolean mIsLoadingMovies = false;
    private boolean mIsLoadingTvSeries = false;
    private boolean mIsLoadingPerson = false;

    private int mCurrentPageMovies = 0;
    private int mCurrentPagePersons = 0;
    private int mCurrentPageTvSeries = 0;


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_home);

        loadInfos();
    }

    public void loadInfos() {
        rvActorsPopular = (RecyclerView) findViewById(R.id.rv_actors_popular);
        rvMoviesPopular = (RecyclerView) findViewById(R.id.rv_movie_popular);
        rvTvSeriesPopular = (RecyclerView) findViewById(R.id.rv_tv_series_popular);

        setupRecycleViewMovies();
        setupRecycleViewMTvSeries();
        setupRecycleViewActors();

        getPopularMovies();
        getPopularTvSeries();
        getPopularPersons();

        getCurrentUser();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_option, menu);

        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                handleIntent(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }


    private void handleIntent(String query) {
        Intent i = new Intent(getBaseContext(), SearchResultsActivity.class);
        i.putExtra("query", query);
        startActivity(i);
    }


    private void setupRecycleViewMovies() {
        mLayoutManagerMovies = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvMoviesPopular.setLayoutManager(mLayoutManagerMovies);
        adapterMovieList = new AdapterPopularMovieList(new AdapterPopularMovieList.OnItemClickedListener() {
            @Override
            public void onItemClick(Movie movie) {
                Intent intent = new Intent(HomeActivity.this, MovieDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Util.MOVIE, movie);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        rvMoviesPopular.setAdapter(adapterMovieList);

        rvMoviesPopular.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mIsLoadingMovies || dx <= 0) {
                    return;
                }
                int visibleItemCount = mLayoutManagerMovies.getChildCount();
                int totalItemCount = mLayoutManagerMovies.getItemCount();
                int pastVisiblesItems = mLayoutManagerMovies.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 3) {
                    getPopularMovies();
                }
            }
        });
    }

    private void setupRecycleViewMTvSeries() {
        mLayoutManagerTvSeries = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvTvSeriesPopular.setLayoutManager(mLayoutManagerTvSeries);
        adapterTvSeriesList = new AdapterPopularTvSeriesList(new AdapterPopularTvSeriesList.OnItemClickedListener() {
            @Override
            public void onItemClick(TvSeries tvSeries) {
                Intent intent = new Intent(HomeActivity.this, TvSeriesDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Util.TV_SERIES, tvSeries);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rvTvSeriesPopular.setAdapter(adapterTvSeriesList);
        rvTvSeriesPopular.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mIsLoadingTvSeries || dx <= 0) {
                    return;
                }
                int visibleItemCount = mLayoutManagerTvSeries.getChildCount();
                int totalItemCount = mLayoutManagerTvSeries.getItemCount();
                int pastVisiblesItems = mLayoutManagerTvSeries.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 3) {
                    getPopularTvSeries();
                }
            }
        });
    }


    private void setupRecycleViewActors() {
        mLayoutManagerPersons = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvActorsPopular.setLayoutManager(mLayoutManagerPersons);
        adapterPeopleList = new AdapterPopularPeopleList(new AdapterPopularPeopleList.OnItemClickedListener() {
            @Override
            public void onItemClick(Person person) {
                Intent intent = new Intent(HomeActivity.this, PersonDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Util.PERSON, person);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rvActorsPopular.setAdapter(adapterPeopleList);
        rvActorsPopular.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mIsLoadingPerson || dx <= 0) {
                    return;
                }
                int visibleItemCount = mLayoutManagerPersons.getChildCount();
                int totalItemCount = mLayoutManagerPersons.getItemCount();
                int pastVisiblesItems = mLayoutManagerPersons.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 3) {
                    getPopularPersons();
                }
            }
        });

    }

    private void getPopularMovies() {
        mCurrentPageMovies++;
        mIsLoadingMovies = true;
        RequestManager.getInstance(this).queryPopularMovies(
                Util.API_KEY,
                mCurrentPageMovies,
                new Callback<ResponseListMovies>() {
                    @Override
                    public void onResponse(Call<ResponseListMovies> call, Response<ResponseListMovies> response) {
                        if (mCurrentPageMovies == 1) {
                            adapterMovieList.setItems(response.body().getResultsList());
                        } else {
                            adapterMovieList.addItems(response.body().getResultsList());
                        }
                        mIsLoadingMovies = false;
                    }

                    @Override
                    public void onFailure(Call<ResponseListMovies> call, Throwable t) {
                        mIsLoadingMovies = false;
                    }
                }
        );
    }

    private void getPopularTvSeries() {
        mCurrentPageTvSeries++;
        mIsLoadingTvSeries = true;
        RequestManager.getInstance(this).queryPopularTvSeries(
                Util.API_KEY,
                mCurrentPageTvSeries,
                new Callback<ResponseListTvSeries>() {
                    @Override
                    public void onResponse(Call<ResponseListTvSeries> call, Response<ResponseListTvSeries> response) {
                        if (mCurrentPageTvSeries == 1) {
                            adapterTvSeriesList.setItems(response.body().getResultsList());
                        } else {
                            adapterTvSeriesList.addItems(response.body().getResultsList());
                        }
                        mIsLoadingTvSeries = false;
                    }

                    @Override
                    public void onFailure(Call<ResponseListTvSeries> call, Throwable t) {
                        mIsLoadingTvSeries = false;
                    }
                }
        );

    }

    private void getPopularPersons() {
        mCurrentPagePersons++;
        mIsLoadingPerson = true;
        RequestManager.getInstance(this).queryPopularPeople(
                Util.API_KEY,
                mCurrentPagePersons,
                new Callback<ResponseListPeople>() {
                    @Override
                    public void onResponse(Call<ResponseListPeople> call, Response<ResponseListPeople> response) {
                        if (mCurrentPagePersons == 1) {
                            adapterPeopleList.setItems(response.body().getResultsList());
                        } else {
                            adapterPeopleList.addItems(response.body().getResultsList());
                        }
                        mIsLoadingPerson = false;
                    }

                    @Override
                    public void onFailure(Call<ResponseListPeople> call, Throwable t) {
                        mIsLoadingPerson = false;
                    }
                }
        );
    }


    private void getCurrentUser() {
        RequestManager.getInstance(this).queryUserAccount(
                Util.API_KEY,
                Util.SESSION_ID,
                new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Util.currentUser = response.body();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                }
        );
    }


    @Override
    protected BottomNavActivityType getType() {
        return BottomNavActivityType.HOME;
    }
}
