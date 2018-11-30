package com.example.docta.myapplication.Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionsSet implements Serializable {
    private ArrayList<Question> easy;
    private ArrayList<Question> medium;
    private ArrayList<Question> hard;
    private ArrayList<Question> dailyQuestion;

    public QuestionsSet(ArrayList<Question> easy, ArrayList<Question> medium, ArrayList<Question> hard, ArrayList<Question> dailyQuestion) {
        this.easy = easy;
        this.medium = medium;
        this.hard = hard;
        this.dailyQuestion = dailyQuestion;
    }


    public ArrayList<Question> getEasy() {
        return easy;
    }

    public void setEasy(ArrayList<Question> easy) {
        this.easy = easy;
    }

    public ArrayList<Question> getMedium() {
        return medium;
    }

    public void setMedium(ArrayList<Question> medium) {
        this.medium = medium;
    }

    public ArrayList<Question> getHard() {
        return hard;
    }

    public void setHard(ArrayList<Question> hard) {
        this.hard = hard;
    }

    public ArrayList<Question> getDailyQuestion() {
        return dailyQuestion;
    }

    public void setDailyQuestion(ArrayList<Question> dailyQuestion) {
        this.dailyQuestion = dailyQuestion;
    }

    @Override
    public String toString() {
        return "QuestionsSet{" +
                "easy=" + easy +
                ", medium=" + medium +
                ", hard=" + hard +
                ", dailyQuestion=" + dailyQuestion +
                '}';
    }
}
