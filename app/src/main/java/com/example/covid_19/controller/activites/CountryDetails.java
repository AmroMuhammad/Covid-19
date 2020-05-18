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
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_19.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class CountryDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.calender_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Toast.makeText(CountryDetails.this, "a7aaaaa", Toast.LENGTH_SHORT).show();
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
