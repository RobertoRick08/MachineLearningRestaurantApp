package com.example.machinelearningrestaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.machinelearningrestaurantapp.fragmente.FragmentCosCumparaturi;
import com.example.machinelearningrestaurantapp.fragmente.FragmentMeniuAcasa;
import com.example.machinelearningrestaurantapp.fragmente.FragmentProfilAdmin;
import com.example.machinelearningrestaurantapp.fragmente.FragmentProfilUtilizator;
import com.example.machinelearningrestaurantapp.fragmente.FragmentRecomandari;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashBoard extends AppCompatActivity {
        public static Bundle bundle = new Bundle();
        BottomNavigationView baraDeNavigare;
        FragmentMeniuAcasa fragmentMeniuAcasa = new FragmentMeniuAcasa();
        FragmentRecomandari fragmentRecomandari = new FragmentRecomandari();
        FragmentProfilUtilizator fragmentProfilUtilizator = new FragmentProfilUtilizator();
        FragmentProfilAdmin fragmentProfilAdmin = new FragmentProfilAdmin();
        FragmentCosCumparaturi fragmentCosCumparaturi = new FragmentCosCumparaturi();
        ImageView imagineBackground;
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Boolean isAdmin = false;


    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        userRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isAdmin = (Boolean) snapshot.child("isAdmin").getValue();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

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
                        if(isAdmin){
                            getSupportFragmentManager().beginTransaction().replace(R.id.containerFragmente, fragmentProfilAdmin).commit();
                        }else{
                            getSupportFragmentManager().beginTransaction().replace(R.id.containerFragmente, fragmentProfilUtilizator).commit();
                        }
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