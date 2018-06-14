package com.leaudro.cities.model;

import android.support.v4.util.Pair;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

public class DataSourceTest {

    private DataSource dataSource;

    @Before
    public void setup() {
        dataSource = new DataSource();
        dataSource.cities = Arrays.asList(
                new City(1, "a", "BR"),
                new City(2, "Ab", "BR"),
                new City(3, "avc", "BR"),
                new City(4, "Bb", "BR"),
                new City(5, "Bd", "BR"),
                new City(6, "Z", "BR")
        );
    }

    @Test
    public void shouldCreateMapCorrectly() {
        HashMap<String, Pair<Integer,Integer>> expectedMap = new HashMap<>();

        expectedMap.put("a", new Pair(0, 3));
        expectedMap.put("ab", new Pair(1, 2));
        expectedMap.put("av", new Pair(2, 3));
        expectedMap.put("b", new Pair(3, 5));
        expectedMap.put("bb", new Pair(3, 4));
        expectedMap.put("bd", new Pair(4, 5));
        expectedMap.put("z", new Pair(5, 6));

        dataSource.createIndexMap();

        assert dataSource.indexMap.equals(expectedMap);
    }
}