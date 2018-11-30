package com.example.docta.myapplication.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.docta.myapplication.R;

public class Student implements Parcelable {
private String avatarName;
private int age;
private int gender;


    public Student(String avatarName, int age, int gender) {
        this.avatarName = avatarName;
        this.age = age;
        this.gender = gender;
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        String result="";
        result+= avatarName + "         "+
              +age +" ani     " ;
        if(gender == R.id.lecc_rb_baietel ){
            result+="   Băiat";
        }
        else if (gender ==R.id.lecc_rb_fetita) result+="    Fată";

        return  result;
    }

    private Student(Parcel in)
    {
        avatarName = in.readString();
        age = in.readInt();
        gender =in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatarName);
        dest.writeInt(age);
        dest.writeInt(gender);
    }
}
