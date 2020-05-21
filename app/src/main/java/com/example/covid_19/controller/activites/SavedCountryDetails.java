package com.example.covid_19.controller.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.covid_19.R;
import com.example.covid_19.model.countryDatabase.Country;
import com.example.covid_19.model.countryDatabase.CountryDB;
import com.example.covid_19.model.historyPOJO.Response;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SavedCountryDetails extends AppCompatActivity {

    private TextView countryNameTV;
    private TextView dateTV;
    private TextView timeTV;
    private ImageView countryFlagIV;
    private TextView newTV;
    private TextView activeTV;
    private TextView criticalTV;
    private TextView recoveredTV;
    private TextView totalTV;
    private TextView newDeathTV;
    private TextView totalDeathTV;
    private CountryDB database;
    private Toolbar toolbar;
    private Bundle bundle;
    private String flagURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_country_details);

        //intialize database
        database = Room.databaseBuilder(this,CountryDB.class,"CountryStatisticsDB").allowMainThreadQueries().build();

        //receiving intent data
        Country country =getIntent().getExtras().getParcelable("countryData");

        //setting toolbar
        //Toolbar initialization
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Saved Country Details");

        //inflating views
        countryNameTV = findViewById(R.id.countryNameTV);
        dateTV = findViewById(R.id.dateTV);
        timeTV = findViewById(R.id.timeTV);
        countryFlagIV = findViewById(R.id.countryFlagIV);
        newTV = findViewById(R.id.newNum_TV);
        activeTV=findViewById(R.id.activeNum_TV);
        criticalTV=findViewById(R.id.criticalNum_TV);
        recoveredTV=findViewById(R.id.recoveredNum_TV);
        totalTV=findViewById(R.id.totalNum_TV);
        newDeathTV=findViewById(R.id.newDeathNum_TV);
        totalDeathTV=findViewById(R.id.totalDeathNum_TV);

        //Setting data to views
        countryNameTV.setText(country.getCountryName());
        dateTV.setText(country.getDate());
        timeTV.setText(country.getTime());
        newTV.setText(country.getNewCases());
        activeTV.setText(country.getActiveCases());
        criticalTV.setText(country.getCriticalCases());
        recoveredTV.setText(country.getRecoveredCases());
        totalTV.setText(country.getTotalCases());
        newDeathTV.setText(country.getNewDeaths());
        totalDeathTV.setText(country.getTotalDeaths());

        bundle = getIntent().getExtras();
        flagURL = bundle.getString("flagURL");

        //Image Caching
        if(flagURL != null) {
            Picasso.get()
                    .load(flagURL)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(countryFlagIV);
        }
        else
            countryFlagIV.setImageResource(R.drawable.worldwide);
    }


}
