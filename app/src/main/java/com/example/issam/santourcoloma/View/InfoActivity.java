package com.example.issam.santourcoloma.View;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.issam.santourcoloma.Model.Sitio;
import com.example.issam.santourcoloma.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoActivity extends AppCompatActivity {

    TextView titulo;
    ViewPager viewPager;
    TextView longDesc;

    DatabaseReference mReference;

    String nombre,description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mReference = FirebaseDatabase.getInstance().getReference();
        titulo = findViewById(R.id.info_titulo);
        viewPager = findViewById(R.id.viewPager);
        longDesc = findViewById(R.id.info_longDesc);
        Sitio sitio = (Sitio) getIntent().getSerializableExtra("info");
        if (sitio!=null) {
            nombre = sitio.nombreSitio;
            description=sitio.longDesc;
        }else {
            nombre="no hay datos";
            description="no hay datos";
        }

            titulo.setText(nombre);
            longDesc.setText(description);



    }
}
