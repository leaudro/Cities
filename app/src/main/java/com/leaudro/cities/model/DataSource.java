package com.leaudro.cities.model;

import android.os.AsyncTask;
import android.support.v4.util.Pair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leaudro.cities.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.leaudro.cities.App.appInstance;

public class DataSource {

    private Gson gson = new GsonBuilder().create();

    public List<City> cities = null;

    /*
    * The decision here was to use array/ArrayList, because then I don't need to worry about
    * duplicating entries when calling subList(), I can just work with indexes and the list
    * will be always the same, with no duplicates.
    *
    * When the JSON is parsed, I also create a field `nameLowerCase` to avoid calling `String.toLowerCase()`
    * all the time when searching.
    * */
    private List<City> getCities() {
        InputStream raw = appInstance.getResources().openRawResource(R.raw.cities);
        Reader reader = new BufferedReader(new InputStreamReader(raw));
        final List<City> list = Arrays.asList(gson.fromJson(reader, City[].class));
        Collections.sort(list);

        return list;
    }

    /*
    * This variable will hold indexes (begin and end) to all possible sublists
    * of the first two characters on the city's list.
    * With that, the search can be optimized when it's most critical: on the beginning.
    * */
    public HashMap<String, Pair<Integer, Integer>> indexMap;

    /*
    * On this method we go through the whole List of cities, keeping the indexes of the first two
    * characters of every city.
    * At the end of this method, `indexMap` will be like this:
    * { { "a", (0, 5) },{ "ab", (0, 2) },{ "ac", (2, 5) }, ... }
    * the key will be the first 1 or 2 chars of a search, while the value will be a pair of indexes,
    * representing the start/end of a sublist that matches the key search.
    * */
    void createIndexMap() {
        City firstCity = cities.get(0);

        final char c;
        if (firstCity.nameLowerCase.length() > 1)
            c = firstCity.nameLowerCase.charAt(1);
        else c = 0;
        final char[] key = {firstCity.nameLowerCase.charAt(0), c};

        indexMap = new HashMap<>();

        int indexOneChar = 0, indexTwoChars = 0;
        final int size = cities.size();
        for (int i = 0; i < size; i++) {
            City city = cities.get(i);
            final char firstChar = city.nameLowerCase.charAt(0);
            final char secondChar;
            if (city.nameLowerCase.length() > 1) //There are cities with just one char (JP)
                secondChar = city.nameLowerCase.charAt(1);
            else secondChar = 0;

            if (key[0] != firstChar) {
                createIndexes(key, indexOneChar, indexTwoChars, i);

                key[0] = firstChar;
                key[1] = secondChar;

                indexOneChar = i;
                indexTwoChars = i;
            } else if (key[1] != secondChar) {
                createIndexes(key, -1, indexTwoChars, i);

                key[1] = secondChar;
                indexTwoChars = i;
            }
        }

        createIndexes(key, indexOneChar, indexTwoChars, size);
    }

    private void createIndexes(char[] keys, int startFirstChar, int startSecondChar, int end) {
        if (startFirstChar >= 0)
            indexMap.put(String.valueOf(keys[0]), new Pair<>(startFirstChar, end));
        if (keys[1] > 0 && startSecondChar >= 0)
            indexMap.put(String.valueOf(keys), new Pair<>(startSecondChar, end));
    }

    public void setup(final OnSetupComplete callback) {
        DataSourceAsyncTask asyncTask = new DataSourceAsyncTask(callback);
        asyncTask.execute(this);
    }

    public interface OnSetupComplete {
        void onComplete();
    }

    private static class DataSourceAsyncTask extends AsyncTask<DataSource, Object, Object> {

        private OnSetupComplete callback;

        DataSourceAsyncTask(OnSetupComplete callback) {
            this.callback = callback;
        }

        @Override
        protected void onPostExecute(Object o) {
            callback.onComplete();
        }

        @Override
        protected Object doInBackground(DataSource[] dataSources) {
            if (dataSources == null) return null;
            for (DataSource dataSource : dataSources) {
                dataSource.cities = dataSource.getCities();
                dataSource.createIndexMap();
            }

            return null;
        }
    }
}
