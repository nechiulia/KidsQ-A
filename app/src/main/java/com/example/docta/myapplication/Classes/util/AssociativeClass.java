package com.example.docta.myapplication.Classes.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class AssociativeClass implements Parcelable {
    private String username;
    private int id_avatar;

    public AssociativeClass(String username, int id_avatar) {
        this.username = username;
        this.id_avatar = id_avatar;
    }

    protected AssociativeClass(Parcel in) {
        username = in.readString();
        id_avatar = in.readInt();
    }

    public static final Creator<AssociativeClass> CREATOR = new Creator<AssociativeClass>() {
        @Override
        public AssociativeClass createFromParcel(Parcel in) {
            return new AssociativeClass(in);
        }

        @Override
        public AssociativeClass[] newArray(int size) {
            return new AssociativeClass[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId_avatar() {
        return id_avatar;
    }

    public void setId_avatar(int id_avatar) {
        this.id_avatar = id_avatar;
    }

    @Override
    public String toString() {
        return "AssociativeClass{" +
                "username='" + username + '\'' +
                ", id_avatar=" + id_avatar +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeInt(id_avatar);
    }
}
