package com.example.covid_19.controller.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.covid_19.R;

public class CountryDetails extends AppCompatActivity {
    TextView textView;
    Bundle bundle;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);
        bundle = getIntent().getExtras();
        data = bundle.getString("country Name");
        textView = findViewById(R.id.countryDetailTV);
        textView.setText(data);
    }
}
