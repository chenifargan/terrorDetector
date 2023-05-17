package com.example.terrordetector;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Map_Fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_,container,false);
        SupportMapFragment supportMapFragment =(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.MY_MAP);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {


                      //  Create a LatLng object from the latitude and longitude values received from the service:
                        double Latitude=31.0461;
                        double Longitude=34.8516;
                        LatLng location = new LatLng(Latitude, Longitude);

                        // Create a MarkerOptions object with the position set to the clicked LatLng
                        MarkerOptions markerOptions = new MarkerOptions().position(location).title("israel");

                        // Set the title of the marker to the latitude and longitude values
                        markerOptions.title(location.latitude + "KG" + location.longitude);

                        // Clear any existing markers from the map
                        googleMap.clear();

                        // Add the marker to the map
                        googleMap.addMarker(markerOptions);

                        // Animate the camera to the marker location
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15));
                    }

                        /*MarkerOptions markerOptions= new MarkerOptions().position(location);
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude+"KG"+latLng.longitude);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,50));
                        googleMap.addMarker(markerOptions);*/


                });
            }
        });
        return view;
    }
}