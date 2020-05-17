package com.example.covid_19.controller.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covid_19.R;
import com.squareup.picasso.Picasso;

public class CountryDetails extends AppCompatActivity {
    TextView textView;
    ImageView imageView;
    Bundle bundle;
    String data;
    String data2;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Country Details");
        bundle = getIntent().getExtras();
        data = bundle.getString("country Name");
        data2 = bundle.getString("country Flag");
        textView = findViewById(R.id.worldTV);
        textView.setText(data);
        imageView = findViewById(R.id.worldIconIV);
        if(data2 !=null)
        Picasso.get().load(data2).into(imageView);
        else
            imageView.setImageResource(R.drawable.worldwide);

    }
}
