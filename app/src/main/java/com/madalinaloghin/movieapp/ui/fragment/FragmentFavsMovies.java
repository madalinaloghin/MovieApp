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
import com.madalinaloghin.movieapp.api.response.ResponseListFavoriteMovies;
import com.madalinaloghin.movieapp.ui.activity.MovieDetailsActivity;
import com.madalinaloghin.movieapp.ui.adapter.AdapterFavsMovies;
import com.madalinaloghin.util.Util;
import com.madalinaloghin.util.object.Movie;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFavsMovies extends Fragment {

    RecyclerView rvFavsMovies;
    private AdapterFavsMovies adapterFavsList;
    private LinearLayoutManager mLayoutManagerMovies;
    private boolean mIsLoading = false;
    private int mCurrentPage = 0;


    public FragmentFavsMovies() {
    }

    public static FragmentFavsMovies newInstance() {
        FragmentFavsMovies fragmentFavsMovies = new FragmentFavsMovies();
        return fragmentFavsMovies;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favs_movies, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvFavsMovies = getView().findViewById(R.id.rv_fragment_favs_movies);

        setupRecycleView();
        getFavsMovies();

    }

    void setupRecycleView() {
        mLayoutManagerMovies = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvFavsMovies.getContext(),
                mLayoutManagerMovies.getOrientation());
        rvFavsMovies.addItemDecoration(dividerItemDecoration);
        rvFavsMovies.setLayoutManager(mLayoutManagerMovies);


        adapterFavsList = new AdapterFavsMovies(new AdapterFavsMovies.OnItemClickedListener() {
            @Override
            public void onItemClicked(Movie movie) {
                Intent intent = new Intent(getView().getContext(), MovieDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Util.MOVIE, movie);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        rvFavsMovies.setAdapter(adapterFavsList);

        rvFavsMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mIsLoading || dy < 0) {
                    return;
                }
                int visibleItemCount = mLayoutManagerMovies.getChildCount();
                int visibleTotalCount = mLayoutManagerMovies.getItemCount();
                int pastVisibleItems = mLayoutManagerMovies.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisibleItems) >= visibleTotalCount - 3) {
                    getFavsMovies();
                }
            }
        });

    }

    private void getFavsMovies() {
        mCurrentPage++;
        mIsLoading = true;
        RequestManager.getInstance(this.getContext()).queryFavoriteMovies(
                Util.API_KEY,
                Util.SESSION_ID,
                Util.currentUser.getId(),
                mCurrentPage,
                new Callback<ResponseListFavoriteMovies>() {
                    @Override
                    public void onResponse(Call<ResponseListFavoriteMovies> call, Response<ResponseListFavoriteMovies> response) {
                        if (mCurrentPage == 1) {
                            adapterFavsList.setItems(response.body().getResultsList());
                        } else {
                            adapterFavsList.addItems(response.body().getResultsList());
                        }
                        mIsLoading = false;
                    }

                    @Override
                    public void onFailure(Call<ResponseListFavoriteMovies> call, Throwable t) {
                        mIsLoading = false;
                    }
                }
        );
    }
}
