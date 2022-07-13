package com.example.machinelearningrestaurantapp;

public class Recomandat {
    private String denumireProdus;
    private int frecventa = 0;

    public Recomandat(String denumireProdus, int frecventa) {
        this.denumireProdus = denumireProdus;
        this.frecventa = frecventa;
    }

    public String getDenumireProdus() {
        return denumireProdus;
    }

    public void setDenumireProdus(String denumireProdus) {
        this.denumireProdus = denumireProdus;
    }

    public int getFrecventa() {
        return frecventa;
    }

    public void setFrecventa(int frecventa) {
        this.frecventa = frecventa;
    }
}
