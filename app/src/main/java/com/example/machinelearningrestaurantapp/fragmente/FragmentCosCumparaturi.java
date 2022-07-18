package com.example.machinelearningrestaurantapp.fragmente;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.machinelearningrestaurantapp.CosAdapter;
import com.example.machinelearningrestaurantapp.DashBoard;
import com.example.machinelearningrestaurantapp.PlasareComandaActivity;
import com.example.machinelearningrestaurantapp.Produs;
import com.example.machinelearningrestaurantapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentCosCumparaturi extends Fragment {
    public static int suma = 0;
    Button afiseazaMap;
    public static HashMap<String,Integer> produsFrecventa = new HashMap<>();
    private View cosView;
    private ArrayList<Produs> cosProduse = new ArrayList<>();
    private RecyclerView recyclerView;
    public static AppCompatButton btnFinalizareCoamnda;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        cosView = inflater.inflate(R.layout.fragment_cos_cumparaturi,container,false);
        recyclerView = (RecyclerView) cosView.findViewById(R.id.listaCumparaturi);
        btnFinalizareCoamnda = cosView.findViewById(R.id.btnFinalizeazaComanda);
        afiseazaMap = cosView.findViewById(R.id.afiseazaMap);

        Bundle bundle = getArguments();
        assert bundle != null;
        if(!bundle.isEmpty()) {
            cosProduse = bundle.getParcelableArrayList("cos");
            for (Produs p : cosProduse){
                produsFrecventa.put(p.getDenumire(),1);
            }
            for (String s : produsFrecventa.keySet()){
                Log.d("Frecventa: ", s + " " + produsFrecventa.get(s).toString() +"\n");
            }
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            CosAdapter adapter = new CosAdapter(cosProduse, getContext());
            recyclerView.setAdapter(adapter);


            for (Produs p : cosProduse) {
                suma += p.getPret();
            }
            Log.d("Mesaj:", String.valueOf(suma));
            Log.d("Numar produse:", String.valueOf(cosProduse.size()));
            btnFinalizareCoamnda.setText("Finalizeaza comanda \n" + suma + " lei");

        }

        return cosView;


        // Inflate the layout for this fragment

    }

    @Override
    public void onStart() {
        super.onStart();
        afiseazaMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String s : produsFrecventa.keySet()){
                    Log.d("Frecventa ", s + " " + produsFrecventa.get(s).toString() + "\n");
                }
            }
        });
        btnFinalizareCoamnda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( suma != 0 ) {
                    Intent intent = new Intent(getContext(), PlasareComandaActivity.class);
                    intent.putParcelableArrayListExtra("cosProduse", cosProduse);
                    intent.putExtra("totalComanda", suma);
                    intent.putExtra("map",produsFrecventa);
                    startActivity(intent);
                }
                else{
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("Upss...nu exista produse in cos!\nDoriti sa reveniti in meniul principal?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Da",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent it = new Intent(getContext(), DashBoard.class);
                                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(it);
                                    dialog.cancel();
                                }
                            });
                    builder1.setNegativeButton(
                            "Nu",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        cosProduse.clear();
        produsFrecventa.clear();
        suma = 0;
    }
}