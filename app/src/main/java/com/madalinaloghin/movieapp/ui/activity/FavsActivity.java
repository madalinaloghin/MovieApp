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
import com.madalinaloghin.movieapp.ui.fragment.FragmentFavsMovies;
import com.madalinaloghin.movieapp.ui.fragment.FragmentFavsTvSeries;

public class FavsActivity extends BottomNavigationBaseActivity {

    ViewPager viewPagerFavorites;
    TabLayout tabLayout;

    private SectionPagerAdapter mSectionPagerAdapter;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_favs);

        viewPagerFavorites = (ViewPager) findViewById(R.id.vp_favorites_fragnents);
        tabLayout = (TabLayout) findViewById(R.id.tb_favorites_tabs);

        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());

        tabLayout.setupWithViewPager(viewPagerFavorites);
        viewPagerFavorites.setAdapter(mSectionPagerAdapter);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected BottomNavActivityType getType() {
        return BottomNavActivityType.FAVS;
    }

    public static class SectionPagerAdapter extends FragmentPagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return FragmentFavsMovies.newInstance();
                case 1:
                    return FragmentFavsTvSeries.newInstance();
                default:
                    return FragmentFavsMovies.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Movies";
                case 1:
                    return "Tv series";
            }
            return null;
        }


    }
}
