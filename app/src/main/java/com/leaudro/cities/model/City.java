package com.leaudro.cities.model;

import android.support.annotation.NonNull;

public class City implements Comparable<City> {
    public long _id;
    public String name;
    public String country;

    @Override
    public String toString() {
        return name + ", " + country;
    }

    @Override
    public int compareTo(@NonNull City o) {
        return name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof City && ((City) obj)._id == _id;
    }

    @Override
    public int hashCode() {
        return (int) _id;
    }
}
