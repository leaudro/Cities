package com.leaudro.cities.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leaudro.cities.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.leaudro.cities.App.appInstance;

public class DataSource {

    private Gson gson = new GsonBuilder().create();

    public List<City> cities = getCities();

    private List<City> getCities() {
        InputStream raw =  appInstance.getResources().openRawResource(R.raw.cities);
        Reader reader = new BufferedReader(new InputStreamReader(raw));
        final List<City> list = Arrays.asList(gson.fromJson(reader, City[].class));
        Collections.sort(list);

        return list;
    }
}
