package com.example.docta.myapplication.clase;

import java.io.Serializable;

public class OptiuniIntrebare implements Serializable {
    private String categorie;
    private String imagine;
    private Double punctaj;

    public OptiuniIntrebare(String categorie, String imagine, Double punctaj) {
        this.categorie = categorie;
        this.imagine = imagine;
        this.punctaj = punctaj;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getImagine() {
        return imagine;
    }

    public void setImagine(String imagine) {
        this.imagine = imagine;
    }

    public Double getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(Double punctaj) {
        this.punctaj = punctaj;
    }

    @Override
    public String toString() {
        return "OptiuniIntrebare{" +
                "categorie='" + categorie + '\'' +
                ", imagine='" + imagine + '\'' +
                ", punctaj=" + punctaj +
                '}';
    }
}
