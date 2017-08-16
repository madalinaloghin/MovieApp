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
import com.madalinaloghin.movieapp.api.response.ResponseListTvSeries;
import com.madalinaloghin.movieapp.ui.activity.TvSeriesDetailsActivity;
import com.madalinaloghin.movieapp.ui.adapter.AdapterFavsTvSeries;
import com.madalinaloghin.util.Util;
import com.madalinaloghin.util.object.TvSeries;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSearchTvSeries extends Fragment {

    RecyclerView rvSearchTvSeries;
    public String query;
    private AdapterFavsTvSeries adapterTvSeriesSearch;
    private LinearLayoutManager mLayoutManagerTvSeriesSearch;
    private boolean mIsLoading = false;
    private int mCurrentPage = 0;

    public static final String QUERY = "query";

    public FragmentSearchTvSeries() {
    }

    public static FragmentSearchTvSeries newInstance(String query) {
        FragmentSearchTvSeries fragment = new FragmentSearchTvSeries();
        Bundle bundle = new Bundle();
        bundle.putString(QUERY, query);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_tv_series, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvSearchTvSeries = getView().findViewById(R.id.rv_search_tv_series);
        query = getArguments().getString(QUERY);

        setupRecycleView();
        getSearchMovies();
    }


    void setupRecycleView() {
        mLayoutManagerTvSeriesSearch = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvSearchTvSeries.getContext(),
                mLayoutManagerTvSeriesSearch.getOrientation());
        rvSearchTvSeries.addItemDecoration(dividerItemDecoration);

        rvSearchTvSeries.setLayoutManager(mLayoutManagerTvSeriesSearch);

        adapterTvSeriesSearch = new AdapterFavsTvSeries(new AdapterFavsTvSeries.OnItemClickedListener() {
            @Override
            public void onItemClicked(TvSeries tvSeries) {
                Intent intent = new Intent(getView().getContext(), TvSeriesDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Util.TV_SERIES, tvSeries);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rvSearchTvSeries.setAdapter(adapterTvSeriesSearch);

        rvSearchTvSeries.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mIsLoading || dy < 0) {
                    return;
                }
                int visibleItemCount = mLayoutManagerTvSeriesSearch.getChildCount();
                int visibleTotalCount = mLayoutManagerTvSeriesSearch.getItemCount();
                int pastVisibleItems = mLayoutManagerTvSeriesSearch.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisibleItems) >= visibleTotalCount - 3) {
                    getSearchMovies();
                }
            }
        });

    }


    void getSearchMovies() {
        mIsLoading = true;
        mCurrentPage++;
        RequestManager.getInstance(this.getContext()).querySearchTvSeries(
                Util.API_KEY,
                query,
                mCurrentPage,
                new Callback<ResponseListTvSeries>() {
                    @Override
                    public void onResponse(Call<ResponseListTvSeries> call, Response<ResponseListTvSeries> response) {
                        if (mCurrentPage == 1) {
                            adapterTvSeriesSearch.setItems(response.body().getResultsList());
                        } else {
                            adapterTvSeriesSearch.addItems(response.body().getResultsList());
                        }
                        mIsLoading = false;
                    }

                    @Override
                    public void onFailure(Call<ResponseListTvSeries> call, Throwable t) {
                        mIsLoading = false;
                    }
                }

        );

    }


}
