package com.example.docta.myapplication.clase;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.docta.myapplication.R;

import java.text.ParseException;

public class Elev implements Parcelable {
private String numeAvatar;
private int varsta;
private int gen;


    public Elev(String numeAvatar, int varsta, int gen) {
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

    public int getGen() {
        return gen;
    }

    public void setGen(int gen) {
        this.gen = gen;
    }

    @Override
    public String toString() {
        String result="";
        result+= numeAvatar + "         "+
              + varsta +" ani     " ;
        if(gen== R.id.lecc_rb_baietel ){
            result+="   Băiat";
        }
        else if (gen==R.id.lecc_rb_fetita) result+="    Fată";

        return  result;
    }

    private Elev(Parcel in)
    {
        numeAvatar = in.readString();
        varsta = in.readInt();
        gen=in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(numeAvatar);
        dest.writeInt(varsta);
        dest.writeInt(gen);
    }
}
