package com.example.machinelearningrestaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText etEmail;
    private FirebaseAuth auth;
    private Button btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        etEmail = findViewById(R.id.etForgotEmail);
        auth = FirebaseAuth.getInstance();

       btnReset = (Button) findViewById(R.id.btnResetPassword);
       btnReset.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               auth.sendPasswordResetEmail(etEmail.getText().toString())
                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if (task.isSuccessful()) {
                                   Toast.makeText(getApplicationContext(),
                                           "Email de resetare parola trimis!",
                                           Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                   finish();
                               }
                               else {
                                   Toast.makeText(getApplicationContext(),task.getException().
                                           getMessage(),Toast.LENGTH_SHORT).show();
                               }
                           }
                       });
                return;
           }
       });
    }






}