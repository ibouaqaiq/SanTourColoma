package com.example.issam.santourcoloma.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.issam.santourcoloma.R;
import com.example.issam.santourcoloma.View.SitiosActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavFragment extends SitiosFragment {
    Query setQuery(){
        return FirebaseDatabase.getInstance().getReference().child("sitios/favoritos").child(FirebaseAuth.getInstance().getUid());
    }
}
