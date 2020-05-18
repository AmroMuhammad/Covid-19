package com.example.covid_19.controller.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_19.R;
import com.example.covid_19.model.worldPOJO.Response;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class CountryDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private TextView dateTV;
    private String newMonth;
    ProgressBar progressBarWorld;
    private TextView countryNameTV;
    private ImageView countryFlagIV;
    private TextView newTV;
    private TextView activeTV;
    private TextView criticalTV;
    private TextView recoveredTV;
    private TextView totalTV;
    private TextView newDeathTV;
    private TextView totalDeathTV;
    private TextView timeTV;
    Bundle bundle;
    String recievedCountryName;
    String recievedImageURL;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Country Details");
        bundle = getIntent().getExtras();
        recievedCountryName = bundle.getString("country Name");
        recievedImageURL = bundle.getString("country Flag");
        countryNameTV = findViewById(R.id.countryNameTV);
        countryNameTV.setText(recievedCountryName);
        countryFlagIV = findViewById(R.id.countryFlagIV);
        if(recievedImageURL !=null)
        Picasso.get().load(recievedImageURL).into(countryFlagIV);
        else
            countryFlagIV.setImageResource(R.drawable.worldwide);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.calender_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)  {
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                   showDatePickerDialog();
                    return false;
                }
            });
        return super.onOptionsItemSelected(item);
    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month+1;
        if(dayOfMonth<10 && month<10)
        Toast.makeText(this, year+"-0"+month+"-0"+dayOfMonth, Toast.LENGTH_SHORT).show();
        else if(dayOfMonth<10)
            Toast.makeText(this, year+"-"+month+"-0"+dayOfMonth, Toast.LENGTH_SHORT).show();
        else if (month<10)
            Toast.makeText(this, year+"-0"+month+"-"+dayOfMonth, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, year+"-"+month+"-"+dayOfMonth, Toast.LENGTH_SHORT).show();
    }
}
