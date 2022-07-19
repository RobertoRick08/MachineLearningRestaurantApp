package com.example.machinelearningrestaurantapp;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.content.Intent;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PlasareComandaActivity extends AppCompatActivity {
    private ArrayList<Produs> cosProduse = new ArrayList<>();
    private TextInputEditText adresaLivrare, numarTelefon;
    private AppCompatButton btnPlasareComanda;
    private HashMap<String, Integer> frecventaMap = new HashMap<>();
    private ImageView checkImage;
    private AnimatedVectorDrawable animatie;
    private float totalComanda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plasare_comanda);

        Intent intent = getIntent();

        cosProduse = intent.getParcelableArrayListExtra("cosProduse");
        totalComanda = intent.getIntExtra("totalComanda", 0);
        adresaLivrare = findViewById(R.id.etAdresaLivrare);
        numarTelefon = findViewById(R.id.etNumarTelefon);
        btnPlasareComanda = findViewById(R.id.btnPlaseazaComanda);
        checkImage = findViewById(R.id.imgCheck);

        frecventaMap = (HashMap<String, Integer>) intent.getSerializableExtra("map");
        adresaLivrare.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    numarTelefon.setError("Ai depasit 10 numere!");
                    btnPlasareComanda.setEnabled(false);
                } else if (s.length() < 9) {
                    numarTelefon.setError(null);
                    btnPlasareComanda.setEnabled(false);
                } else {
                    btnPlasareComanda.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        numarTelefon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 9) {
                    numarTelefon.setError("Ai depasit 10 numere!");
                    btnPlasareComanda.setEnabled(false);
                } else if (s.length() < 9) {
                    numarTelefon.setError(null);
                    btnPlasareComanda.setEnabled(false);
                } else {
                    btnPlasareComanda.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnPlasareComanda.setEnabled(false);
        btnPlasareComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isDigitsOnly(numarTelefon.getText().toString())) {
                    numarTelefon.setError("Ati introdus din greseala si caractere!");
                    numarTelefon.requestFocus();
                    return;
                }
                if (adresaLivrare.getText().toString().isEmpty()) {
                    adresaLivrare.setError("Introduceti adresa de livrare");
                    adresaLivrare.requestFocus();
                    return;
                }
                if (adresaLivrare.getText().toString().length() < 10) {
                    adresaLivrare.setError("Adresa trebuie sa contina minim 10 caractere");
                    adresaLivrare.requestFocus();
                    return;
                }
                inregistreazaRecomandate(frecventaMap);
                inregistreazaComanda();
            }
        });


    }
    public void inregistreazaRecomandate(HashMap<String,Integer> hash){
        DatabaseReference recomandateRef = FirebaseDatabase.getInstance().getReference("Produse");
        recomandateRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if(hash.keySet().contains(dataSnapshot.child("denumire").getValue(String.class))){
                            int nrProduse = dataSnapshot.child("frecventa").getValue(Integer.class);
                            nrProduse -= hash.get(dataSnapshot.child("denumire").getValue(String.class));
                            recomandateRef.child(dataSnapshot.getKey()).child("frecventa").setValue(nrProduse);
                            hash.remove(dataSnapshot.child("denumire").getValue(String.class));
                        }

                    Log.d("Recomandate", dataSnapshot.child("denumire").getValue(String.class) + dataSnapshot.child("frecventa").getValue(Integer.class)+ "\n");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void inregistreazaComanda() {
        String adresa = adresaLivrare.getText().toString();
        String numar = numarTelefon.getText().toString();
        Log.d("Clickkk", adresa + " " + numar + " " + cosProduse.size());
        Date date = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        String stringdate = dt.format(date);
        Comanda comanda = new Comanda(adresa, numar, totalComanda, cosProduse.size(), stringdate);
        FirebaseDatabase.getInstance().getReference("Comenzi")
                .push().setValue(comanda).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Comanda a fost inregistrata cu succes!", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(getApplicationContext(), DashBoard.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent1);

                } else {
                    Toast.makeText(getApplicationContext(), "Eroare la procesarea comenzii", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}