package com.example.covid_19.controller.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.covid_19.R;
import com.example.covid_19.model.countryDatabase.Country;
import com.example.covid_19.model.countryDatabase.CountryDB;
import com.example.covid_19.model.historyPOJO.HistoryResponse;
import com.example.covid_19.model.historyPOJO.Response;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CountryDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TextWatcher {
    private List<Response> historyResponse;
    private TextView dateTV;
    String formattedDate;
    private TextView timeTV;
    private ProgressBar progressBarWorld;
    private TextView countryNameTV;
    private ImageView countryFlagIV;
    private TextView newTV;
    private TextView activeTV;
    private TextView criticalTV;
    private TextView recoveredTV;
    private TextView totalTV;
    private TextView newDeathTV;
    private TextView totalDeathTV;
    private Bundle bundle;
    private String receivedCountryName;
    private String receivedImageURL;
    private Toolbar toolbar;
    private String time;
    private ImageView favouriteIV;
    private CountryDB database;

    private static final String TAG = "CountryDetails";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);

        //Toolbar initialization
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Country Details");

        //data bundle from countries tab
        bundle = getIntent().getExtras();
        receivedCountryName = bundle.getString("country Name");
        receivedImageURL = bundle.getString("country Flag");
        getDate();

        //inflating views
        countryNameTV = findViewById(R.id.countryNameTV);
        countryFlagIV = findViewById(R.id.countryFlagIV);
        dateTV = findViewById(R.id.dateTV);
        timeTV = findViewById(R.id.timeTV);
        progressBarWorld = findViewById(R.id.progressBarWorld);
        newTV=findViewById(R.id.newNum_TV);
        activeTV=findViewById(R.id.activeNum_TV);
        criticalTV=findViewById(R.id.criticalNum_TV);
        recoveredTV=findViewById(R.id.recoveredNum_TV);
        totalTV=findViewById(R.id.totalNum_TV);
        newDeathTV=findViewById(R.id.newDeathNum_TV);
        totalDeathTV=findViewById(R.id.totalDeathNum_TV);
        favouriteIV = findViewById(R.id.favouriteIV);

        //initializing Database
        database = Room.databaseBuilder(this,CountryDB.class,"CountryStatisticsDB").allowMainThreadQueries().build();

        //checking country in favourite list or not
        if(isFavourite(receivedCountryName)){
            favouriteIV.setImageResource(R.drawable.onstar);
        }

        favouriteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavourite(receivedCountryName)) {
                    removeFromFavourite(receivedCountryName);
                    favouriteIV.setImageResource(R.drawable.offstar);
                    Toast.makeText(CountryDetails.this, "Removed from Saved Countries", Toast.LENGTH_SHORT).show();
                } else {
                    addToFavourtie(receivedCountryName);
                    favouriteIV.setImageResource(R.drawable.onstar);
                    Toast.makeText(CountryDetails.this, "added to Saved Countries", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //setting Bundle to text.
        dateTV.setText(formattedDate);
        countryNameTV.setText(receivedCountryName);
        if(receivedImageURL !=null) {
            Picasso.get().load(receivedImageURL).into(countryFlagIV);
        }
        else {
            countryFlagIV.setImageResource(R.drawable.worldwide);
        }

        //networking initialization
        progressBarWorld.setVisibility(View.VISIBLE);
        historyNetworking(receivedCountryName,formattedDate);

        //detecting date
        dateTV.addTextChangedListener(this);


    }

    private void removeFromFavourite(String receivedCountryName) {
        Country country = new Country();
        country.setCountryName(receivedCountryName);
        database.countryDao().delete(country);
    }

    public boolean isFavourite(String receivedCountryName){
        Country[] val = database.countryDao().search(receivedCountryName);
        if(val.length > 0)
            return true;
        else
            return false;
    }

    public void addToFavourtie(String receivedCountryName){
        Country country = new Country();
        country.setCountryName(receivedCountryName);
       long id = database.countryDao().insert(country);
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
            this.formattedDate = year + "-0" + month + "-0" + dayOfMonth;
        else if(dayOfMonth<10)
            this.formattedDate = year+"-"+month+"-0"+dayOfMonth;
        else if (month<10)
            this.formattedDate = year + "-0" + month + "-" + dayOfMonth;
        else
            this.formattedDate = year + "-" + month + "-" + dayOfMonth;
        dateTV.setText(formattedDate);
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
    }

    private void historyNetworking(String countryName,String date){
        AndroidNetworking.get("https://covid-193.p.rapidapi.com/history").addQueryParameter("country",countryName).addQueryParameter("day",date).addHeaders("x-rapidapi-host","covid-193.p.rapidapi.com")
                .addHeaders("x-rapidapi-key","8e7aa5120dmshad49bc24f64c127p15c72cjsncd6aef60585a")
                .build().getAsObject(HistoryResponse.class, new ParsedRequestListener<HistoryResponse>() {
            @Override
            public void onResponse(HistoryResponse response) {
                progressBarWorld.setVisibility(View.GONE);
                Toast.makeText(CountryDetails.this,"success3" ,Toast.LENGTH_SHORT).show();
                historyResponse = response.getResponse();
                if(historyResponse.size() != 0) {
                    int i = historyResponse.size() - 1;
                    setDataToViews(i);
                }
                else{
                    showCountryHasNoHistoryAvailable();
                }
            }

            @Override
            public void onError(ANError anError) {
                progressBarWorld.setVisibility(View.GONE);
                showDialog();
                Log.e(TAG, "onError: "+ anError.getErrorDetail());
            }
        });
    }
    private void showCountryHasNoHistoryAvailable() {
        new AlertDialog.Builder(this).setTitle("No Data Available").setMessage("Country Has No History Available").show();
    }

    private void setDataToViews(int i) {
        if(historyResponse.get(i).getCases().getNew() == null)
            newTV.setText("0");
        else
        newTV.setText(historyResponse.get(i).getCases().getNew());

        activeTV.setText(historyResponse.get(i).getCases().getActive().toString());
        criticalTV.setText(historyResponse.get(i).getCases().getCritical().toString());
        recoveredTV.setText(historyResponse.get(i).getCases().getRecovered().toString());
        totalTV.setText(historyResponse.get(i).getCases().getTotal().toString());

        if(historyResponse.get(i).getDeaths().getNew() == null)
            newDeathTV.setText("0");
        else
        newDeathTV.setText(historyResponse.get(i).getDeaths().getNew());

        totalDeathTV.setText(historyResponse.get(i).getDeaths().getTotal().toString());
        time = historyResponse.get(i).getTime();
        time = "Last Updated " + time.substring(11, 19) + " GMT";
        timeTV.setText(time);

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

    private String getDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c);
        return formattedDate;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        historyNetworking(receivedCountryName,s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
