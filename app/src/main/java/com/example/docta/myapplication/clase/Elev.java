package com.example.docta.myapplication.clase;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;

public class Elev implements Parcelable {
private String numeAvatar;
private int varsta;
private boolean gen=true;

public Elev(){
    this.numeAvatar="";
    this.varsta=7;
    this.gen=true;
}
    public Elev(String numeAvatar, int varsta, boolean gen) {
        this.numeAvatar = numeAvatar;
        this.varsta = varsta;
        this.gen = gen;
    }

    public static final Creator<Elev> CREATOR = new Creator<Elev>() {
        @Override
        public Elev createFromParcel(Parcel in) {
            return new Elev(in);
        }

        @Override
        public Elev[] newArray(int size) {
            return new Elev[size];
        }
    };

    public String getNumeAvatar() {
        return numeAvatar;
    }

    public void setNumeAvatar(String numeAvatar) {
        this.numeAvatar = numeAvatar;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public boolean isGen() {
        return gen;
    }

    public void setGen(boolean gen) {
        this.gen = gen;
    }

    @Override
    public String toString() {
        return "Elev: " +
                "Nume avatar: '" + numeAvatar + '\'' +
                ", varsta: " + varsta +
                ", gen: " + gen ;
    }

    private Elev(Parcel in) {

        numeAvatar = in.readString();
        varsta = in.readInt();
        //gen = Boolean.parseBoolean(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(numeAvatar);
        dest.writeInt(varsta);

     //   dest.writeString(String.valueOf(gen));
    }
}
