package com.madalinaloghin.movieapp.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.movieapp.api.RequestManager;
import com.madalinaloghin.movieapp.api.response.ResponseListMovies;
import com.madalinaloghin.movieapp.ui.activity.MovieDetailsActivity;
import com.madalinaloghin.movieapp.ui.adapter.AdapterFavsMovies;
import com.madalinaloghin.util.Util;
import com.madalinaloghin.util.object.Movie;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSearchMovies extends Fragment {

    RecyclerView rvSearchMovies;
    public String query;
    private AdapterFavsMovies adapterMoviesSearch;
    private LinearLayoutManager mLayoutManagerMoviesSearch;
    private boolean mIsLoading = false;
    private int mCurrentPage = 0;

    public static final String QUERY = "query";

    public static FragmentSearchMovies newInstance(String query) {
        FragmentSearchMovies fragment = new FragmentSearchMovies();
        Bundle bundle = new Bundle();
        bundle.putString(QUERY, query);
        fragment.setArguments(bundle);
        return fragment;
    }


    public FragmentSearchMovies() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_movies, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvSearchMovies = getView().findViewById(R.id.rv_search_movies);
        query = getArguments().getString(QUERY);

        setupRecycleView();
        getSearchMovies();
    }

    void setupRecycleView() {
        mLayoutManagerMoviesSearch = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvSearchMovies.getContext(),
                mLayoutManagerMoviesSearch.getOrientation());
        rvSearchMovies.addItemDecoration(dividerItemDecoration);

        rvSearchMovies.setLayoutManager(mLayoutManagerMoviesSearch);

        adapterMoviesSearch = new AdapterFavsMovies(new AdapterFavsMovies.OnItemClickedListener() {
            @Override
            public void onItemClicked(Movie movie) {
                Intent intent = new Intent(getView().getContext(), MovieDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Util.MOVIE, movie);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rvSearchMovies.setAdapter(adapterMoviesSearch);

        rvSearchMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mIsLoading || dy < 0) {
                    return;
                }
                int visibleItemCount = mLayoutManagerMoviesSearch.getChildCount();
                int visibleTotalCount = mLayoutManagerMoviesSearch.getItemCount();
                int pastVisibleItems = mLayoutManagerMoviesSearch.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisibleItems) >= visibleTotalCount - 3) {
                    getSearchMovies();
                }
            }
        });

    }


    void getSearchMovies() {
        mIsLoading = true;
        mCurrentPage++;
        RequestManager.getInstance(this.getContext()).querySearchMovies(
                Util.API_KEY,
                query,
                mCurrentPage,
                new Callback<ResponseListMovies>() {
                    @Override
                    public void onResponse(Call<ResponseListMovies> call, Response<ResponseListMovies> response) {
                        if (mCurrentPage == 1) {
                            adapterMoviesSearch.setItems(response.body().getResultsList());
                        } else {
                            adapterMoviesSearch.addItems(response.body().getResultsList());
                        }
                        mIsLoading = false;
                    }

                    @Override
                    public void onFailure(Call<ResponseListMovies> call, Throwable t) {
                        mIsLoading = false;
                    }
                }

        );

    }

}
