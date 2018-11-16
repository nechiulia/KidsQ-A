package com.example.docta.myapplication.clase;

import android.os.Parcel;
import android.os.Parcelable;

public class Sarcini implements Parcelable {

    private String data;
    private String info;

    public Sarcini(String data, String info) {
        this.data = data;
        this.info = info;
    }

    public static final Creator<Sarcini> CREATOR = new Creator<Sarcini>() {
        @Override
        public Sarcini createFromParcel(Parcel in) {
            return new Sarcini(in);
        }

        @Override
        public Sarcini[] newArray(int size) {
            return new Sarcini[size];
        }
    };

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String toString ()
    {
        return "Data: "+ data + "\nInfo: " + info;
    }

    private Sarcini(Parcel in)
    {
         data = in.readString();
         info = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(data);
        dest.writeString(info);
    }
}
