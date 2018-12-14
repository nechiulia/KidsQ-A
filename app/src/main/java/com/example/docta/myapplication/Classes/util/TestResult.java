package com.example.docta.myapplication.Classes.util;

import android.os.Parcel;
import android.os.Parcelable;

public class TestResult implements Parcelable {
    private int id_test;
    private String difficulty;
    private String category;
    private String username;
    private int noCorrectAnswers;
    private double score;

    public TestResult(int id_test, String dificultate, String categorie, String username, int nrIntrebari, double punctaj) {
        this.id_test = id_test;
        this.difficulty = dificultate;
        this.category = categorie;
        this.username = username;
        this.noCorrectAnswers = nrIntrebari;
        this.score = punctaj;
    }
    public TestResult (String dificultate, String categorie, String username, int nrIntrebari, double punctaj) {
        this.difficulty = dificultate;
        this.category = categorie;
        this.username = username;
        this.noCorrectAnswers = nrIntrebari;
        this.score = punctaj;
    }

    public TestResult(String dificultate, String categorie, int nrIntrebari, double punctaj) {
        this.difficulty = dificultate;
        this.category = categorie;
        this.noCorrectAnswers = nrIntrebari;
        this.score = punctaj;
    }


    protected TestResult(Parcel in) {
        id_test = in.readInt();
        difficulty = in.readString();
        category = in.readString();
        username = in.readString();
        noCorrectAnswers = in.readInt();
        score = in.readDouble();
    }

    public int getId_test() {
        return id_test;
    }

    public void setId_test(int id_test) {
        this.id_test = id_test;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNoCorrectAnswers() {
        return noCorrectAnswers;
    }

    public void setNoCorrectAnswers(int noCorrectAnswers) {
        this.noCorrectAnswers = noCorrectAnswers;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public static final Creator<TestResult> CREATOR = new Creator<TestResult>() {
        @Override
        public TestResult createFromParcel(Parcel in) {
            return new TestResult(in);
        }

        @Override
        public TestResult[] newArray(int size) {
            return new TestResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_test);
        dest.writeString(difficulty);
        dest.writeString(category);
        dest.writeString(username);
        dest.writeInt(noCorrectAnswers);
        dest.writeDouble(score);
    }
}
