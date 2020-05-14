package com.example.covid_19.controller.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_19.R;
import com.example.covid_19.adaptors.CountryRecyclerAdaptor;
import com.mindorks.nybus.NYBus;
import com.mindorks.nybus.annotation.Subscribe;
import com.mindorks.nybus.event.Channel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CountriesFragment extends Fragment {
    private List<String> responseList;
    private RecyclerView recyclerView;

    public CountriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_countries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NYBus.get().register(this, Channel.TWO);
        recyclerView = view.findViewById(R.id.recyclerView);


    }

    @Subscribe(channelId = Channel.TWO)
    public void setCountryData(List<String> response){
        responseList = response;
        recyclerView.setAdapter(new CountryRecyclerAdaptor(responseList,getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));

    }
}
