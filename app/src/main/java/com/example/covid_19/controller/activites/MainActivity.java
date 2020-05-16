package com.example.covid_19.controller.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;

import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.covid_19.controller.fragments.CountriesFragment;
import com.example.covid_19.R;
import com.example.covid_19.controller.fragments.SavedFragment;
import com.example.covid_19.controller.fragments.WorldFragment;
import com.example.covid_19.adaptors.ViewPagerAdaptor;
import com.example.covid_19.model.countriesPOJO.CountryResponse;
import com.example.covid_19.model.worldPOJO.APIResponse;
import com.google.android.material.tabs.TabLayout;
import com.mindorks.nybus.NYBus;
import com.mindorks.nybus.event.Channel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity  {


    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private WorldFragment worldFragment;
    private CountriesFragment countriesFragment;
    private SavedFragment savedFragment;
    private List<Fragment> fragments;
    private List<String> fragmentTitles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing android networking
        AndroidNetworking.initialize(getApplicationContext());
        worldNetworking();
        countriesNetworking();

        //inflating views
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        setSupportActionBar(toolbar);

        //initializing fragments
        worldFragment = new WorldFragment();
        countriesFragment = new CountriesFragment();
        savedFragment = new SavedFragment();

        //initializing viewPager
        tabLayout.setupWithViewPager(viewPager);
        fragmentsinit();
        fragmentTitlesinit();
        ViewPagerAdaptor adaptor = new ViewPagerAdaptor(getSupportFragmentManager(),0,fragments,fragmentTitles);
        viewPager.setAdapter(adaptor);

    }

    private void worldNetworking(){
        AndroidNetworking.get("https://covid-193.p.rapidapi.com/statistics").addHeaders("x-rapidapi-host","covid-193.p.rapidapi.com")
                .addHeaders("x-rapidapi-key","8e7aa5120dmshad49bc24f64c127p15c72cjsncd6aef60585a")
                .build().getAsObject(APIResponse.class, new ParsedRequestListener<APIResponse>() {
            @Override
            public void onResponse(APIResponse response) {
                Toast.makeText(MainActivity.this,"success" ,Toast.LENGTH_SHORT).show();
                NYBus.get().post(response.getResponse(), Channel.ONE);
            }

            @Override
            public void onError(ANError anError) {
                showDialog();
                Log.e(TAG, "onError: "+ anError.getErrorDetail());
            }
        });
    }

    private void countriesNetworking(){
        AndroidNetworking.get("https://covid-193.p.rapidapi.com/countries").addHeaders("x-rapidapi-host","covid-193.p.rapidapi.com")
                .addHeaders("x-rapidapi-key","8e7aa5120dmshad49bc24f64c127p15c72cjsncd6aef60585a")
                .build().getAsObject(CountryResponse.class, new ParsedRequestListener<CountryResponse>() {
            @Override
            public void onResponse(CountryResponse response) {
                Toast.makeText(MainActivity.this,"sucess2",Toast.LENGTH_SHORT).show();
                NYBus.get().post(response.getResponse(), Channel.TWO);
            }

            @Override
            public void onError(ANError anError) {
                showDialog();
                Log.e(TAG, "onError: "+ anError.getErrorDetail());
            }
        });
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

       private void showDialog(){
            new AlertDialog.Builder(this).setTitle("Network Error").setCancelable(false).setMessage("No Internet Connection").setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();

                }
            }).show();
    }

}
