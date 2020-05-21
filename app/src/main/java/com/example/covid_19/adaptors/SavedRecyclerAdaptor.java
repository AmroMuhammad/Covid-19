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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19.R;
import com.example.covid_19.controller.activites.CountryDetails;
import com.example.covid_19.controller.activites.SavedCountryDetails;
import com.example.covid_19.model.countryDatabase.Country;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SavedRecyclerAdaptor extends RecyclerView.Adapter<SavedRecyclerAdaptor.RecyclerHolder>{
    private List<Country> savedList;
    private Context context;

    public SavedRecyclerAdaptor(List<Country> savedList, Context context) {
        this.savedList = savedList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_country_item,parent,false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerHolder holder, int position) {
        final Intent intent = new Intent(context, SavedCountryDetails.class);
        final String country = savedList.get(position).getCountryName();
        final Country countryData = savedList.get(position);
        String countryFlagURL = countryData.getImageURL();
        holder.countryNameTV.setText(country);
        if(countryFlagURL != null) {
            Picasso.get().load(countryFlagURL).into(holder.countryFlagIV);
            intent.putExtra("flagURL",countryFlagURL);
        }
        else
            holder.countryFlagIV.setImageResource(R.drawable.worldwide);

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, holder.countryNameTV.getText(), Toast.LENGTH_SHORT).show();
                intent.putExtra("countryData",countryData);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return savedList.size();
    }



    class RecyclerHolder extends RecyclerView.ViewHolder{
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
