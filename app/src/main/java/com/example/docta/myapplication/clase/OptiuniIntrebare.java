package com.example.docta.myapplication.clase;

import android.os.Parcel;
import android.os.Parcelable;

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

//    protected OptiuniIntrebare(Parcel in) {
//        categorie = in.readString();
//        imagine = in.readString();
//        if (in.readByte() == 0) {
//            punctaj = null;
//        } else {
//            punctaj = in.readDouble();
//        }
//    }

//    public static final Creator<OptiuniIntrebare> CREATOR = new Creator<OptiuniIntrebare>() {
//        @Override
//        public OptiuniIntrebare createFromParcel(Parcel in) {
//            return new OptiuniIntrebare(in);
//        }
//
//        @Override
//        public OptiuniIntrebare[] newArray(int size) {
//            return new OptiuniIntrebare[size];
//        }
//    };

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

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(categorie);
//        dest.writeString(imagine);
//        dest.writeDouble(punctaj);
//    }
}
