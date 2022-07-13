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
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PlasareComandaActivity extends AppCompatActivity {
    private ArrayList<Produs> cosProduse = new ArrayList<>();
    private TextInputEditText adresaLivrare , numarTelefon;
    private AppCompatButton btnPlasareComanda;
    private ImageView checkImage;
    private AnimatedVectorDrawable animatie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plasare_comanda);

        Intent intent = getIntent();

        cosProduse = intent.getParcelableArrayListExtra("cosProduse");
        float totalComanda = intent.getIntExtra("totalComanda",0);
        adresaLivrare = findViewById(R.id.etAdresaLivrare);
        numarTelefon = findViewById(R.id.etNumarTelefon);
        btnPlasareComanda = findViewById(R.id.btnPlaseazaComanda);
        checkImage = findViewById(R.id.imgCheck);

        adresaLivrare.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0 ){
                    numarTelefon.setError("Ai depasit 10 numere!");
                    btnPlasareComanda.setEnabled(false);
                }
                else if (s.length() < 9){
                    numarTelefon.setError(null);
                    btnPlasareComanda.setEnabled(false);
                }
                else {
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
                if(s.length() > 9){
                    numarTelefon.setError("Ai depasit 10 numere!");
                    btnPlasareComanda.setEnabled(false);
                }
                else if (s.length() < 9){
                    numarTelefon.setError(null);
                    btnPlasareComanda.setEnabled(false);
                }
                else {
                    btnPlasareComanda.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnPlasareComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String adresa = adresaLivrare.getText().toString();
                String numar = numarTelefon.getText().toString();
                Log.d("Clickkk", adresa + " " + numar + " " + cosProduse.size());

                Comanda comanda = new Comanda(adresa,numar,totalComanda,cosProduse.size());
                FirebaseDatabase.getInstance().getReference("Comenzi")
                        .push().setValue(comanda).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Comanda a fost inregistrata cu succes!",Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(getApplicationContext(),DashBoard.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent1);

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Eroare la procesarea comenzii",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}