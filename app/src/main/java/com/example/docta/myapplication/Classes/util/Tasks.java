package com.example.docta.myapplication.Classes.util;

import android.os.Parcel;
import android.os.Parcelable;

public class Tasks implements Parcelable {

    private Long id;
    private String date;
    private String info;
    private String username;

    public Tasks(Long id, String date, String info, String username) {
        this.date = date;
        this.info = info;
        this.username = username;
        this.id = id;
    }

    public Tasks(String date, String info, String username) {
        this.date = date;
        this.info = info;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

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

    @Override
    public String toString() {
        return "Data: " +
                 date + '\n' +
                "Info: " + info ;

    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private Tasks(Parcel in)
    {
         date = in.readString();
         info = in.readString();
         username = in.readString();
         id = in.readLong();
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
        dest.writeString(username);
        dest.writeLong(id);
    }
}
