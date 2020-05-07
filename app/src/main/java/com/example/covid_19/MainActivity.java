package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

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

        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get("https://covid-193.p.rapidapi.com/statistics").addQueryParameter("country","egypt").addHeaders("x-rapidapi-host","covid-193.p.rapidapi.com").addHeaders("x-rapidapi-key","8e7aa5120dmshad49bc24f64c127p15c72cjsncd6aef60585a")
    .build().getAsObject(APIResponse.class, new ParsedRequestListener<APIResponse>() {
            @Override
            public void onResponse(APIResponse response) {
                Toast.makeText(MainActivity.this,response.getGet(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(MainActivity.this, anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
            }
        });
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
