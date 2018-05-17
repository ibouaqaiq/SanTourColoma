package com.example.issam.santourcoloma.Model;

public class Sitio {
    public String nombreSitio;
    public int fotoSitioId;
    public boolean fav;
    public String shortDesc;
    public String latitud, longitud;

    public Sitio(){}

    public Sitio(String nombreSitio, int fotoSitioId, boolean fav, String shortDesc, String latitud, String longitud) {
        this.nombreSitio = nombreSitio;
        this.fotoSitioId = fotoSitioId;
        this.fav = fav;
        this.shortDesc = shortDesc;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
