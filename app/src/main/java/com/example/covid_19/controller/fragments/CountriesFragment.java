package com.example.covid_19.controller.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_countries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NYBus.get().register(this, Channel.TWO);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.country_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search Country");
    }

    @Subscribe(channelId = Channel.TWO)
    public void setCountryData(List<String> response){
        responseList = response;
        recyclerView.setAdapter(new CountryRecyclerAdaptor(responseList,getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));

    }
}
