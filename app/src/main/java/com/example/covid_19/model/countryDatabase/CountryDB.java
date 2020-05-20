package com.example.covid_19.model.countryDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Country.class},version = 1,exportSchema = false)
public abstract class CountryDB extends RoomDatabase {

    public abstract CountryDao countryDao();
}
