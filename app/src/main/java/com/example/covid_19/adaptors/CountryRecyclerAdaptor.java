package com.example.covid_19.adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19.R;
import com.example.covid_19.controller.activites.CountryDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CountryRecyclerAdaptor extends RecyclerView.Adapter<CountryRecyclerAdaptor.RecyclerHolder> implements Filterable {
    private List<String> countriesList;
    private List<String> countriesListFull;
    private Context context;
    private List<HashMap<String, String>> countriesFlags;
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(countriesListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (String item : countriesListFull) {
                    if (item.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            countriesList.clear();
            countriesList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public CountryRecyclerAdaptor(List<String> countriesList, Context context, List<HashMap<String, String>> countriesFlags) {
        this.countriesList = countriesList;
        this.context = context;
        this.countriesFlags = countriesFlags;
        this.countriesListFull = new ArrayList<>(countriesList);
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_country_item, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerHolder holder, int position) {
        final Intent intent = new Intent(context, CountryDetails.class);
        final String country = countriesList.get(position);
        String countryFlagURL = null;
        String countryCode = getCountryCode(country);
        holder.countryNameTV.setText(country);
        if (countryCode != null) {
            countryFlagURL = "https://www.countryflags.io/" + countryCode + "/shiny/64.png";
            Picasso.get().load(countryFlagURL).into(holder.countryFlagIV);
            intent.putExtra("country Flag", countryFlagURL);
        } else
            holder.countryFlagIV.setImageResource(R.drawable.worldwide);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("country Name", country);
                context.startActivity(intent);
            }
        });
    }

    private String getCountryCode(String countryName) {
        for (HashMap<String, String> hashMap : countriesFlags) {
            if (hashMap.containsValue(countryName)) {
                return hashMap.get("code");
            }
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return countriesList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        private ImageView countryFlagIV;
        private TextView countryNameTV;
        private View root;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            countryFlagIV = itemView.findViewById(R.id.countryFlag_TV);
            countryNameTV = itemView.findViewById(R.id.countryName_TV);
            root = itemView.findViewById(R.id.countryCard);
        }


    }
}
