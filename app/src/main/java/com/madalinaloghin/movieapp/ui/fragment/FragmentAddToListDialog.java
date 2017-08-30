package com.madalinaloghin.movieapp.ui.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.movieapp.api.RequestManager;
import com.madalinaloghin.movieapp.api.response.ResponseUserLists;
import com.madalinaloghin.movieapp.ui.adapter.AdapterListsAddToList;
import com.madalinaloghin.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddToListDialog extends DialogFragment {

    public final static String KEY_ID = "id";
    RecyclerView rvList;
    int itemId;
    private LinearLayoutManager mLinearLayoutmanager;
    private AdapterListsAddToList mAdapter;


    public FragmentAddToListDialog() {
    }


    public static FragmentAddToListDialog newInstance(int id) {
        FragmentAddToListDialog fragment = new FragmentAddToListDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        rvList = new RecyclerView(getContext());
        setupRecycleView();
        getListItems();

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.title_add_to_a_list)
                .setView(rvList)
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                updateListInfos();
                            }
                        })
                .create();

    }

  /*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rvList = getView().findViewById(R.id.rv_fragment_favs_movies);

        setupRecycleView();
        getListItems();
    }*/


    void setupRecycleView() {
        mLinearLayoutmanager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(mLinearLayoutmanager);
        mAdapter = new AdapterListsAddToList(itemId);
        rvList.setAdapter(mAdapter);
    }


    void updateListInfos() {

    }


    void getListItems() {
        RequestManager.getInstance(this.getContext()).queryUserList(
                Util.currentUser.getId(),
                Util.API_KEY,
                Util.SESSION_ID,
                new Callback<ResponseUserLists>() {
                    @Override
                    public void onResponse(Call<ResponseUserLists> call, Response<ResponseUserLists> response) {
                        mAdapter.setItems(response.body().getResultsList());
                    }

                    @Override
                    public void onFailure(Call<ResponseUserLists> call, Throwable t) {

                    }
                }
        );
    }

}
