package com.example.machinelearningrestaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView register,forgot;
    private Button login;
    private FirebaseAuth mAuth;
    private TextInputEditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.etEmail);
        editTextPassword = findViewById(R.id.etParola);


        register = (TextView) findViewById(R.id.tvRegister);
        register.setOnClickListener(this);

        login = (Button) findViewById(R.id.btLogin);
        login.setOnClickListener(this);

        forgot = (TextView) findViewById(R.id.tvForgotPassword);
        forgot.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tvForgotPassword:{
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
            }
            case R.id.tvRegister:{
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            }
            case R.id.btLogin:{
                String email = editTextEmail.getText().toString();
                String password= editTextPassword.getText().toString();
                Log.d("Login : ", email);
                Log.d("Login : ", password);
                if(email.isEmpty()){
                    editTextEmail.setError("Introduceti mailul!");
                    editTextEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    editTextPassword.setError("Introduceti parola!");
                    editTextPassword.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(MainActivity.this, DashBoard.class));
                                    finish();
                                } else {

                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }


        }

    }
}