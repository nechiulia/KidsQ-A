package com.example.docta.myapplication.clase;

import java.io.Serializable;
import java.util.List;

public class SetIntrebari implements Serializable {
    private List<Intrebare> usor;
    private List<Intrebare> mediu;
    private List<Intrebare> greu;

    public SetIntrebari(List<Intrebare> usor, List<Intrebare> mediu, List<Intrebare> greu) {
        this.usor = usor;
        this.mediu = mediu;
        this.greu = greu;
    }

    public List<Intrebare> getUsor() {
        return usor;
    }

    public void setUsor(List<Intrebare> usor) {
        this.usor = usor;
    }

    public List<Intrebare> getMediu() {
        return mediu;
    }

    public void setMediu(List<Intrebare> mediu) {
        this.mediu = mediu;
    }

    public List<Intrebare> getGreu() {
        return greu;
    }

    public void setGreu(List<Intrebare> greu) {
        this.greu = greu;
    }

    @Override
    public String toString() {
        return "SetIntrebari{" +
                "usor=" + usor +
                ", mediu=" + mediu +
                ", greu=" + greu +
                '}';
    }
}
