package com.example.docta.myapplication.clase;

public class Elev {
private String numeAvatar;
private int varsta;
private boolean gen;

    public Elev(String numeAvatar, int varsta, boolean gen) {
        this.numeAvatar = numeAvatar;
        this.varsta = varsta;
        this.gen = gen;
    }

    public String getNumeAvatar() {
        return numeAvatar;
    }

    public void setNumeAvatar(String numeAvatar) {
        this.numeAvatar = numeAvatar;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public boolean isGen() {
        return gen;
    }

    public void setGen(boolean gen) {
        this.gen = gen;
    }

    @Override
    public String toString() {
        return "Elev{" +
                "Nume avatar: '" + numeAvatar + '\'' +
                ", varsta: " + varsta +
                ", gen: " + gen +
                '}';
    }
}
