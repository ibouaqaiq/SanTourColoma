package com.example.issam.santourcoloma.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.issam.santourcoloma.Model.Sitio;
import com.example.issam.santourcoloma.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class SitiosFragment extends Fragment {

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
                if(sitio.fav){
                    viewHolder.fav.setImageResource(R.drawable.heart_on);
                    } else  {
                    viewHolder.fav.setImageResource(R.drawable.heart_off);

               }

                viewHolder.short_desc.setText(sitio.shortDesc);

            }
        };
    }

    class SitioViewHolder extends RecyclerView.ViewHolder {
        TextView nombresitio;
        ImageView fotositio;
        ImageView fav;
        TextView short_desc;

        SitioViewHolder(View view){
            super(view);
            nombresitio = view.findViewById(R.id.nombreSitio);
            fotositio = view.findViewById(R.id.fotositio);
            fav = view.findViewById(R.id.fav);
            short_desc = view.findViewById(R.id.shortDesc);

        }

    }

    abstract Query setQuery();
}
