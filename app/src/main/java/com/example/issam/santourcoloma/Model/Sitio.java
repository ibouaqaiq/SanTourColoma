package com.example.issam.santourcoloma.Model;

import android.widget.RelativeLayout;

import java.util.HashMap;

public class Sitio {
    public String nombreSitio;
    public HashMap<String,Boolean> fav;
    public HashMap<String,Boolean> flag;
    public String shortDesc;
    public String longDesc;
    public String latitud, longitud;
    HashMap<String, Boolean> imagenes;


    public Sitio(){}

    public Sitio(String nombreSitio, String shortDesc, String longDesc, String latitud, String longitud) {
        this.nombreSitio = nombreSitio;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
