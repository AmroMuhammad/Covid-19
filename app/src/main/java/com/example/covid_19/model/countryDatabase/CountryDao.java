package com.example.covid_19.model.countryDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CountryDao {

    @Insert
    long insert(Country country);

    @Delete
    void delete(Country country);

    @Query("SELECT * FROM Country WHERE countryName LIKE :countryName")
    Country[] search(String countryName);

    @Query("SELECT * FROM Country")
    List<Country> getAll();
}
