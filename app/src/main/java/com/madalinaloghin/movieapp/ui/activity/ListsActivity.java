package com.madalinaloghin.movieapp.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.movieapp.api.RequestManager;
import com.madalinaloghin.movieapp.api.response.ResponseUserLists;
import com.madalinaloghin.movieapp.ui.fragment.FragmentListsComponents;
import com.madalinaloghin.util.Util;
import com.madalinaloghin.util.object.UserList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListsActivity extends BottomNavigationBaseActivity {

    ViewPager viewPagerLists;
    TabLayout tabLayout;

    public static ArrayList<UserList> userLists = new ArrayList<>();

    private SectionPagerAdapter mSectionPagerAdapter;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_lists);

        userLists.clear();
        viewPagerLists = (ViewPager) findViewById(R.id.vp_lists_fragments);
        tabLayout = (TabLayout) findViewById(R.id.tb_lists_tabs);

        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());

        getUserList();

    }

    void getUserList() {
        RequestManager.getInstance(this.getBaseContext()).queryUserList(
                Util.currentUser.getId(),
                Util.API_KEY,
                Util.SESSION_ID,
                new Callback<ResponseUserLists>() {
                    @Override
                    public void onResponse(Call<ResponseUserLists> call, Response<ResponseUserLists> response) {

                        updateUserLists(response.body().getResultsList());
                    }

                    @Override
                    public void onFailure(Call<ResponseUserLists> call, Throwable t) {

                    }
                }
        );
    }

    void updateUserLists(ArrayList<UserList> list) {
        userLists.addAll(list);
        tabLayout.setupWithViewPager(viewPagerLists);
        viewPagerLists.setAdapter(mSectionPagerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected BottomNavActivityType getType() {
        return BottomNavActivityType.LISTS;
    }


    private static class SectionPagerAdapter extends FragmentPagerAdapter {

        SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return FragmentListsComponents.newInstance(userLists.get(position).getId());
        }


        @Override
        public int getCount() {
            return userLists.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            CharSequence title;
            if (userLists.get(position).getItems() == 1) {
                title = userLists.get(position).getName() + "  (" +
                        userLists.get(position).getItems() + " item) ";
            } else {
                title = userLists.get(position).getName() + " (" +
                        userLists.get(position).getItems() + " items) ";
            }
            return title;
        }
    }


}
