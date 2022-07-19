package com.example.machinelearningrestaurantapp.fragmente;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.machinelearningrestaurantapp.Comanda;
import com.example.machinelearningrestaurantapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class FragmentProfilAdmin extends Fragment {
private TextInputEditText numeAdmin, numarAdmin, emailAdmin;
private Button btnDeconectare;
private ImageView imagineAdmin;
private ArrayList<Comanda> list  = new ArrayList<>();
private View fragmentView;
private TextView ordersNumber, oredersRevenue;
private BarChart barChart;
private ArrayList barArrayList = new ArrayList();

DatabaseReference userRef, ordersRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_profil_admin, container, false);

        numeAdmin = fragmentView.findViewById(R.id.etAdminProfilNume);
        numarAdmin = fragmentView.findViewById((R.id.etAdminProfilNumar));
        emailAdmin = fragmentView.findViewById(R.id.etAdminProfilEmail);
        imagineAdmin = fragmentView.findViewById(R.id.imageAdminProfilUtilizator);
        btnDeconectare = fragmentView.findViewById(R.id.btnAdminDelogare);
        ordersNumber = fragmentView.findViewById(R.id.ordersNumber);
        oredersRevenue = fragmentView.findViewById(R.id.ordersRevenue);
        barChart = fragmentView.findViewById(R.id.barchart);
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Comenzi");

        init();

        return fragmentView;
    }

    private void init(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                numeAdmin.setText(snapshot.child("nume").getValue().toString());
                emailAdmin.setText(snapshot.child("mail").getValue().toString());
                numarAdmin.setText(snapshot.child("numarTelefon").getValue().toString());
                Glide.with(getActivity())
                        .load(snapshot.child("imageProfile").getValue().toString())
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .circleCrop()
                        .into(imagineAdmin);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //adding the product to product list
                list = new ArrayList<>();
                for (DataSnapshot postSnapshot : snapshot.getChildren())
                    list.add(new Comanda(
                            postSnapshot.child("adresaDeLivrare").getValue().toString(),
                            postSnapshot.child("numarTelefon").getValue().toString(),
                            postSnapshot.child("totalComanda").getValue(Double.class),
                            postSnapshot.child("numarProduse").getValue(Integer.class),
                            postSnapshot.child("date").getValue().toString()
                            )
                    );
                mapOffer();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void mapOffer(){
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        Double sum = list.stream().mapToDouble(o -> o.getTotalComanda()).sum();
        Integer orders = list.size();
        oredersRevenue.setText("Total incasari: " + sum);
        ordersNumber.setText("Total nr. comenzi: " + orders);
        Map<String, List<Comanda>> result = list.stream().collect(Collectors.groupingBy(Comanda::getDate));
        Map<Float,Float> graf = new HashMap<>();
        result.forEach((keyDate,ords)-> {
            try {
                Date date = dt.parse(keyDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                Float day_of_month = (float) calendar.get(Calendar.DAY_OF_MONTH);
                Float s = (float) ords.stream().filter(o -> o.getTotalComanda() > 0).mapToDouble(o -> o.getTotalComanda()).sum();
                graf.put(day_of_month, s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        graf.forEach((key,value)->{
            barArrayList.add(new BarEntry(key,value));
        });

        setBarChart();
    }

    private void setBarChart(){
        BarDataSet barDataSet = new BarDataSet(barArrayList,"");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);
    }
}