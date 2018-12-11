package com.example.docta.myapplication.Classes.util;

import android.os.Parcel;
import android.os.Parcelable;

public class Tasks implements Parcelable {

    private String date;
    private String info;

    public Tasks(String date, String info) {
        this.date = date;
        this.info = info;
    }

    public static final Creator<Tasks> CREATOR = new Creator<Tasks>() {
        @Override
        public Tasks createFromParcel(Parcel in) {
            return new Tasks(in);
        }

        @Override
        public Tasks[] newArray(int size) {
            return new Tasks[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String toString ()
    {
        return "Data: "+ date + "\nInfo: " + info;
    }

    private Tasks(Parcel in)
    {
         date = in.readString();
         info = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(date);
        dest.writeString(info);
    }
}
