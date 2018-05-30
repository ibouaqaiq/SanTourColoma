package com.example.issam.santourcoloma.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.issam.santourcoloma.Fragments.SitiosFragment;
import com.example.issam.santourcoloma.Model.Sitio;
import com.example.issam.santourcoloma.R;

import java.util.ArrayList;

public class IRVAdapter extends RecyclerView.Adapter<IRVAdapter.ImageViewHolder> {
    ArrayList<String> imagenes;
    Context context;

    public IRVAdapter(ArrayList<String> sitios, Context context){
        this.imagenes = sitios;
        this.context=context;

    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;

        public ImageViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.imagenInfo);
        }
    }
    @Override
    public ImageViewHolder onCreateViewHolder( ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_sitio, viewGroup, false);
        ImageViewHolder ivh = new ImageViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder( ImageViewHolder holder, int position) {
        if(imagenes.get(position) != null){
            holder.iv.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(imagenes.get(position))
                    .into(holder.iv);
        }
    }

    @Override
    public int getItemCount() {
        return imagenes.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
