package com.leaudro.cities.model;

import android.os.AsyncTask;

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

    public List<City> cities = null;

    private List<City> getCities() {
        InputStream raw = appInstance.getResources().openRawResource(R.raw.cities);
        Reader reader = new BufferedReader(new InputStreamReader(raw));
        final List<City> list = Arrays.asList(gson.fromJson(reader, City[].class));
        Collections.sort(list);

        return list;
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
            }
            return null;
        }
    }
}
