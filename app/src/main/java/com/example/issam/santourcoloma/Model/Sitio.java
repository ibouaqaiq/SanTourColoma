package com.example.issam.santourcoloma.Model;

public class Sitio {
    public String nombreSitio;
    public int fotoSitioId;
    public boolean fav;
    public String shortDesc;

    public Sitio(){}

    public Sitio(String nombreSitio, int fotoSitioId, boolean fav, String shortDesc) {
        this.nombreSitio = nombreSitio;
        this.fotoSitioId = fotoSitioId;
        this.fav = fav;
        this.shortDesc = shortDesc;
    }
}
