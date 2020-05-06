package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private WorldFragment worldFragment;
    private CountriesFragment countriesFragment;
    private  SavedFragment savedFragment;
    private List<Fragment> fragments;
    private List<String> fragmentTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        setSupportActionBar(toolbar);

        worldFragment = new WorldFragment();
        countriesFragment = new CountriesFragment();
        savedFragment = new SavedFragment();

        tabLayout.setupWithViewPager(viewPager);
        fragmentsinit();
        fragmentTitlesinit();
        ViewPagerAdaptor adaptor = new ViewPagerAdaptor(getSupportFragmentManager(),0,fragments,fragmentTitles);
        viewPager.setAdapter(adaptor);
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
