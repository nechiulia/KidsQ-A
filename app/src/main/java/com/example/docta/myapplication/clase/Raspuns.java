package com.example.docta.myapplication.clase;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Raspuns implements Serializable {
    private String textRaspuns;
    private boolean corect;

    public Raspuns(String textRaspuns, boolean corect) {
        this.textRaspuns = textRaspuns;
        this.corect = corect;
    }

//    protected Raspuns(Parcel in) {
//        textRaspuns = in.readString();
//        corect = in.readByte() != 0;
//    }

//    public static final Creator<Raspuns> CREATOR = new Creator<Raspuns>() {
//        @Override
//        public Raspuns createFromParcel(Parcel in) {
//            return new Raspuns(in);
//        }
//
//        @Override
//        public Raspuns[] newArray(int size) {
//            return new Raspuns[size];
//        }
//    };

    public String getTextRaspuns() {
        return textRaspuns;
    }

    public void setTextRaspuns(String textRaspuns) {
        this.textRaspuns = textRaspuns;
    }

    public boolean isCorect() {
        return corect;
    }

    public void setCorect(boolean corect) {
        this.corect = corect;
    }

    @Override
    public String toString() {
        return "Raspuns{" +
                "textRaspuns='" + textRaspuns + '\'' +
                ", corect=" + corect +
                '}';
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(textRaspuns);
//        dest.writeByte((byte)(corect?1:0));
//    }
}
