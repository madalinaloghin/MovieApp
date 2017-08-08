package com.madalinaloghin.movieapp.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.movieapp.api.RequestManager;
import com.madalinaloghin.movieapp.api.response.ResponseUserListDetails;
import com.madalinaloghin.movieapp.api.response.ResponseUserLists;
import com.madalinaloghin.movieapp.ui.adapter.AdapterUserList;
import com.madalinaloghin.movieapp.ui.adapter.AdapterUserListDetails;
import com.madalinaloghin.util.object.UserList;
import com.madalinaloghin.util.object.UserListDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListsActivity extends BottomNavigationBaseActivity {

    RecyclerView rvLists;
    private AdapterUserList adapterLists;
    private LinearLayoutManager mLayoutManager;
    private boolean mIsLoading = false;
    private int mCurrentPage = 0;

    int listId;

    RecyclerView rvListDetails;
    private UserListDetail detail;
    private LinearLayoutManager mLayoutManagerDetails;
    public AdapterUserListDetails mAdapter;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_lists);
        rvLists = (RecyclerView) findViewById(R.id.rv_user_lists);

        setupRecycleView();
        getUserList();

        rvListDetails = (RecyclerView) findViewById(R.id.rv_user_lists_details2);

    }


    void setupRecycleView() {
        mLayoutManager = new LinearLayoutManager(this.getBaseContext(), LinearLayoutManager.HORIZONTAL, false );
        rvLists.setLayoutManager(mLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvLists.getContext(),
                mLayoutManager.getOrientation());
        rvLists.addItemDecoration(dividerItemDecoration);

        adapterLists = new AdapterUserList(new AdapterUserList.OnItemClickedListener() {
            @Override
            public void onItemClicke(UserList userList) {
                listId = userList.getId();
                setupRecycleViewDetails();
                getListComponentsDetails();
                /*Intent intent = new Intent(getBaseContext(), MovieListDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", userList.getId());
                intent.putExtras(bundle);
                startActivity(intent);*/
            }
        });

        rvLists.setAdapter(adapterLists);

        rvLists.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mIsLoading || dy < 0) {
                    return;
                }
                int visibleItemCount = mLayoutManager.getChildCount();
                int visibleTotalCount = mLayoutManager.getItemCount();
                int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                if (visibleItemCount  < visibleTotalCount ) {
                    getUserList();
                }
            }
        });
    }

    void getUserList() {
        mCurrentPage++;
        mIsLoading = true;
        RequestManager.getInstance(this.getBaseContext()).queryUserList(
                mCurrentPage,
                new Callback<ResponseUserLists>() {
                    @Override
                    public void onResponse(Call<ResponseUserLists> call, Response<ResponseUserLists> response) {
                        if (mCurrentPage == 1) {
                            adapterLists.setItems(response.body().getResultsList());
                        } else {
                            adapterLists.addItems(response.body().getResultsList());
                        }
                        mIsLoading = false;
                    }
                    @Override
                    public void onFailure(Call<ResponseUserLists> call, Throwable t) {
                        mIsLoading = false;
                    }
                }
        );

    }


    void setupRecycleViewDetails() {
        mLayoutManagerDetails = new LinearLayoutManager(this.getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvListDetails.getContext(),
                mLayoutManagerDetails.getOrientation());
        rvListDetails.addItemDecoration(dividerItemDecoration);

        rvListDetails.setLayoutManager(mLayoutManagerDetails);

        mAdapter = new AdapterUserListDetails(new AdapterUserListDetails.OnItemClickedListener() {
            @Override
            public void onItemClicked(UserListDetail userListDetail) {


            }
        });

        rvListDetails.setAdapter(mAdapter);

    }

    void getListComponentsDetails() {
        RequestManager.getInstance(this.getBaseContext()).queryUserListsDetails(
                listId,
                new Callback<ResponseUserListDetails>() {
                    @Override
                    public void onResponse(Call<ResponseUserListDetails> call, Response<ResponseUserListDetails> response) {
                        mAdapter.setItems(response.body().getResultsList());
                    }
                    @Override
                    public void onFailure(Call<ResponseUserListDetails> call, Throwable t) {
                    }
                }
        );

    }

    @Override
    protected BottomNavActivityType getType() {
        return BottomNavActivityType.LISTS;
    }
}
