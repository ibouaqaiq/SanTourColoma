package com.example.issam.santourcoloma.Model;

import android.widget.RelativeLayout;

import java.util.HashMap;

public class Sitio {
    public String nombreSitio;
    public int fotoSitioId;
    public HashMap<String,Boolean> fav;
    public String shortDesc;
    public String latitud, longitud;


    public Sitio(){}

    public Sitio(String nombreSitio, int fotoSitioId, String shortDesc, String latitud, String longitud) {
        this.nombreSitio = nombreSitio;
        this.fotoSitioId = fotoSitioId;
        this.shortDesc = shortDesc;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
