package com.madalinaloghin.movieapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.madalinaloghin.movieapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public abstract class BottomNavigationBaseActivity extends AppCompatActivity {

    @BindView(R.id.bn_bottom_menu)
    BottomNavigationView navigation;

    @BindView(R.id.fl_basic_activity)
    FrameLayout frameParent;


    protected void onCreate(Bundle savedInstanceState, final int layout) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_base);

        ButterKnife.bind(this);
        View view = getLayoutInflater().inflate(layout, frameParent, false);
        frameParent.addView(view, 0);

        switch (getType()) {
            case HOME:
                navigation.setSelectedItemId(R.id.nav_menu_home_activity);
                break;
            case LISTS:
                navigation.setSelectedItemId(R.id.nav_menu_lists_activity);
                break;
            case FAVS:
                navigation.setSelectedItemId(R.id.nav_menu_favorites_activity);
                break;
            case ACCOUNT:
                navigation.setSelectedItemId(R.id.nav_menu_account_activity);
                break;
        }

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.isChecked()) {
                    return false;
                }
                switch (item.getItemId()) {
                    case R.id.nav_menu_home_activity:
                        startActivity(new Intent(BottomNavigationBaseActivity.this, HomeActivity.class));
                        overridePendingTransition();
                        return true;
                    case R.id.nav_menu_lists_activity:
                        startActivity(new Intent(BottomNavigationBaseActivity.this, ListsActivity.class));
                        overridePendingTransition();
                        return true;
                    case R.id.nav_menu_favorites_activity:
                        startActivity(new Intent(BottomNavigationBaseActivity.this, FavsActivity.class));
                        overridePendingTransition();
                        return true;
                    case R.id.nav_menu_account_activity:
                        startActivity(new Intent(BottomNavigationBaseActivity.this, AccountActivity.class));
                        overridePendingTransition();
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (navigation.getSelectedItemId() == R.id.nav_menu_home_activity) {
            finishAffinity();
        } else {
            navigation.setSelectedItemId(R.id.nav_menu_home_activity);
        }
    }

    protected abstract BottomNavActivityType getType();

    void overridePendingTransition() {
        overridePendingTransition(R.animator.no_anim, R.animator.no_anim);
    }

    public static enum BottomNavActivityType {
        HOME,
        LISTS,
        FAVS,
        ACCOUNT
    }
}
