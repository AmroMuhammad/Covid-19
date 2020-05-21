package com.example.covid_19.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.covid_19.R;
import com.example.covid_19.adaptors.SavedRecyclerAdaptor;
import com.example.covid_19.model.countryDatabase.Country;
import com.example.covid_19.model.countryDatabase.CountryDB;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SavedFragment extends Fragment {

    private List<Country> savedList;
    private RecyclerView recyclerView;
    private SavedRecyclerAdaptor adaptor;
    private CountryDB database;

    public SavedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);

        database = Room.databaseBuilder(getContext(), CountryDB.class, "CountryStatisticsDB").build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                savedList = database.countryDao().getAll();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        adaptor = new SavedRecyclerAdaptor(savedList, getContext());
                        recyclerView.setAdapter(adaptor);
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    }
                });
            }
        }).start();


    }
}
