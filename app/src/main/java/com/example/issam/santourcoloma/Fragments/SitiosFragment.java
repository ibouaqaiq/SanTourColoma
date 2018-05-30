package com.example.issam.santourcoloma.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.issam.santourcoloma.Model.Sitio;
import com.example.issam.santourcoloma.R;
import com.example.issam.santourcoloma.View.InfoActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class SitiosFragment extends Fragment {
    DatabaseReference mReference;

    DatabaseReference mReferenceFotos;

    RecyclerView recyclerView;


    public SitiosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sitios, container, false);

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(getAdapter());

        mReference = FirebaseDatabase.getInstance().getReference();


        return view;
    }


    FirebaseRecyclerAdapter<Sitio, SitioViewHolder> getAdapter(){
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Sitio>()
                .setIndexedQuery(setQuery(), FirebaseDatabase.getInstance().getReference().child("sitios/data"), Sitio.class)
                .setLifecycleOwner(this)
                .build();

        return new FirebaseRecyclerAdapter<Sitio, SitioViewHolder>(options) {
            @Override
            public SitioViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new SitioViewHolder(inflater.inflate(R.layout.item_sitio, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(final SitioViewHolder viewHolder, int position, final Sitio sitio) {
                final String postKey = getRef(position).getKey();

                System.out.println("SITIOOOOOO " + sitio.nombreSitio);
                viewHolder.nombresitio.setText(sitio.nombreSitio);
                //viewHolder.fotositio.setText(sitio.fotoSitioId);

                if(sitio.fav!= null && sitio.fav.containsKey(FirebaseAuth.getInstance().getUid())){
                    viewHolder.fav.setImageResource(R.drawable.heart_on);
                    } else  {
                    viewHolder.fav.setImageResource(R.drawable.heart_off);

                }

                viewHolder.fav.setOnClickListener(v -> {
                    String uid = FirebaseAuth.getInstance().getUid();

                    if(sitio.fav != null && sitio.fav.containsKey(uid)){
                        mReference.child("sitios/data").child(postKey).child("fav").child(uid).setValue(null);
                        mReference.child("sitios/favoritos").child(uid).child(postKey).setValue(null);
                    } else{
                        mReference.child("sitios/data").child(postKey).child("fav").child(uid).setValue(true);
                        mReference.child("sitios/favoritos").child(uid).child(postKey).setValue(true);

                    }
                });

                if(sitio.flag!= null && sitio.flag.containsKey(FirebaseAuth.getInstance().getUid())){
                    viewHolder.flag.setImageResource(R.drawable.flag_on);
                } else  {
                    viewHolder.flag.setImageResource(R.drawable.flag_off);

                }

                viewHolder.flag.setOnClickListener((View v) -> {
                    String uid = FirebaseAuth.getInstance().getUid();
                    if(sitio.flag != null && sitio.flag.containsKey(uid)){
                        mReference.child("sitios/data").child(postKey).child("flag").child(uid).setValue(null);
                        mReference.child("sitios/visitados").child(uid).child(postKey).setValue(null);
                    } else{
                        mReference.child("sitios/data").child(postKey).child("flag").child(uid).setValue(true);
                        mReference.child("sitios/visitados").child(uid).child(postKey).setValue(true);

                    }
                });

                viewHolder.cv.setOnClickListener(v -> {

                    Intent i = new Intent(getActivity(), InfoActivity.class);
                    i.putExtra("info",sitio);
                    startActivity(i);

                });

                if(sitio.imagenes != null){
                    viewHolder.fotositio.setVisibility(View.VISIBLE);
                    Glide.with(SitiosFragment.this.getActivity())
                            .load(sitio.imagenes.get(0))
                            .into(viewHolder.fotositio);
                }

                viewHolder.short_desc.setText(sitio.shortDesc);

            }
        };
    }

    class SitioViewHolder extends RecyclerView.ViewHolder {
        TextView nombresitio;
        ImageView fotositio;
        ImageView fav;
        ImageView flag;
        TextView short_desc;
        CardView cv;
        //LinearLayout favLayout;
        //LinearLayout flagLayout;


        SitioViewHolder(View view){
            super(view);
            nombresitio = view.findViewById(R.id.nombreSitio);
            fotositio = view.findViewById(R.id.fotositio);
            fav = view.findViewById(R.id.fav);
            short_desc = view.findViewById(R.id.shortDesc);
            flag = view.findViewById(R.id.flag);
            cv = view.findViewById(R.id.cv);

        }

    }

    abstract Query setQuery();
}
