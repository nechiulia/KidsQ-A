package com.example.docta.myapplication.Classes.util;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
//private Long id;
private String username;
private byte[] avatar;
private int age;
private int gender;
private double score;
private String teacher_email;


    public Student(String username, byte[] avatar, int age, int gender, Double score, String teacher_email) {
        this.username = username;
        this.avatar = avatar;
        this.age = age;
        this.gender = gender;
        this.score = score;
        this.teacher_email = teacher_email;
    }

    public Student(String username, byte[] avatar, Integer age, Integer gender) {
        this.username = username;
        this.avatar = avatar;
        this.age = age;
        this.gender = gender;
    }


    protected Student(Parcel in) {
        username = in.readString();
        avatar = in.createByteArray();
        age = in.readInt();
        gender = in.readInt();
        score = in.readDouble();
        teacher_email = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getTeacher_email() {
        return teacher_email;
    }

    public void setTeacher_email(String teacher_email) {
        this.teacher_email = teacher_email;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeByteArray(avatar);
        dest.writeInt(age);
        dest.writeInt(gender);
        dest.writeDouble(score);
        dest.writeString(teacher_email !=null ? teacher_email : null);
    }
}
