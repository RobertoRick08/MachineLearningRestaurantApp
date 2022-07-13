package com.example.machinelearningrestaurantapp;

public class Comanda {

    private String adresaDeLivrare, numarTelefon;
    private Float totalComanda;
    private int numarProduse;

    public Comanda(String adresaDeLivrare, String numarTelefon, Float totalComanda, int numarProduse) {
        this.adresaDeLivrare = adresaDeLivrare;
        this.numarTelefon = numarTelefon;
        this.totalComanda = totalComanda;
        this.numarProduse = numarProduse;
    }

    public String getAdresaDeLivrare() {
        return adresaDeLivrare;
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
