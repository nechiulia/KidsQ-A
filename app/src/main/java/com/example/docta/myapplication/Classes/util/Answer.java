package com.example.docta.myapplication.Classes.util;

import java.io.Serializable;

public class Answer implements Serializable {
    private String answerText;
    private boolean correct;

    public Answer(String answerText, boolean correct) {
        this.answerText = answerText;
        this.correct = correct;
    }


    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerText='" + answerText + '\'' +
                ", correct=" + correct +
                '}';
    }

}
