package com.leaudro.cities.model.jsonadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.leaudro.cities.model.City;

import java.io.IOException;

public class CityJsonAdapter extends TypeAdapter<City> {
    @Override
    public void write(JsonWriter out, City value) throws IOException {
        //We don't need it right now, we don't write JSON
    }

    @Override
    public City read(JsonReader in) throws IOException {
        City city = new City();
        in.beginObject();
        while (in.peek() == JsonToken.NAME) {
            switch (in.nextName()) {
                case "name":
                    city.name = in.nextString();
                    break;
                case "country":
                    city.country = in.nextString();
                    break;
                case "_id":
                    city._id = in.nextLong();
                    break;
            }
        }

        //TODO deal with locations
        in.skipValue();

        in.endObject();

        city.nameLowerCase = city.name.toLowerCase();

        return city;
    }
}
