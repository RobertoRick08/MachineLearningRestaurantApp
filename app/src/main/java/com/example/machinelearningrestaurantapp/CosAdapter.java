package com.example.machinelearningrestaurantapp;

import static com.example.machinelearningrestaurantapp.fragmente.FragmentCosCumparaturi.btnFinalizareCoamnda;
import static com.example.machinelearningrestaurantapp.fragmente.FragmentCosCumparaturi.produsFrecventa;
import static com.example.machinelearningrestaurantapp.fragmente.FragmentCosCumparaturi.suma;

import android.content.Context;
import android.util.Log;
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
import com.example.machinelearningrestaurantapp.fragmente.FragmentCosCumparaturi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CosAdapter extends RecyclerView.Adapter<CosAdapter.ViewHolder> {

    private ArrayList<Produs> produseCos;

    private Context context;
    private final LayoutInflater mLayoutInflater;

    public CosAdapter(ArrayList<Produs> produseCos, Context context) {
        this.produseCos = produseCos;
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(this.context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView denumire, pret ,cantitate;
        ImageView imagineProdusCos;
        String imagineCosURL;
        ImageView btnRemoveProdus;
        ImageView addProdus,rmvProdus;

        public ViewHolder(View view) {
            super(view);
            denumire = view.findViewById(R.id.denumireProdusCos);
            pret = view.findViewById(R.id.pretProdusCos);
            imagineProdusCos = view.findViewById(R.id.imagineProdusCos);
            cantitate = view.findViewById(R.id.cantitate);
            final int[] cant = {Integer.parseInt(cantitate.getText().toString())};
            addProdus = view.findViewById(R.id.btnAddProdus);
            addProdus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cant[0]++;
                    cantitate.setText(cant[0]+"");
                    produsFrecventa.put(produseCos.get(getAbsoluteAdapterPosition()).getDenumire(),cant[0]);
                    updateAt(getAbsoluteAdapterPosition(),true);
                }
            });
            rmvProdus = view.findViewById(R.id.btnRmvProdus);
            rmvProdus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(cant[0] != 0){
                    cant[0]--;
                    produsFrecventa.put(produseCos.get(getAbsoluteAdapterPosition()).getDenumire(),cant[0]);
                    cantitate.setText(cant[0]+"");
                    updateAt(getAbsoluteAdapterPosition(),false);
                   }
                    if(cant[0] == 0) {

                        produseCos.remove(getAbsoluteAdapterPosition());
                        notifyItemRemoved(getAbsoluteAdapterPosition());
                        notifyItemRangeChanged(getAbsoluteAdapterPosition(), produseCos.size());
                        produsFrecventa.remove(produseCos.get(getAbsoluteAdapterPosition()).getDenumire());
                    }
                }
            });
            btnRemoveProdus = view.findViewById(R.id.btnRemoveProdusCos);
            btnRemoveProdus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   removeAt(getAbsoluteAdapterPosition());
                    Log.d("Numar de produse", String.valueOf(produseCos.size()));
                }
            });
        }
        private void removeAt(int position) {
            suma = (int) (suma - produseCos.get(position).getPret() * Integer.parseInt(cantitate.getText().toString()));
            produsFrecventa.remove(produseCos.get(position).getDenumire());
            produseCos.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, produseCos.size());

            btnFinalizareCoamnda.setText("Finalizeaza comanda \n" + suma + " lei");
        }
        private void updateAt(int position, boolean flag) {
            if(flag){
                 suma = (int) (suma + produseCos.get(position).getPret());
                 btnFinalizareCoamnda.setText("Finalizeaza comanda \n" + suma + " lei");
            }else{
                suma = (int) (suma - produseCos.get(position).getPret());
                btnFinalizareCoamnda.setText("Finalizeaza comanda \n" + suma + " lei");
            }
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
        viewHolder.pret.setText((int) produseCos.get(position).getPret()+" lei");
        Glide.with(context).load(produseCos.get(position).getImagineURL()).into(viewHolder.imagineProdusCos);

    }

    @Override
    public int getItemCount() {
        return produseCos.size();
    }

}


