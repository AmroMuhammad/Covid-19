package com.example.covid_19.controller.fragments;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.covid_19.R;
import com.example.covid_19.controller.activites.MainActivity;
import com.example.covid_19.model.worldPOJO.APIResponse;
import com.example.covid_19.model.worldPOJO.Response;
import com.mindorks.nybus.NYBus;
import com.mindorks.nybus.annotation.Subscribe;
import com.mindorks.nybus.event.Channel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorldFragment extends Fragment {
    private static final String TAG = "WorldFragment";
    private List<Response> responseList;
    private TextView dateTV;
    private String newMonth;
    ProgressBar progressBarWorld;
    private TextView newTV;
    private TextView activeTV;
    private TextView criticalTV;
    private TextView recoveredTV;
    private TextView totalTV;
    private TextView newDeathTV;
    private TextView totalDeathTV;
    private TextView timeTV;

    public WorldFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_world, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dateTV = view.findViewById(R.id.dateTV);
        timeTV= view.findViewById(R.id.timeTV);
        newTV = view.findViewById(R.id.newNum_TV);
        activeTV = view.findViewById(R.id.activeNum_TV);
        criticalTV = view.findViewById(R.id.criticalNum_TV);
        recoveredTV = view.findViewById(R.id.recoveredNum_TV);
        totalTV = view.findViewById(R.id.totalNum_TV);
        newDeathTV = view.findViewById(R.id.newDeathNum_TV);
        totalDeathTV = view.findViewById(R.id.totalDeathNum_TV);
        progressBarWorld = view.findViewById(R.id.progressBarWorld);
        progressBarWorld.setVisibility(View.VISIBLE);

        worldNetworking();


    }

    public void setData(List<Response> responses) {
        responseList = responses;
        String oldDate = String.valueOf(responseList.get(189).getDay());
        String time = String.valueOf(responseList.get(189).getTime());
        time = "Last Updated "+time.substring(11,19) + " GMT";
        dateTV.setText(reverseDate(oldDate));
        timeTV.setText(time);
        newTV.setText(responseList.get(189).getCases().getNew());
        activeTV.setText(responseList.get(189).getCases().getActive().toString());
        criticalTV.setText(responseList.get(189).getCases().getCritical().toString());
        recoveredTV.setText(responseList.get(189).getCases().getRecovered().toString());
        totalTV.setText(responseList.get(189).getCases().getTotal().toString());
        newDeathTV.setText(responseList.get(189).getDeaths().getNew());
        totalDeathTV.setText(responseList.get(189).getDeaths().getTotal().toString());
        progressBarWorld.setVisibility(View.GONE);
    }

    private void worldNetworking(){
        AndroidNetworking.get("https://covid-193.p.rapidapi.com/statistics").addHeaders("x-rapidapi-host","covid-193.p.rapidapi.com")
                .addHeaders("x-rapidapi-key","8e7aa5120dmshad49bc24f64c127p15c72cjsncd6aef60585a")
                .build().getAsObject(APIResponse.class, new ParsedRequestListener<APIResponse>() {
            @Override
            public void onResponse(APIResponse response) {
                Toast.makeText(getContext(),"success" ,Toast.LENGTH_SHORT).show();
                setData(response.getResponse());
            }

            @Override
            public void onError(ANError anError) {
                showDialog();
               Log.e(TAG, "onError: "+ anError.getErrorDetail());
            }
        });
    }

    private void showDialog(){
        new AlertDialog.Builder(getContext()).setTitle("Network Error").setMessage("No Internet Connection").setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = getActivity().getIntent();
                getActivity().finish();
                startActivity(intent);
            }
        }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();

            }
        }).show();
    }

        private String reverseDate(String date) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "Septmeber", "October", "November", "December"};
        String rightDate;
        // String newMonth;
        if (date.charAt(5) == '0') {
            for (int i = 1; i <= 9; i++) {
                if (Character.getNumericValue(date.charAt(6)) == i) {
                    newMonth = months[i - 1];
                    break;
                }
            }
        } else if (date.charAt(5) == '1') {
            for (int i = 0; i <= 2; i++) {
                if (Character.getNumericValue(date.charAt(6)) == i) {
                    newMonth = months[i + 9];
                    break;
                }
            }
        } else
            return date;
        rightDate = date.substring(8, 10) + "/" + newMonth + "/" + date.substring(0, 4);
        return rightDate;
    }

}