package com.example.docta.myapplication.clase;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SetIntrebari implements Serializable {
    private ArrayList<Intrebare> usor;
    private ArrayList<Intrebare> mediu;
    private ArrayList<Intrebare> greu;
    private ArrayList<Intrebare> intrebareaZilei;

    public SetIntrebari(ArrayList<Intrebare> usor, ArrayList<Intrebare> mediu, ArrayList<Intrebare> greu, ArrayList<Intrebare> intrebareaZilei) {
        this.usor = usor;
        this.mediu = mediu;
        this.greu = greu;
        this.intrebareaZilei=intrebareaZilei;
    }


    public ArrayList<Intrebare> getUsor() {
        return usor;
    }

    public void setUsor(ArrayList<Intrebare> usor) {
        this.usor = usor;
    }

    public ArrayList<Intrebare> getMediu() {
        return mediu;
    }

    public void setMediu(ArrayList<Intrebare> mediu) {
        this.mediu = mediu;
    }

    public ArrayList<Intrebare> getGreu() {
        return greu;
    }

    public void setGreu(ArrayList<Intrebare> greu) {
        this.greu = greu;
    }

    public ArrayList<Intrebare> getIntrebareaZilei() {
        return intrebareaZilei;
    }

    public void setIntrebareaZilei(ArrayList<Intrebare> intrebareaZilei) {
        this.intrebareaZilei = intrebareaZilei;
    }

    @Override
    public String toString() {
        return "SetIntrebari{" +
                "usor=" + usor +
                ", mediu=" + mediu +
                ", greu=" + greu +
                ", intrebareaZilei=" + intrebareaZilei +
                '}';
    }
}
