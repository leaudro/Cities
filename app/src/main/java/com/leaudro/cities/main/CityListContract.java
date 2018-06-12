package com.leaudro.cities.main;

import com.leaudro.cities.model.City;

import java.util.List;

public interface CityListContract {

    public interface Presenter {
        public void fetchFullList();
        public void filter(String s);
    }

    public interface View {
        void showList(List<City> cities);
        void updateList(List<City> cities);
    }
}


