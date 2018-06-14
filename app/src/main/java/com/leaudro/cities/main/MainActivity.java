package com.leaudro.cities.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaudro.cities.main.citydetail.CityDetailFragment;
import com.leaudro.cities.model.City;

import static com.leaudro.cities.main.citydetail.CityDetailFragment.EXTRA_LAT;
import static com.leaudro.cities.main.citydetail.CityDetailFragment.EXTRA_LON;

public class MainActivity extends AppCompatActivity implements CityAdapter.OnCityListClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, new CityListFragment())
                    .commit();
        }
    }

    @Override
    public void onListFragmentClick(City item) {
        CityDetailFragment fragment = new CityDetailFragment();

        final Bundle arguments = new Bundle();
        arguments.putDouble(EXTRA_LAT, item.coord.lat);
        arguments.putDouble(EXTRA_LON, item.coord.lon);
        fragment.setArguments(arguments);

        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, fragment)
                .addToBackStack("detail")
                .commit();
    }
}
