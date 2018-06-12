package com.leaudro.cities.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.JsonAdapter;
import com.leaudro.cities.model.jsonadapter.CityJsonAdapter;

@JsonAdapter(CityJsonAdapter.class)
public class City implements Comparable<City> {
    public long _id;
    public String name;
    public String country;
    public String nameLowerCase;

    @Override
    public String toString() {
        return name + ", " + country;
    }

    @Override
    public int compareTo(@NonNull City o) {
        int result = nameLowerCase.compareTo(o.nameLowerCase);
        if (result == 0)
            return country.compareTo(o.country);
        else return result;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof City && ((City) obj)._id == _id;
    }

    @Override
    public int hashCode() {
        return (int) _id;
    }
}
