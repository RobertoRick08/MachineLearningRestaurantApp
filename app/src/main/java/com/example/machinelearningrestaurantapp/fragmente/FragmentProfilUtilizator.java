package com.example.machinelearningrestaurantapp.fragmente;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.machinelearningrestaurantapp.DashBoard;
import com.example.machinelearningrestaurantapp.MainActivity;
import com.example.machinelearningrestaurantapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.MalformedURLException;
import java.net.URL;

public class FragmentProfilUtilizator extends Fragment {
    private View fragmentView;
    private TextInputEditText nume,mail,varsta;
    DatabaseReference userRef;
    ImageView imagineUser;
    Button btnDelogare;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_profil_utilizator,container,false);

        nume = fragmentView.findViewById(R.id.etProfilNume);
        mail = fragmentView.findViewById(R.id.etProfilEmail);
        varsta = fragmentView.findViewById(R.id.etProfilNumar);
        imagineUser = fragmentView.findViewById(R.id.imageProfilUtilizator);
        btnDelogare = fragmentView.findViewById(R.id.btnDelogare);
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        btnDelogare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();





        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nume.setText(snapshot.child("nume").getValue().toString());
                mail.setText(snapshot.child("mail").getValue().toString());
                varsta.setText(snapshot.child("numarTelefon").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}