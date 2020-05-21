package com.example.covid_19.controller.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.covid_19.R;
import com.example.covid_19.adaptors.CountryRecyclerAdaptor;
import com.example.covid_19.model.countriesPOJO.CountryResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CountriesFragment extends Fragment {
    private static final String TAG = "Countries Fragment";
    private List<String> responseList;
    private RecyclerView recyclerView;
    private ArrayList<HashMap<String, String>> formList;
    private CountryRecyclerAdaptor adaptor;

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
        recyclerView = view.findViewById(R.id.recyclerView);
        countriesNetworking();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.country_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setQueryHint("Search Country");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adaptor.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void setCountryData(List<String> response) {
        responseList = response;
        adaptor = new CountryRecyclerAdaptor(responseList, getContext(), loadCountriesFromJSON());
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }


    private void countriesNetworking() {
        AndroidNetworking.get("https://covid-193.p.rapidapi.com/countries").addHeaders("x-rapidapi-host", "covid-193.p.rapidapi.com")
                .addHeaders("x-rapidapi-key", "8e7aa5120dmshad49bc24f64c127p15c72cjsncd6aef60585a")
                .build().getAsObject(CountryResponse.class, new ParsedRequestListener<CountryResponse>() {
            @Override
            public void onResponse(CountryResponse response) {
                setCountryData(response.getResponse());
            }

            @Override
            public void onError(ANError anError) {
                showDialog();
                Log.e(TAG, "onError: " + anError.getErrorDetail());
            }
        });
    }

    private void showDialog() {
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

    public ArrayList<HashMap<String, String>> loadCountriesFromJSON() {
        String json = null;
        try {
            InputStream is = getContext().getAssets().open("country.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray m_jArry = obj.getJSONArray("countries");
            formList = new ArrayList<>();
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("name"));
                Log.d("Details-->", jo_inside.getString("code"));
                String countryName = jo_inside.getString("name");
                String countryCode = jo_inside.getString("code");

                //Add your values in your `ArrayList` as below:
                m_li = new HashMap<String, String>();
                m_li.put("name", countryName);
                m_li.put("code", countryCode);

                formList.add(m_li);
            }
            return formList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

}
