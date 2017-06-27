package com.swift.solutions.player;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class DrawerActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    public Toolbar mToolbar;

    private FragmentDrawer drawerFragment;

    // used to store app title
    private CharSequence mTitle;
    Fragment fragment;
    private Boolean restore = false;
    Boolean action = false;
    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        if (savedInstanceState != null) {

            //Restore the fragment's instance
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, "currentFragment");

            restore = savedInstanceState.getBoolean("restore");
            mTitle = savedInstanceState.getString("mTitle");

        } else {

            fragment = new Fragment();

            restore = false;
            mTitle = getString(R.string.app_name);
        }

        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container_body, fragment).commit();
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(mTitle);

        drawerFragment = (FragmentDrawer) getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        if (!restore) {

            displayView(1);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putBoolean("restore", true);
        outState.putString("mTitle", getSupportActionBar().getTitle().toString());
        getSupportFragmentManager().putFragment(outState, "currentFragment", fragment);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

        displayView(position);
    }
    private void displayView(int position) {

        action = false;

        switch (position) {

            case 0: {

                break;
            }
            case 1: {

                page = 1;
                fragment = new StreamFragment();
                getSupportActionBar().setTitle(R.string.page_1);


                action = true;

                break;
            }
            case 2: {

                page = 2;

                fragment = new VideoFragment();
                getSupportActionBar().setTitle(R.string.page_2);

                action = true;

                break;
            }

            case 3: {

                page = 3;

                fragment = new AudioFragment();
                getSupportActionBar().setTitle(R.string.page_3);
                action = true;

                break;
            }

            case 4: {

                page = 4;

                fragment = new RecentFragment();
                getSupportActionBar().setTitle(R.string.page_4);
                action = true;

                break;
            }

            case 5: {

                page = 5;

                fragment = new SettingsFragment();
                getSupportActionBar().setTitle(R.string.page_5);
                action = true;

                break;
            }

            default: {

                page = 6;

                fragment = new AboutFragment();
                getSupportActionBar().setTitle(R.string.page_6);
                action = true;

                break;
            }
        }
        if (action) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container_body, fragment)
                    .commit();
        }
    }
    @Override
    public void onBackPressed() {

        if (drawerFragment.isDrawerOpen()) {

            drawerFragment.closeDrawer();

        } else {

            super.onBackPressed();
        }
    }

    @Override
    public void setTitle(CharSequence title) {

        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
}
