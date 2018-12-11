package com.example.docta.myapplication.Classes.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {
    private String questionText;
    private QuestionSettings settings;
    private ArrayList<Answer> answers;


    public Question(String questionText, QuestionSettings settings, ArrayList<Answer> answers) {
        this.questionText = questionText;
        this.settings = settings;
        this.answers = answers;
    }



    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public QuestionSettings getSettings() {
        return settings;
    }

    public void setSettings(QuestionSettings settings) {
        this.settings = settings;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionText='" + questionText + '\'' +
                ", settings=" + settings +
                ", answers=" + answers +
                '}';
    }

}
