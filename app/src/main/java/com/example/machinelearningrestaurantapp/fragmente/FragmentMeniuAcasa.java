package com.example.machinelearningrestaurantapp.fragmente;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Visibility;

import com.bumptech.glide.Glide;
import com.example.machinelearningrestaurantapp.DashBoard;
import com.example.machinelearningrestaurantapp.Produs;
import com.example.machinelearningrestaurantapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FragmentMeniuAcasa extends Fragment {
    private View produseView;
    private RecyclerView listaProduse;
    private DatabaseReference produseRef;

    public FragmentMeniuAcasa() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        produseView = inflater.inflate(R.layout.fragment_meniu_acasa,container,false);

        listaProduse = (RecyclerView) produseView.findViewById(R.id.listaProduse);
        listaProduse.setLayoutManager(new LinearLayoutManager(getContext()));

        produseRef = FirebaseDatabase.getInstance().getReference().child("Produse");
        return produseView;
    }

    @Override
    public void onOptionsMenuClosed(@NonNull Menu menu) {
        super.onOptionsMenuClosed(menu);

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions optiuni =
                new FirebaseRecyclerOptions.Builder<Produs>()
                .setQuery(produseRef, Produs.class)
                .build();

        FirebaseRecyclerAdapter<Produs,ProduseHolder> adapter =
                new FirebaseRecyclerAdapter<Produs, ProduseHolder>(optiuni) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProduseHolder holder, int position, @NonNull Produs model) {
                        String idProduse = getRef(position).getKey();
                        produseRef.child(idProduse).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    String textProdus = snapshot.child("denumire").getValue().toString();
                                    String imagineProdus = snapshot.child("imagineURL").getValue().toString();
                                    String valoareProdus = snapshot.child("pret").getValue().toString();
                                    String greutateProdus = snapshot.child("gramaj").getValue().toString();

                                    holder.denumire.setText(textProdus);
                                    holder.pret.setText(valoareProdus + " lei");
                                    holder.gramaj.setText(greutateProdus);
                                    holder.imagineURL = imagineProdus;
                                    Glide.with(getContext()).load(imagineProdus).into(holder.imagineProd);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProduseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produse_display, parent,false);
                       ProduseHolder viewHolder = new ProduseHolder(view);
                       return viewHolder;
                    }
                };

        listaProduse.setAdapter(adapter);
        adapter.startListening();
    }

    public static class ProduseHolder extends  RecyclerView.ViewHolder{
        TextView denumire, pret, gramaj;
        ImageView imagineProd;
        Button btnAdaugaLaComanda;
        String imagineURL;
        private static Produs produs;
        private static ArrayList<Produs> produseAdaugateCos = new ArrayList<>();

        public List<Produs> getProduseAdaugateCos() {
            return produseAdaugateCos;
        }


        public ProduseHolder(@NonNull View itemView) {
            super(itemView);

            denumire = itemView.findViewById(R.id.denumireProdus);
            pret = itemView.findViewById(R.id.pretProdus);
            gramaj = itemView.findViewById(R.id.gramajProdus);
            imagineProd = itemView.findViewById(R.id.imagineProdus);
            btnAdaugaLaComanda = itemView.findViewById(R.id.btnAdaugaLaComanda);
            btnAdaugaLaComanda.setTag(1);
            btnAdaugaLaComanda.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    final int status = (Integer) v.getTag();
                    if(status == 1) {
                        String denumireProdus = denumire.getText().toString();
                        String pretProdus = pret.getText().toString();
                        String gramajProdus = gramaj.getText().toString();
                        ImageView imagine = imagineProd;

                        produs = new Produs(denumireProdus,Float.parseFloat(pretProdus.split(" ")[0]),gramajProdus,imagineURL);
                        produseAdaugateCos.add(produs);
                        DashBoard.bundle.putParcelableArrayList("cos",produseAdaugateCos);


                        Toast.makeText(itemView.getContext(),denumireProdus,Toast.LENGTH_SHORT).show();


                        btnAdaugaLaComanda.setText("Adaugat!");
                        btnAdaugaLaComanda.setBackgroundColor(Color.GREEN);

                        v.setTag(0);

                    } else {

                        produseAdaugateCos.remove(produs);
                        btnAdaugaLaComanda.setText("Adauga in cos");
                        btnAdaugaLaComanda.setBackgroundColor(Color.GRAY);
                        v.setTag(1);


                    }
                }
            });



        }


    }


}