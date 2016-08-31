package com.shubham.haptikchattask;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.shubham.haptikchattask.adapters.ParticipantsAdapter;
import com.shubham.haptikchattask.helper.Users;
import com.shubham.haptikchattask.listeners.UpdateList;
import com.shubham.haptikchattask.listeners.UsersListListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by Shubham Gupta on 29-08-2016.
 */
@EActivity(R.layout.main_activity_layout)
public class MainActivity extends AppCompatActivity implements UsersListListener {

    @ViewById(R.id.tab_layout)
    TabLayout tabLayout;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.pager)
    ViewPager viewPager;
    ParticipantsAdapter.PagerAdapter adapter;

    @AfterViews
    void init() {
        setSupportActionBar(toolbar);

        tabLayout.addTab(tabLayout.newTab().setText("Chat"));
        tabLayout.addTab(tabLayout.newTab().setText("Participants"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);



        adapter = new ParticipantsAdapter.PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public void updateList(ArrayList<Users> users) {
        Log.i("check","print");
        UpdateList updateList = adapter.getParticipantsListFragment();
        updateList.updateList(users);
    }
}
