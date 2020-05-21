package com.example.covid_19.controller.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;

import com.example.covid_19.controller.fragments.CountriesFragment;
import com.example.covid_19.R;
import com.example.covid_19.controller.fragments.SavedFragment;
import com.example.covid_19.controller.fragments.WorldFragment;
import com.example.covid_19.adaptors.ViewPagerAdaptor;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private WorldFragment worldFragment;
    private CountriesFragment countriesFragment;
    private SavedFragment savedFragment;
    private List<Fragment> fragments;
    private List<String> fragmentTitles;
    private  ViewPagerAdaptor adaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing android networking
        AndroidNetworking.initialize(getApplicationContext());


        //inflating views
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        setSupportActionBar(toolbar);

        //initializing fragments
        savedFragment = new SavedFragment();
        worldFragment = new WorldFragment();
        countriesFragment = new CountriesFragment();

        //initializing viewPager
        tabLayout.setupWithViewPager(viewPager);
        fragmentsinit();
        fragmentTitlesinit();
        adaptor = new ViewPagerAdaptor(getSupportFragmentManager(),0,fragments,fragmentTitles);
        viewPager.setAdapter(adaptor);

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPager.getAdapter().notifyDataSetChanged();
    }

    private void fragmentTitlesinit() {
        fragmentTitles = new ArrayList<>();
        fragmentTitles.add("World");
        fragmentTitles.add("Countries");
        fragmentTitles.add("Saved");
    }

    private void fragmentsinit() {
        fragments = new ArrayList<>();
        fragments.add(worldFragment);
        fragments.add(countriesFragment);
        fragments.add(savedFragment);
    }

}
