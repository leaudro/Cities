package com.leaudro.cities.main;

import android.support.v4.util.Pair;

import com.leaudro.cities.model.City;
import com.leaudro.cities.model.DataSource;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CityListPresenterTest {

    CityListPresenter presenter;

    CityListContract.View view;
    DataSource dataSource;

    @Before
    public void setup() {
        dataSource = mock(DataSource.class);
        view = mock(CityListContract.View.class);
        presenter = new CityListPresenter(view, dataSource);
    }

    @Test
    public void shouldReturnFullList_WhenSearchIsEmpty() {
        setupDataSource();

        presenter.filter("");
        verify(view).updateList(dataSource.cities);
    }

    @Test
    public void shouldReturnEmpty_WhenThereIsNoMatch() {
        setupDataSource();

        presenter.filter("s");
        verify(view).updateList(Collections.<City>emptyList());
    }

    @Test
    public void shouldReturnCorrect_WhenSearchOneChar() {
        setupDataSource();

        presenter.filter("a");
        verify(view).updateList(Arrays.asList(new City(1, "a", "BR"),
                new City(2, "Ab", "BR"),
                new City(3, "avc", "BR")));
    }

    @Test
    public void shouldReturnCorrect_WhenSearchTwoChars() {
        setupDataSource();

        presenter.filter("bb");
        verify(view).updateList(Collections.singletonList(new City(4, "Bb", "BR")));
    }

    @Test
    public void shouldReturnCorrect_WhenSearchMoreThanTwoChars() {
        setupDataSource();

        presenter.filter("avc");
        verify(view).updateList(Collections.singletonList(new City(3, "avc", "BR")));
    }

    @Test
    public void shouldReturnCorrect_WhenSearchCase() {
        setupDataSource();

        presenter.filter("BB");
        verify(view).updateList(Collections.singletonList(new City(4, "Bb", "BR")));
    }

    private void setupDataSource() {
        dataSource.indexMap = new HashMap<>();

        dataSource.indexMap.put("a", new Pair(0, 3));
        dataSource.indexMap.put("ab", new Pair(1, 2));
        dataSource.indexMap.put("av", new Pair(2, 3));
        dataSource.indexMap.put("b", new Pair(3, 5));
        dataSource.indexMap.put("bb", new Pair(3, 4));
        dataSource.indexMap.put("bd", new Pair(4, 5));
        dataSource.indexMap.put("z", new Pair(5, 6));

        dataSource.cities = Arrays.asList(
                new City(1, "a", "BR"),
                new City(2, "Ab", "BR"),
                new City(3, "avc", "BR"),
                new City(4, "Bb", "BR"),
                new City(5, "Bd", "BR"),
                new City(6, "Z", "BR")
        );
    }
}
