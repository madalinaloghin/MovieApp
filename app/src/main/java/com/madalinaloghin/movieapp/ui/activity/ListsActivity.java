package com.madalinaloghin.movieapp.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.madalinaloghin.movieapp.R;

public class ListsActivity extends BottomNavigationBaseActivity {

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_lists);


    }

    @Override
    protected BottomNavActivityType getType() {
        return BottomNavActivityType.LISTS;
    }
}
