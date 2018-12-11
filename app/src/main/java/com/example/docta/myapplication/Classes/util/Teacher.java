package com.example.docta.myapplication.Classes.util;

import android.os.Parcel;
import android.os.Parcelable;

public class Teacher implements Parcelable {
    private String email;
    private String password;

    public Teacher(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static final Creator<Teacher> CREATOR = new Creator<Teacher>() {
        @Override
        public Teacher createFromParcel(Parcel in) {
            return new Teacher(in);
        }

        @Override
        public Teacher[] newArray(int size) {
            return new Teacher[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    private Teacher(Parcel in){
        email = in.readString();
        password = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(password);
    }
}
