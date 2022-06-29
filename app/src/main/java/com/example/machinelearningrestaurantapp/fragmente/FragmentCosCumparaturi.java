package com.example.machinelearningrestaurantapp.fragmente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.machinelearningrestaurantapp.CosAdapter;
import com.example.machinelearningrestaurantapp.DashBoard;
import com.example.machinelearningrestaurantapp.Produs;
import com.example.machinelearningrestaurantapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class FragmentCosCumparaturi extends Fragment {

    private View cosView;
    private List<Produs> cosProduse = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView cos;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        cosView = inflater.inflate(R.layout.fragment_cos_cumparaturi,container,false);
        recyclerView = cosView.findViewById(R.id.listaCumparaturi);
        Bundle bundle = getArguments();
        List<Produs> cos = bundle.getParcelableArrayList("cos");
        CosAdapter adapter = new CosAdapter(cos,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);


        int suma = 0;
        for(Produs p : cos){
           suma+= p.getPret();
        }
        Log.d("Mesaj:", String.valueOf(suma));
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cos_cumparaturi, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

    }


}