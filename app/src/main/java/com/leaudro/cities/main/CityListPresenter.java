package com.leaudro.cities.main;

import android.support.v4.util.Pair;

import com.leaudro.cities.model.City;
import com.leaudro.cities.model.DataSource;

import java.util.Collections;
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
                view.updateList(dataSource.cities);
            }
        });
    }


    /*
    *
    * This is where the search is made. First, it checks whether the search is empty, so it shows
    * the full list.
    * Then, it tries to get a pair of indexes from the `indexMap`. If it finds it, there's nothing else
    * to do unless to show the sublist. If not:
    *
    * It checks if a search was already started, and if the last search made can be used as a start point.
    * The logic behind it is that if I was looking for "Ams" and I just added a letter to search for "Amst",
    * I should use the same sublist from my previous search because I already know that all the possible cities
    * with "Amst" are inside the "Ams" result. And this is a very common use case, because as the user is typing,
    * it will filter from the previous search.
    *
    * If a search wasn't started yet, or the user just pasted another string unrelated to the last search,
    * the app will then restart from the beginning. It will try to get the indexes from the first two (or one)
    * characters, and if it fails on doing that, is because there's no match.
    *
    * */
    @Override
    public void filter(String s) {
        s = s.toLowerCase();

        if (s.isEmpty()) {
            view.updateList(dataSource.cities);
            return;
        }

        Pair<Integer, Integer> index = dataSource.indexMap.get(s);
        if (index != null) {
            lastResult = dataSource.cities.subList(index.first, index.second);
        } else {
            if (lastSearch.length() > 0 && s.startsWith(lastSearch)) {
                lastResult = subList(lastResult, s);
            } else if (s.length() > 2) {
                if (dataSource.indexMap.containsKey(s.substring(0, 2))) {
                    index = dataSource.indexMap.get(s.substring(0, 2));
                } else if (dataSource.indexMap.containsKey(s.substring(0, 1))) {
                    index = dataSource.indexMap.get(s.substring(0, 1));
                }

                if (index != null) { //Tries to find the first two letters and goes from there
                    lastResult = subList(dataSource.cities.subList(index.first, index.second), s);
                } else {
                    lastResult = Collections.emptyList();
                }
            } else { //less than 2 chars, no match
                lastResult = Collections.emptyList();
            }
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
