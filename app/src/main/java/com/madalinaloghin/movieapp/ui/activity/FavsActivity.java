package com.madalinaloghin.movieapp.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.madalinaloghin.movieapp.R;

public class FavsActivity extends BottomNavigationBaseActivity {

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_favs);
    }

    @Override
    protected BottomNavActivityType getType() {
        return BottomNavActivityType.FAVS;
    }
}
