package com.leaudro.cities.main;

import com.leaudro.cities.model.City;
import com.leaudro.cities.model.DataSource;

import java.util.List;

public class CityListPresenter implements CityListContract.Presenter {

    private final CityListContract.View view;
    private final DataSource dataSource;

    private String lastSearch = "";
    private List<City> lastResult = null;

    public CityListPresenter(CityListContract.View view, DataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    public void fetchFullList() {
        view.showLoading();
        dataSource.setup(new DataSource.OnSetupComplete() {
            @Override
            public void onComplete() {
                view.hideLoading();
                view.showList(dataSource.cities);
            }
        });
    }

    @Override
    public void filter(String s) {
        s = s.toLowerCase();
        if (lastSearch.length() > 0 && s.startsWith(lastSearch)) {
            lastResult = subList(lastResult, s);
        } else {
            lastResult = subList(dataSource.cities, s);
        }
        lastSearch = s;
        view.updateList(lastResult);
    }


    private List<City> subList(List<City> list, String s) {
        int start = 0;
        int end = 0;
        boolean found = false;
        for (int i = 0; i < list.size(); i++) {
            final boolean startsWith = list.get(i).nameLowerCase.startsWith(s);
            if (!found && startsWith) {
                found = true;
                start = i;
            } else if (found && !startsWith) {
                end = i;
                found = false;
                break;
            }
        }
        if (found) end = list.size();
        return list.subList(start, end);
    }
}
