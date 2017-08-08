package com.madalinaloghin.movieapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.movieapp.api.RequestManager;
import com.madalinaloghin.movieapp.api.response.ResponseUserListDetails;
import com.madalinaloghin.movieapp.ui.adapter.AdapterUserListDetails;
import com.madalinaloghin.util.object.UserListDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListDetailsActivity extends AppCompatActivity {

    int listId;
    RecyclerView rvListDetails;
    private UserListDetail detail;
    private LinearLayoutManager mLayoutManager;
    public AdapterUserListDetails mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list_details);

        Bundle b = getIntent().getExtras();
        listId = b.getInt("id");
        rvListDetails = (RecyclerView) findViewById(R.id.rv_user_lists_details);

        setupRecycleView();
        getListComponents();
    }

    void setupRecycleView() {
        mLayoutManager = new LinearLayoutManager(this.getBaseContext(), LinearLayoutManager.HORIZONTAL, false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvListDetails.getContext(),
                mLayoutManager.getOrientation());
        rvListDetails.addItemDecoration(dividerItemDecoration);

        rvListDetails.setLayoutManager(mLayoutManager);

        mAdapter = new AdapterUserListDetails(new AdapterUserListDetails.OnItemClickedListener() {
            @Override
            public void onItemClicked(UserListDetail userListDetail) {


            }
        });

        rvListDetails.setAdapter(mAdapter);

    }

    void getListComponents() {
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
}
