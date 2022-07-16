package com.example.machinelearningrestaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.machinelearningrestaurantapp.fragmente.FragmentCosCumparaturi;
import com.example.machinelearningrestaurantapp.fragmente.FragmentMeniuAcasa;
import com.example.machinelearningrestaurantapp.fragmente.FragmentProfilUtilizator;
import com.example.machinelearningrestaurantapp.fragmente.FragmentRecomandari;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashBoard extends AppCompatActivity {
        public static Bundle bundle = new Bundle();
        BottomNavigationView baraDeNavigare;
        FragmentMeniuAcasa fragmentMeniuAcasa = new FragmentMeniuAcasa();
        FragmentRecomandari fragmentRecomandari = new FragmentRecomandari();
        FragmentProfilUtilizator fragmentProfilUtilizator = new FragmentProfilUtilizator();
        FragmentCosCumparaturi fragmentCosCumparaturi = new FragmentCosCumparaturi();
        ImageView imagineBackground;

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        baraDeNavigare = findViewById(R.id.baraDeNavigare);
        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragmente,fragmentMeniuAcasa).commit();

        baraDeNavigare.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.itemAcasa:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragmente, fragmentMeniuAcasa).commit();
                        return true;

                    case R.id.itemProfil:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragmente, fragmentProfilUtilizator).commit();
                        return true;

                    case R.id.itemRecomandari:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragmente, fragmentRecomandari).commit();
                        return true;

                    case R.id.itemCart:
                        fragmentCosCumparaturi.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragmente, fragmentCosCumparaturi).commit();
                        return true;
                }
                return false;
            }

        });
    }
}