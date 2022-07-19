package com.example.machinelearningrestaurantapp.fragmente;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.machinelearningrestaurantapp.R;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class FragmentProfilAdmin extends Fragment {
private TextInputEditText numeAdmin, numarAdmin, emailAdmin;
private Button btnDeconectare,btnAfisareStatistici;
private ImageView imagineAdmin;
    public FragmentProfilAdmin() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        numeAdmin = container.findViewById(R.id.etAdminProfilNume);
        numarAdmin = container.findViewById((R.id.etAdminProfilNumar));
        emailAdmin = container.findViewById(R.id.etAdminProfilEmail);
        btnDeconectare = container.findViewById((R.id.btnAdminDelogare));
        btnAfisareStatistici = container.findViewById(R.id.btnAdminStatistici);




        return inflater.inflate(R.layout.fragment_profil_admin, container, false);
    }
}