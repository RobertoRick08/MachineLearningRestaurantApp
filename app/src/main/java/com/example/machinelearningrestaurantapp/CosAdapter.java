package com.example.machinelearningrestaurantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class CosAdapter extends RecyclerView.Adapter<CosAdapter.ViewHolder> {

    private ArrayList<Produs> produseCos;
    private Context context;
    private final LayoutInflater mLayoutInflater;

    public CosAdapter(ArrayList<Produs> produseCos, Context context) {
        this.produseCos = produseCos;
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(this.context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView denumire, pret;
        ImageView imagineProdusCos;


        public ViewHolder(View view) {
            super(view);
            denumire = view.findViewById(R.id.denumireProdusCos);
            pret = view.findViewById(R.id.pretProdusCos);
            imagineProdusCos = view.findViewById(R.id.imagineProdusCos);
        }

    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item

        View view = this.mLayoutInflater.inflate(R.layout.cos_cumparaturi_display, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        viewHolder.denumire.setText(produseCos.get(position).getDenumire());
        viewHolder.pret.setText("aici sa rezolv cu intu ala");
        Glide.with(context).load(produseCos.get(position).getImagineURL()).into(viewHolder.imagineProdusCos);

    }

    @Override
    public int getItemCount() {
        return produseCos.size();
    }

}

