package com.example.machinelearningrestaurantapp;

public class Comanda {

    public String adresaDeLivrare, numarTelefon, date;
    public Float totalComanda;
    public int numarProduse;

    public Comanda(String adresaDeLivrare, String numarTelefon, Float totalComanda, int numarProduse, String date) {
        this.adresaDeLivrare = adresaDeLivrare;
        this.numarTelefon = numarTelefon;
        this.totalComanda = totalComanda;
        this.numarProduse = numarProduse;
        this.date = date;
    }

    public String getAdresaDeLivrare() {
        return adresaDeLivrare;
    }

    public String getDate() {
        return date;
    }

    public void setAdresaDeLivrare(String adresaDeLivrare) {
        this.adresaDeLivrare = adresaDeLivrare;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }

    public Float getTotalComanda() {
        return totalComanda;
    }

    public void setTotalComanda(Float totalComanda) {
        this.totalComanda = totalComanda;
    }

    public int getNumarProduse() {
        return numarProduse;
    }

    public void setNumarProduse(int numarProduse) {
        this.numarProduse = numarProduse;
    }
}
