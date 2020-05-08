package com.example.covid_19;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;

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
    }

    @Subscribe
    public void setData(List<Response> responses) {
        responseList = responses;
        String oldDate = String.valueOf(responseList.get(189).getDay());
        dateTV.setText(reverseDate(oldDate));
    }

    private String reverseDate(String date) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "Septmeber", "October", "November", "December"};
        String rightDate;
        // String newMonth;
        if (date.charAt(5) == '0') {
            for (int i = 1; i <= 9; i++) {
                if (Character.getNumericValue(date.charAt(6)) ==i) {
                    newMonth = months[i - 1];
                    break;
                }
            }
        }
        else if (date.charAt(5) == '1') {
            for (int i = 0; i <= 2; i++) {
                if (Character.getNumericValue(date.charAt(6)) == i) {
                    newMonth = months[i + 9];
                    break;
                }
            }
        }
        else
            return date;
        rightDate = date.substring(8,10)+"/"+ newMonth +"/"+date.substring(0,4);
        return rightDate;
    }
}