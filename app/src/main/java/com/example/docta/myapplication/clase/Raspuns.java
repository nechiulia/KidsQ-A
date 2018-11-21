package com.example.docta.myapplication.clase;

import java.io.Serializable;

public class Raspuns implements Serializable {
    private String textRaspuns;
    private boolean corect;

    public Raspuns(String textRaspuns, boolean corect) {
        this.textRaspuns = textRaspuns;
        this.corect = corect;
    }

    public String getTextRaspuns() {
        return textRaspuns;
    }

    public void setTextRaspuns(String textRaspuns) {
        this.textRaspuns = textRaspuns;
    }

    public boolean isCorect() {
        return corect;
    }

    public void setCorect(boolean corect) {
        this.corect = corect;
    }

    @Override
    public String toString() {
        return "Raspuns{" +
                "textRaspuns='" + textRaspuns + '\'' +
                ", corect=" + corect +
                '}';
    }
}
