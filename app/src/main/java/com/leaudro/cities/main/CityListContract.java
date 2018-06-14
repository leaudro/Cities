package com.leaudro.cities.main;

import com.leaudro.cities.model.City;

import java.util.List;

public interface CityListContract {

    interface Presenter {
        void fetchFullList();
        void filter(String s);
    }

    interface View {
        void showLoading();
        void hideLoading();
        void updateList(List<City> cities);
    }
}


