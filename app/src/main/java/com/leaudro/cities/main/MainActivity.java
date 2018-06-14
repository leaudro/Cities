package com.leaudro.cities.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.leaudro.cities.R;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setDisplayHomeAsUpEnabled(false);
        setTitle(R.string.app_name);
        super.onBackPressed();
    }

    @Override
    public void onListFragmentClick(City item) {
        CityDetailFragment fragment = new CityDetailFragment();

        final Bundle arguments = new Bundle();
        arguments.putDouble(EXTRA_LAT, item.coord.lat);
        arguments.putDouble(EXTRA_LON, item.coord.lon);
        fragment.setArguments(arguments);

        setDisplayHomeAsUpEnabled(true);
        setTitle(item.toString());
        hideKeyboard();

        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, fragment)
                .addToBackStack("detail")
                .commit();
    }

    private void setDisplayHomeAsUpEnabled(boolean enable) {
        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(enable);
        }
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null && (imm != null)) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
