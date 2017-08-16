package com.madalinaloghin.movieapp.ui.fragment;


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
import com.madalinaloghin.movieapp.api.response.ResponseUserListDetails;
import com.madalinaloghin.movieapp.ui.adapter.AdapterUserListDetails;
import com.madalinaloghin.util.Util;
import com.madalinaloghin.util.object.UserListDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */

public class FragmentListsComponents extends Fragment {

    RecyclerView rvList;
    public int listId;
    private AdapterUserListDetails adapterUserListsDetails;
    private LinearLayoutManager mLayoutManagerList;
    private static final String LIST_ID = "id";

    public FragmentListsComponents() {
    }

    public static FragmentListsComponents newInstance(int listId) {
        Bundle bundle = new Bundle();
        bundle.putInt(LIST_ID, listId);
        FragmentListsComponents fragmentLists = new FragmentListsComponents();
        fragmentLists.setArguments(bundle);
        return fragmentLists;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lists_components, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        rvList = getView().findViewById(R.id.rv_list_components);
        listId = getArguments().getInt(LIST_ID);
        setupRecycleView();
        getListComponents();
    }


    void setupRecycleView() {
        mLayoutManagerList = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvList.getContext(),
                mLayoutManagerList.getOrientation());
        rvList.addItemDecoration(dividerItemDecoration);

        rvList.setLayoutManager(mLayoutManagerList);

        adapterUserListsDetails = new AdapterUserListDetails(new AdapterUserListDetails.OnItemClickedListener() {
            @Override
            public void onItemClicked(UserListDetail userListDetail) {

            }

        });
        rvList.setAdapter(adapterUserListsDetails);
    }

    void getListComponents() {
        RequestManager.getInstance(this.getContext()).queryUserListsDetails(
                Util.API_KEY,
                listId,
                new Callback<ResponseUserListDetails>() {
                    @Override
                    public void onResponse(Call<ResponseUserListDetails> call, Response<ResponseUserListDetails> response) {
                        adapterUserListsDetails.setItems(response.body().getResultsList());
                    }

                    @Override
                    public void onFailure(Call<ResponseUserListDetails> call, Throwable t) {
                    }
                }
        );


    }


}
