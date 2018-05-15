package com.example.issam.santourcoloma.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.issam.santourcoloma.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements OnMapReadyCallback {



    GoogleMap mMap;

    SupportMapFragment supportMapFragment;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getActivity();

        FragmentManager fm = getActivity().getSupportFragmentManager();/// getChildFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map1);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map1, supportMapFragment).commit();
        }
        supportMapFragment.getMapAsync(this);
    }


    @Override
    public void onResume() {
        supportMapFragment.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        supportMapFragment.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        //mMap.setMaxZoomPreference(15);
        mMap.setMinZoomPreference(14.0f);
        // Add a marker in Sydney and move the camera
        LatLng santaColoma = new LatLng(41.452808, 2.208545);
        mMap.addMarker(new MarkerOptions().position(santaColoma).title("Marker in SantaColoma"));

        CameraPosition camera = new CameraPosition.Builder()
                .target(santaColoma)
                .zoom(10)
                .bearing(325)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(santaColoma));
    }
}
