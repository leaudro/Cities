package com.leaudro.cities.main.citydetail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leaudro.cities.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class CityDetailFragment extends Fragment {

    public static final String EXTRA_LAT = "EXTRA_LAT";
    public static final String EXTRA_LON = "EXTRA_LON";

    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_city_detail, container, false);
        imageView = view.findViewById(R.id.image);

        if (getArguments() != null) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    final double lat = getArguments().getDouble(EXTRA_LAT);
                    final double lon = getArguments().getDouble(EXTRA_LON);
                    downloadImage(lat, lon);
                }
            });
        }

        return view;
    }

    public void downloadImage(double lat, double lon) {
        final AsyncTask<URL, Void, Bitmap> asyncTask = new AsyncTask<URL, Void, Bitmap>() {

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }

            @Override
            protected Bitmap doInBackground(URL... urls) {
                URL url = urls[0];
                try {
                    return BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    // TODO show error to user (on postExecute)
                    return null;
                }
            }
        };

        try {
            asyncTask.execute(createMapsURL(lat, lon));
        } catch (MalformedURLException ignored) {
            // TODO show error to user
        }
    }

    private URL createMapsURL(double lat, double lon) throws MalformedURLException {
        String imageViewSize = String.format("%dx%d", 320, 480);
        return new URL("https://maps.googleapis.com/maps/api/staticmap?zoom=14&scale=2" +
                "&size=" + imageViewSize +
                "&maptype=roadmap" +
                "&markers=color:blue%7Clabel:S%7C" + lat + "," + lon +
                "&key=AIzaSyDE2c8JZzuTqKAvCBm7GEcYNgzT7HSdm4M");
    }
}