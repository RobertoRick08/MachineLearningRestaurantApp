package com.example.machinelearningrestaurantapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Produs implements Parcelable {
    String denumire;
    float pret;
    String gramaj;
    String imagineURL;

    protected Produs(Parcel in) {
        denumire = in.readString();
        pret = in.readFloat();
        gramaj = in.readString();
        imagineURL = in.readString();
    }

    public static final Creator<Produs> CREATOR = new Creator<Produs>() {
        @Override
        public Produs createFromParcel(Parcel in) {
            return new Produs(in);
        }

        @Override
        public Produs[] newArray(int size) {
            return new Produs[size];
        }
    };

    @Override
    public String toString() {
        return "Produs{" +
                "denumire='" + denumire + '\'' +
                ", pret=" + pret +
                ", gramaj='" + gramaj + '\'' +
                ", imagineURL='" + imagineURL + '\'' +
                '}';
    }

    public Produs() {

    }

    public Produs(String denumire, float pret, String gramaj, String imagineURL) {
        this.denumire = denumire;
        this.pret = pret;
        this.gramaj = gramaj;
        this.imagineURL = imagineURL;
    }

    public String getGramaj() {
        return gramaj;
    }

    public void setGramaj(String gramaj) {
        this.gramaj = gramaj;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public String getImagineURL() {
        return imagineURL;
    }

    public void setImagineURL(String imagineURL) {
        this.imagineURL = imagineURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(denumire);
        dest.writeFloat(pret);
        dest.writeString(gramaj);
        dest.writeString(imagineURL);
    }
}
