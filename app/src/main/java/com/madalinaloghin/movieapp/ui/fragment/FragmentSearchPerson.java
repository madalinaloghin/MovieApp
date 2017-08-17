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
import com.madalinaloghin.movieapp.api.response.ResponseListPeople;
import com.madalinaloghin.movieapp.ui.activity.PersonDetailsActivity;
import com.madalinaloghin.movieapp.ui.adapter.AdapterSearchPerson;
import com.madalinaloghin.util.Util;
import com.madalinaloghin.util.object.Person;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSearchPerson extends Fragment {

    public static final String QUERY = "query";

    RecyclerView rvSearchPerson;
    public String query;
    private AdapterSearchPerson adapterSearchPerson;
    private LinearLayoutManager mLayoutManagerSearchPerson;
    private boolean mIsLoading = false;
    private int mCurrentPage = 0;

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


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvSearchPerson = getView().findViewById(R.id.rv_search_person);
        query = getArguments().getString(QUERY);

        setupRecycleView();
        getSearchPerson();
    }

    void setupRecycleView() {
        mLayoutManagerSearchPerson = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(rvSearchPerson.getContext(),
                mLayoutManagerSearchPerson.getOrientation());
        rvSearchPerson.addItemDecoration(itemDecoration);

        rvSearchPerson.setLayoutManager(mLayoutManagerSearchPerson);

        adapterSearchPerson = new AdapterSearchPerson(new AdapterSearchPerson.OnItemClickedListener() {
            @Override
            public void onItemClicked(Person person) {
                Intent intent = new Intent(getView().getContext(), PersonDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Util.PERSON, person);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        rvSearchPerson.setAdapter(adapterSearchPerson);

        rvSearchPerson.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mIsLoading || dy < 0) {
                    return;
                }
                int visibleItemCount = mLayoutManagerSearchPerson.getChildCount();
                int visibleTotalCount = mLayoutManagerSearchPerson.getItemCount();
                int pastVisibleItemsCount = mLayoutManagerSearchPerson.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisibleItemsCount) >= visibleTotalCount - 3) {
                    getSearchPerson();
                }
            }
        });
    }


    void getSearchPerson() {
        mIsLoading = true;
        mCurrentPage++;
        RequestManager.getInstance(this.getContext()).querySearchPerson(
                Util.API_KEY,
                query,
                mCurrentPage,
                new Callback<ResponseListPeople>() {
                    @Override
                    public void onResponse(Call<ResponseListPeople> call, Response<ResponseListPeople> response) {
                        if (mCurrentPage == 1) {
                            adapterSearchPerson.setItems(response.body().getResultsList());
                        } else {
                            adapterSearchPerson.addItems(response.body().getResultsList());
                        }
                        mIsLoading = false;
                    }

                    @Override
                    public void onFailure(Call<ResponseListPeople> call, Throwable t) {
                        mIsLoading = false;
                    }
                }
        );
    }


}
