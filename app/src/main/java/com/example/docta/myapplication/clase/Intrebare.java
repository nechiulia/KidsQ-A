package com.example.docta.myapplication.clase;

import java.io.Serializable;
import java.util.List;

public class Intrebare implements Serializable {
    private String textIntrebare;
    private OptiuniIntrebare optiuni;
    private List<Raspuns> raspunsuri;

    public Intrebare(String textIntrebare, OptiuniIntrebare optiuni, List<Raspuns> raspunsuri) {
        this.textIntrebare = textIntrebare;
        this.optiuni = optiuni;
        this.raspunsuri = raspunsuri;
    }

    public String getTextIntrebare() {
        return textIntrebare;
    }

    public void setTextIntrebare(String textIntrebare) {
        this.textIntrebare = textIntrebare;
    }

    public OptiuniIntrebare getOptiuni() {
        return optiuni;
    }

    public void setOptiuni(OptiuniIntrebare optiuni) {
        this.optiuni = optiuni;
    }

    public List<Raspuns> getRaspunsuri() {
        return raspunsuri;
    }

    public void setRaspunsuri(List<Raspuns> raspunsuri) {
        this.raspunsuri = raspunsuri;
    }

    @Override
    public String toString() {
        return "Intrebare{" +
                "textIntrebare='" + textIntrebare + '\'' +
                ", optiuni=" + optiuni +
                ", raspunsuri=" + raspunsuri +
                '}';
    }
}
