package com.example.machinelearningrestaurantapp;

public class User {
    public  String nume, mail, numarTelefon;
    public boolean isAdmin;
    public User(){
    }

    public User(String nume, String mail, String numarTelefon) {
        this.nume = nume;
        this.mail = mail;
        this.numarTelefon = numarTelefon;
        isAdmin = false;
    }
}
