package com.example.issam.santourcoloma.Fragments;


import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AllSitiosFragment extends SitiosFragment {

    Query setQuery(){
        return FirebaseDatabase.getInstance().getReference().child("sitios/all");
    }
}
