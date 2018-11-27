package com.example.docta.myapplication.clase;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.security.cert.CRLException;
import java.util.ArrayList;
import java.util.List;

public class Intrebare implements Serializable {
    private String textIntrebare;
    private OptiuniIntrebare optiuni;
    private ArrayList<Raspuns> raspunsuri;


    public Intrebare(String textIntrebare, OptiuniIntrebare optiuni, ArrayList<Raspuns> raspunsuri) {
        this.textIntrebare = textIntrebare;
        this.optiuni = optiuni;
        this.raspunsuri = raspunsuri;
    }


//    protected Intrebare(Parcel in) {
//        textIntrebare = in.readString();
//        optiuni = in.readParcelable(OptiuniIntrebare.class.getClassLoader());
//        raspunsuri = in.createTypedArrayList(Raspuns.CREATOR);
//    }

//    public static final Creator<Intrebare> CREATOR = new Creator<Intrebare>() {
//        @Override
//        public Intrebare createFromParcel(Parcel in) {
//            return new Intrebare(in);
//        }
//
//        @Override
//        public Intrebare[] newArray(int size) {
//            return new Intrebare[size];
//        }
//    };

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

    public void setRaspunsuri(ArrayList<Raspuns> raspunsuri) {
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

//    protected Intrebare(Parcel in)
//    {
//        textIntrebare = in.readString();
//        String categorie= in.readString();
//        String  imagine= in.readString();
//        Double punctaj= in.readDouble();
//        optiuni = new OptiuniIntrebare(categorie,imagine,punctaj);
////        optiuni.setCategorie(in.readString());
////        optiuni.setImagine(in.readString());
////        optiuni.setPunctaj(in.readDouble());
//        if(in.readByte()==0x01){
//            raspunsuri = new ArrayList<Raspuns>();
//            in.readList(raspunsuri,Raspuns.class.getClassLoader());
//        }else{
//            raspunsuri=null;
//        }
//
//        raspunsuri=in.readArrayList(Raspuns.class.getClassLoader());
//    }
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(textIntrebare);
//        dest.writeString(optiuni.getCategorie());
//        dest.writeString(optiuni.getImagine());
//        dest.writeDouble(optiuni.getPunctaj());
////        dest.writeTypedList(raspunsuri);
//        if(raspunsuri==null){
//            dest.writeByte((byte)(0x00));
//        }else{
//            dest.writeByte((byte)(0x01));
//            dest.writeList(raspunsuri);
//        }
//    }
}
