package com.example.issam.santourcoloma.View;

import android.icu.text.IDNA;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.issam.santourcoloma.Adapter.IRVAdapter;
import com.example.issam.santourcoloma.Model.Sitio;
import com.example.issam.santourcoloma.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoActivity extends AppCompatActivity {

    TextView titulo;
    TextView longDesc;
    String nombre,description;
    RecyclerView imageRV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);




        Sitio sitio = (Sitio) getIntent().getSerializableExtra("info");

        titulo = findViewById(R.id.info_titulo);
        longDesc = findViewById(R.id.info_longDesc);
        imageRV =findViewById(R.id.imageRecyclerView);

        LinearLayoutManager llm =  new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        imageRV.setLayoutManager(llm);
        IRVAdapter adapter = new IRVAdapter(sitio.imagenes,getApplicationContext());
        imageRV.setAdapter(adapter);

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
