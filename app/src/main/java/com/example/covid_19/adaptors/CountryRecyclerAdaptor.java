package com.example.covid_19.adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.covid_19.controller.activites.CountryDetails;

public class CountryRecyclerAdaptor extends RecyclerView.Adapter<CountryRecyclerAdaptor.RecyclerHolder> {
    private List<String> countriesList;
    private Context context;
    private List<HashMap<String,String>> countriesFlags;


    public CountryRecyclerAdaptor(List<String> countriesList, Context context,List<HashMap<String,String>> countriesFlags) {
        this.countriesList = countriesList;
        this.context = context;
        this.countriesFlags = countriesFlags;
    }
    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_country_item,parent,false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerHolder holder, int position) {
        final String country = countriesList.get(position);
        String countryCode = getCountryCode(country);
        holder.countryNameTV.setText(country);
        if(countryCode != null)
        Picasso.get().load("https://www.countryflags.io/"+countryCode+"/shiny/64.png").into(holder.countryFlagIV);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, holder.countryNameTV.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,CountryDetails.class);
                intent.putExtra("country Name",country);
                context.startActivity(intent);
            }
        });
    }

    private String getCountryCode(String countryName){
        for (HashMap<String,String> hashMap : countriesFlags) {
                if(hashMap.containsValue(countryName)){
                  return  hashMap.get("code");
               }
        }

        return null;
    }


    @Override
    public int getItemCount() {
        return countriesList.size();
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
