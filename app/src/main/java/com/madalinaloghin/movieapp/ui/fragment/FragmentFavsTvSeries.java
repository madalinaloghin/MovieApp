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
import com.madalinaloghin.movieapp.api.response.ResponseListFavoriteTvSeries;
import com.madalinaloghin.movieapp.ui.activity.MovieTvSeriesDetailsActivity;
import com.madalinaloghin.movieapp.ui.adapter.AdapterFavsTvSeries;
import com.madalinaloghin.util.Util;
import com.madalinaloghin.util.object.Categories;
import com.madalinaloghin.util.object.TvSeries;
import com.madalinaloghin.util.object.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFavsTvSeries extends Fragment {

    RecyclerView rvFavs;
    private AdapterFavsTvSeries adapterFavsList;
    private LinearLayoutManager mLayoutManagerTvSeries;

    private static User userAccount = new User();

    private boolean mIsLoading = false;
    private int mCurrentPage = 0;

    public FragmentFavsTvSeries() {
    }

    public static FragmentFavsTvSeries newInstance() {
        FragmentFavsTvSeries fragmentFavsTvSeries = new FragmentFavsTvSeries();
        return fragmentFavsTvSeries;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvFavs = getView().findViewById(R.id.rv_fragment_favs_tv_series);

        setupRecycleView();
        getFavsTvSeries();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favs_tv_series, container, false);
    }

    void setupRecycleView() {
        mLayoutManagerTvSeries = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvFavs.getContext(),
                mLayoutManagerTvSeries.getOrientation());
        rvFavs.addItemDecoration(dividerItemDecoration);
        rvFavs.setLayoutManager(mLayoutManagerTvSeries);

        adapterFavsList = new AdapterFavsTvSeries(new AdapterFavsTvSeries.OnItemClickedListener() {
            @Override
            public void onItemClicked(TvSeries tvSeries) {
                Intent intent = new Intent(getView().getContext(), MovieTvSeriesDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Categories.TV_SERIES, tvSeries);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        rvFavs.setAdapter(adapterFavsList);
        rvFavs.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mIsLoading || dy <= 0) {
                    return;
                }
                int visibleItemCount = mLayoutManagerTvSeries.getChildCount();
                int totalItemCount = mLayoutManagerTvSeries.getItemCount();
                int pastVisiblesItems = mLayoutManagerTvSeries.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 3) {
                    getFavsTvSeries();
                }
            }
        });
    }


    private void getFavsTvSeries() {
        mCurrentPage++;
        mIsLoading = true;

        RequestManager.getInstance(this.getContext()).queryFavoriteTvSeries(
                Util.getApiKey,
                Util.getSessionId,
                Util.currentUser.getId(),
                mCurrentPage,
                new Callback<ResponseListFavoriteTvSeries>() {
                    @Override
                    public void onResponse(Call<ResponseListFavoriteTvSeries> call, Response<ResponseListFavoriteTvSeries> response) {
                        if (mCurrentPage == 1) {
                            adapterFavsList.setItems(response.body().getResultsList());
                        } else {
                            adapterFavsList.addItems(response.body().getResultsList());
                        }
                        mIsLoading = false;
                    }

                    @Override
                    public void onFailure(Call<ResponseListFavoriteTvSeries> call, Throwable t) {
                        mIsLoading = false;
                    }
                });

    }


}
