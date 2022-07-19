package com.example.machinelearningrestaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText editTextFullName, editTextNumarTelefon, editTextPassword, editTextEmail;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private Button register;
    private TextView banner, registerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        editTextFullName = (TextInputEditText) findViewById(R.id.etRegisterNume);
        editTextEmail = (TextInputEditText) findViewById(R.id.etRegisterEmail);
        editTextPassword = (TextInputEditText) findViewById(R.id.etRegisterParola);
        editTextNumarTelefon = (TextInputEditText) findViewById(R.id.etNumarTelefon);

        editTextNumarTelefon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 10) {
                    editTextNumarTelefon.setError("Ai depasit 10 numere!");

                } else if (s.length() < 10) {
                    editTextNumarTelefon.setError("Numar gresit");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        progressBar = findViewById(R.id.progressBarRegister);

        register = findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                return;
            }
        });
    }


    private void registerUser() {
        String nume = editTextFullName.getText().toString();
        String password = editTextPassword.getText().toString();
        String email = editTextEmail.getText().toString();
        String numarTelefon = editTextNumarTelefon.getText().toString();


        if (nume.isEmpty()) {
            editTextFullName.setError("Introduceti numele!");
            editTextFullName.requestFocus();
            return;
        }
        if (numarTelefon.isEmpty()) {
            editTextNumarTelefon.setError("Introduceti numarul de telefon!");
            editTextNumarTelefon.requestFocus();
            return;
        }
        if (!TextUtils.isDigitsOnly(numarTelefon)) {
            editTextNumarTelefon.setError("Ati introdus din greseala si caractere!");
            editTextNumarTelefon.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Introduceti parola!");
            editTextPassword.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Introduceti mailul!");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Introduceti un e mail valid");
            editTextEmail.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Introduceti cel putin 6 caractere!");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(nume,email,numarTelefon);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this,"Contul a fost creat cu succes!",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    }
                                    else {
                                        Toast.makeText(RegisterActivity.this, "Inregistrare esuata",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this,"Failed to register", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}