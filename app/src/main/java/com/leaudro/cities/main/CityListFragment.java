package com.leaudro.cities.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.leaudro.cities.R;
import com.leaudro.cities.model.City;
import com.leaudro.cities.model.DataSource;

import java.util.List;

public class CityListFragment extends Fragment implements CityListContract.View {

    private RecyclerView recyclerView;
    private View loadingView;

    private CityListPresenter presenter;
    private CityAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_city_list, container, false);

        recyclerView = view.findViewById(R.id.list);
        loadingView = view.findViewById(R.id.loading);
        final EditText editSearch = view.findViewById(R.id.edit_search);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new CityListPresenter(this, new DataSource());
        presenter.fetchFullList();
    }

    @Override
    public void showList(List<City> cities) {
        adapter = new CityAdapter(cities, null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateList(List<City> cities) {
        adapter.update(cities);
    }

    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
    }
}
