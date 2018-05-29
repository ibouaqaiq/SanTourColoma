package com.example.issam.santourcoloma.Fragments;


import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.issam.santourcoloma.Model.Sitio;
import com.example.issam.santourcoloma.R;
import com.example.issam.santourcoloma.View.InfoActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.function.Consumer;

public class MapsFragment extends Fragment implements OnMapReadyCallback {



    GoogleMap mMap;

    SupportMapFragment supportMapFragment;
    Context mContext;

    private MarkerOptions marker;

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
        //supportMapFragment.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        //mMap.setMaxZoomPreference(15);
        mMap.setMinZoomPreference(14.0f);

        FirebaseDatabase.getInstance().getReference().child("sitios/data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren().forEach(item -> {
                    Sitio sitio = item.getValue(Sitio.class);

                    LatLng sitioLatLng = new LatLng(Double.parseDouble(sitio.latitud), Double.parseDouble(sitio.longitud));
                    MarkerOptions marker = new MarkerOptions();
                    marker.position(sitioLatLng);
                    marker.title(sitio.nombreSitio);
                    marker.snippet(sitio.shortDesc);
                    marker.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_on));
                    mMap.addMarker(marker);

                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        // Add a marker in Sydney and move the camera
        LatLng santaColoma = new LatLng(41.452808, 2.208545);
        LatLng ayuntamientoSntk = new LatLng(41.452101, 2.207929);

        //mMap.addMarker(new MarkerOptions().position(santaColoma).title("Marker in SantaColoma"));

        marker = new MarkerOptions();
        marker.position(ayuntamientoSntk);
        marker.title("Ayuntamiento de Santa Coloma");
        marker.snippet("El primer núcleo de edificios...");
        marker.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_on));

        mMap.addMarker(marker);

        CameraPosition camera = new CameraPosition.Builder()
                .target(santaColoma)
                .zoom(10)
                .bearing(325)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(santaColoma));
    }


}
