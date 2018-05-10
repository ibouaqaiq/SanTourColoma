package com.example.issam.santourcoloma.Model;

import android.media.Image;
import android.widget.ImageButton;

public class Sitio {
    public String nombresitio;
    public Image fotositio;
    public ImageButton fav;
    public String short_desc;

    public Sitio(){}

    public Sitio(String nombresitio, Image fotositio, ImageButton fav, String short_desc) {
        this.nombresitio = nombresitio;
        this.fotositio = fotositio;
        this.fav = fav;
        this.short_desc = short_desc;
    }
}
