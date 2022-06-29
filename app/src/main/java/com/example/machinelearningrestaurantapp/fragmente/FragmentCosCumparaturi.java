package com.example.machinelearningrestaurantapp.fragmente;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.machinelearningrestaurantapp.CosAdapter;
import com.example.machinelearningrestaurantapp.Produs;
import com.example.machinelearningrestaurantapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FragmentCosCumparaturi extends Fragment {

    private View cosView;
    private ArrayList<Produs> cosProduse = new ArrayList<>();
    private RecyclerView recyclerView;
    private AppCompatButton btnFinalizareCoamnda;
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

        Bundle bundle = getArguments();
        ArrayList<Produs> cos = bundle.getParcelableArrayList("cos");

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        CosAdapter adapter = new CosAdapter(cos,getContext());
        recyclerView.setAdapter(adapter);
if(!cos.isEmpty()) {
    int suma = 0;
    for (Produs p : cos) {
        suma += p.getPret();
    }

    Log.d("Mesaj:", String.valueOf(suma));
    btnFinalizareCoamnda.setText("Finalizeaza comanda \n" + suma + " lei");
}
        // Inflate the layout for this fragment
        return cosView;
    }

    @Override
    public void onStart() {
        super.onStart();

    }


}