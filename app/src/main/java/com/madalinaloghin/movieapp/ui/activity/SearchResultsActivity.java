package com.madalinaloghin.movieapp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.movieapp.ui.fragment.FragmentSearchMovies;
import com.madalinaloghin.movieapp.ui.fragment.FragmentSearchPerson;
import com.madalinaloghin.movieapp.ui.fragment.FragmentSearchTvSeries;

public class SearchResultsActivity extends AppCompatActivity {

    ViewPager viewPagerFavorites;
    TabLayout tabLayout;

    private SectionPagerAdapter mSectionPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        String query = getIntent().getStringExtra("query");

        viewPagerFavorites = (ViewPager) findViewById(R.id.vp_search_fragments);
        tabLayout = (TabLayout) findViewById(R.id.tl_search_tabs);

        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());

        tabLayout.setupWithViewPager(viewPagerFavorites);
        viewPagerFavorites.setAdapter(mSectionPagerAdapter);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    public static class SectionPagerAdapter extends FragmentPagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return FragmentSearchMovies.newInstance();
                case 1:
                    return FragmentSearchTvSeries.newInstance();
                case 2:
                    return FragmentSearchPerson.newInstance();
                default:
                    return FragmentSearchMovies.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Movies";
                case 1:
                    return "Tv series";
                case 2:
                    return "Persons";
            }
            return null;
        }


    }
}
