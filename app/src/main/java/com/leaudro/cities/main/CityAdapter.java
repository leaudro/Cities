package com.leaudro.cities.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leaudro.cities.R;
import com.leaudro.cities.model.City;

import java.util.Collections;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private List<City> cities = Collections.emptyList();
    private final OnCityListClickListener listener;

    public CityAdapter(OnCityListClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.city = cities.get(position);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onListFragmentClick(holder.city);
                }
            }
        });
        holder.textName.setText(holder.city.toString());
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void update(List<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView textName;
        public City city;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            textName = view.findViewById(R.id.name);
        }
    }

    public interface OnCityListClickListener {
        void onListFragmentClick(City item);
    }
}