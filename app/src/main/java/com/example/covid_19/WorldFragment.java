package com.example.covid_19;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorldFragment extends Fragment {
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
        EventBus.getDefault().register(this);
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
    }

    @Subscribe
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